package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

public class GameActivity extends AppCompatActivity {

    private Game MainGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get user selected data
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.displayText);
        String s = "Level : " + intent.getStringExtra("SelectedLevel") + ". Selected players : ";
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<String> selection = intent.getStringArrayListExtra("Selected_players");
        for (int i = 0; i < intent.getStringArrayListExtra("Selected_players").size(); i++) {
            players.add(new Player(i, selection.get(i), i / 2 == 0 ? Gender.MALE : Gender.FEMALE));
        }

        //Get all Displayables from the JSON file
        //First, read the JSON file and create a JSONObject
        InputStream is = getResources().openRawResource(R.raw.displayed);
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
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("errorCode", "IOException");
            startActivity(i);
        }

        ArrayList<Displayable> displayables = null;
        try {
            JSONObject jsonString = new JSONObject(writer.toString());

            // Then, for each object, create a displayable
            JSONArray jArray = jsonString.getJSONArray("displayables");
            displayables = new ArrayList<>();
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
                displayables.add(new Displayable(text, level, dType, genders));
            }
        } catch (JSONException e) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("errorCode", "JSONException");
            startActivity(i);
        }
        this.MainGame = new Game(players, Integer.parseInt(intent.getStringExtra("SelectedLevel")), displayables);
    }
}
