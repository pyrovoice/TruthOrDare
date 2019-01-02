package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Game MainGame;
    private ArrayList<Displayable> currentDisplayables = new ArrayList();
    private int currentIterationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Add listeners
        ((ImageButton)findViewById(R.id.thumbUp)).setOnClickListener(this);

        // Get user selected data
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.displayableText);
        String s = "Level : " + intent.getStringExtra("SelectedLevel") + ". Selected players : ";
        ArrayList<Player> players = Helper.StringToPlayerList(intent.getStringExtra("Selected_players"));

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
        currentIterationNumber = 0;
        displayNextQuestion();
    }

    private void displayNextQuestion(){
        //Check there are displayables left. If not, add 20 new displayables to the list
        if(currentIterationNumber >= currentDisplayables.size()){
            currentDisplayables = MainGame.getRandomDisplayables();
            currentIterationNumber = 0;
        }

        //Display next question
        TextView displayableText = (TextView) findViewById(R.id.displayableText);
        displayableText.setText(buildDisplayableText(currentDisplayables.get(currentIterationNumber)));
        currentIterationNumber++;
    }

    private String buildDisplayableText(Displayable d){
        String text = d.text;
        ArrayList<Player> selectedPlayers = MainGame.getRandomPlayers(d.targets);
        for(int i = 0; i < d.targets.size(); i++){
            text = text.replace("{" + i + "}", selectedPlayers.get(i).iconName);
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.thumbUp:
                displayNextQuestion();
                break;
        }
    }
}
