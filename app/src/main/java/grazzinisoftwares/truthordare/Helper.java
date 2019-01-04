package grazzinisoftwares.truthordare;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Helper {

    public static ArrayList<Displayable> allDisplayables = null;
    public static ArrayList<Displayable> currentGameDisplayables = new ArrayList<>();



    public static String PlayerMapToString(Map<String, Gender> map){
        String rs = "";
        for(String s : map.keySet()){
            rs += s + "-" + map.get(s).toString() + ";";
        }
        return rs;
    }

    public static ArrayList<Player> StringToPlayerList(String rs){
        String[] split = rs.split(";");
        ArrayList<Player> players = new ArrayList<Player>();
        for(String s : split){
            String[] playerInfo = s.split("-");
            if(playerInfo.length != 2)
                continue;
            players.add(new Player(playerInfo[0], Gender.valueOf(playerInfo[1])));
        }
        return players;
    }

    public static void refreshGameDisplayables(Game g){
        currentGameDisplayables = new ArrayList<>();
        for (Displayable d : allDisplayables) {
            if (g.isDisplayableCompatible(d) && g.level == d.level) {
                currentGameDisplayables.add(d);
            }
        }
    }

    public static List<Displayable> getRandomDisplayables(int nbr, Game game) {
        if(currentGameDisplayables.size()<= 0){
            refreshGameDisplayables(game);
        }

        Collections.shuffle(currentGameDisplayables);
        if (currentGameDisplayables.size() <= nbr) {
            return currentGameDisplayables;
        }

        return currentGameDisplayables.subList(0, nbr-1);
    }

    public static boolean LoadDisplayables(Context context){
        //Get all Displayables from the JSON file
        //First, read the JSON file and create a JSONObject
        InputStream is = context.getResources().openRawResource(R.raw.displayed);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            /*Intent i = new Intent(this, MainActivity.class);
            i.putExtra("errorCode", "IOException");
            startActivity(i);*/
            return false;
        }

        try {
            JSONObject jsonString = new JSONObject(writer.toString());

            // Then, for each object, create a displayable
            JSONArray jArray = jsonString.getJSONArray("displayables");
            allDisplayables = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject currentDisplayable = jArray.getJSONObject(i);
                String text = currentDisplayable.getString("Text");
                int level = currentDisplayable.getInt("Level");
                DisplayableType dType = currentDisplayable.getString("Type").equals("Dare") ? DisplayableType.DARE : DisplayableType.QUESTION;
                JSONArray gendersJSON = currentDisplayable.getJSONArray("Targets");
                ArrayList<Gender> genders = new ArrayList<>();

                for (int j = 0; j < gendersJSON.length(); j++) {
                    genders.add(Gender.valueOf((String) gendersJSON.get(j)));
                }

                allDisplayables.add(new Displayable(text, level, dType, genders, currentDisplayable.optInt("Font-size", -1)));
            }
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
