package grazzinisoftwares.truthordare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Helper {

    private static final String ABSOLUTE_PATH_TO_USER_CHALLENGES = "/mnt/sdcard/challenges.json";
    public static ArrayList<Challenge> allChallenges = null;
    public static ArrayList<Challenge> currentGameChallenges = new ArrayList<>();
    private static String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public static String PlayerMapToString(Map<String, Gender> map) {
        String rs = "";
        for (String s : map.keySet()) {
            rs += s + "-" + map.get(s).toString() + ";";
        }
        return rs;
    }

    public static ArrayList<Player> StringToPlayerList(String rs) {
        String[] split = rs.split(";");
        ArrayList<Player> players = new ArrayList<Player>();
        for (String s : split) {
            String[] playerInfo = s.split("-");
            if (playerInfo.length != 2)
                continue;
            players.add(new Player(playerInfo[0], Gender.valueOf(playerInfo[1])));
        }
        return players;
    }

    public static void refreshGameDisplayables(Game g) {
        currentGameChallenges = new ArrayList<>();
        for (Challenge d : allChallenges) {
            if (g.isDisplayableCompatible(d) && Math.abs(g.level - d.level) <= 0.5) {
                currentGameChallenges.add(d);
            }
        }
    }

    public static boolean LoadDisplayables(Context context) {
        if (allChallenges != null) {
            return true;
        }
        allChallenges = new ArrayList<>();
        try {
            JSONObject jsonString = new JSONObject(readFromFile(context.getResources().openRawResource(R.raw.displayed)));

            // Then, for each object, create a displayable
            JSONArray jArray = jsonString.getJSONArray("displayables");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject currentDisplayable = jArray.getJSONObject(i);
                String text = currentDisplayable.getString("Text");
                int level = currentDisplayable.getInt("Level");
                ChallengeType dType = currentDisplayable.getString("Type").equals("Dare") ? ChallengeType.DARE : ChallengeType.QUESTION;
                JSONArray gendersJSON = currentDisplayable.getJSONArray("Targets");
                ArrayList<Gender> genders = new ArrayList<>();

                for (int j = 0; j < gendersJSON.length(); j++) {
                    genders.add(Gender.valueOf((String) gendersJSON.get(j)));
                }

                allChallenges.add(new Challenge(text, level, dType, genders, currentDisplayable.optInt("Font-size", -1), false));
            }
        } catch (JSONException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        //Load user challenges
        File f = new File(ABSOLUTE_PATH_TO_USER_CHALLENGES);
        if(f.exists()){
            try {
                JSONObject jsonString = new JSONObject(readFromFile(ABSOLUTE_PATH_TO_USER_CHALLENGES));

                // Then, for each object, create a displayable
                JSONArray jArray = jsonString.getJSONArray("displayables");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject currentDisplayable = jArray.getJSONObject(i);
                    String text = currentDisplayable.getString("Text");
                    int level = currentDisplayable.getInt("Level");
                    ChallengeType dType = currentDisplayable.getString("Type").equals("Dare") ? ChallengeType.DARE : ChallengeType.QUESTION;
                    JSONArray gendersJSON = currentDisplayable.getJSONArray("Targets");
                    ArrayList<Gender> genders = new ArrayList<>();

                    for (int j = 0; j < gendersJSON.length(); j++) {
                        genders.add(Gender.valueOf((String) gendersJSON.get(j)));
                    }

                    allChallenges.add(new Challenge(text, level, dType, genders, currentDisplayable.optInt("Font-size", -1), true));
                }
            } catch (JSONException e) {
                return false;
            }
        }
        return true;
    }

    public static Challenge getRandomDisplayableForPlayer(Game game, Player player, ChallengeType dType) {
        if (currentGameChallenges.size() <= 0) {
            refreshGameDisplayables(game);
        }
        Collections.shuffle(currentGameChallenges);
        for (Challenge d : currentGameChallenges) {
            if ((d.targets.contains(player.gender) || d.targets.contains(Gender.Any)) && d.type == dType)
                return d;
        }
        return null;
    }

    public static List<Challenge> getRandomDisplayables(int nbr, Game game) {
        if (currentGameChallenges.size() <= 0) {
            refreshGameDisplayables(game);
        }

        Collections.shuffle(currentGameChallenges);
        if (currentGameChallenges.size() <= nbr) {
            return currentGameChallenges;
        }

        return currentGameChallenges.subList(0, nbr - 1);
    }

    public static boolean createUserChallenge(Context context, Challenge newChallenge) {
        File f = new File(ABSOLUTE_PATH_TO_USER_CHALLENGES);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = "{\n\"displayables\": []\n}";
            writeToFile(ABSOLUTE_PATH_TO_USER_CHALLENGES, s);
        }
        String s = readFromFile(ABSOLUTE_PATH_TO_USER_CHALLENGES);
        int jsonClose = s.lastIndexOf("]");
        String challengeString = JsonStringFromChallenge(newChallenge);
        s = s.substring(0, jsonClose) + challengeString + s.substring(jsonClose);
        writeToFile(ABSOLUTE_PATH_TO_USER_CHALLENGES, s);
        return true;
    }

    private static String readFromFile(String filePath) {
        try {
            return readFromFile(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String JsonStringFromChallenge(Challenge newChallenge) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\"Text\": \"" + newChallenge.text + "\",\n");
        sb.append("\"Level\": " + newChallenge.level + ",\n");
        sb.append("\"Type\": \"" + newChallenge.type.toString() + "\",\n");
        sb.append("\"Targets\": [\n");
        for (Gender g : newChallenge.targets) {
            sb.append("\"" + g.toString() + "\"\n");
        }
        sb.append("]\n},\n");
        return sb.toString();
    }

    private static String readFromFile(InputStream is) throws IOException {
        /*Writer writer = new StringWriter();
        byte[] buffer = null;
        try {
            buffer = new byte[is.read()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String a = new String(buffer, StandardCharsets.UTF_8);
        return a;*/
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
        return total.toString();
    }

    private static void writeToFile(String path, String toWrite) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            byte[] buffer = toWrite.getBytes(StandardCharsets.UTF_8);
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String buildDisplayableText(Challenge d, ArrayList<Player> selectedPlayers) {
        String text = d.text;
        for (int i = 0; i < d.targets.size(); i++) {
            text = text.replace("{" + i + "}", selectedPlayers.get(i).iconName);
        }
        return text;
    }
}
