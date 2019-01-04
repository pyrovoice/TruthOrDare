package grazzinisoftwares.truthordare;

import android.app.Activity;
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
    private int currentIterationNumber = 0;
    private int currentSelectedPlayer = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Add listeners
        ((ImageButton) findViewById(R.id.thumbUp)).setOnClickListener(this);

        // Get user selected data
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.displayableText);
        ArrayList<Player> players = Helper.StringToPlayerList(intent.getStringExtra("Selected_players"));
        Helper.LoadDisplayables(this);
        this.MainGame = new Game(players, Float.parseFloat(intent.getStringExtra("SelectedLevel")), intent.getBooleanExtra("speedMode", false));
        startNextRound();
    }

    private void startNextRound() {
        if (this.MainGame.isQuickGame)
            quickGameActivity();
        else {
            gameActivity();
        }
    }

    private void gameActivity() {
        if(currentSelectedPlayer >= MainGame.players.size() - 1){
            currentSelectedPlayer = -1;
        }
        currentSelectedPlayer++;
        Player currentPlayer = MainGame.players.get(currentSelectedPlayer);
        Intent intentToPickTruthOrDare = new Intent(this, PickTruthOrDareActivity.class);
        intentToPickTruthOrDare.putExtra("playerName", currentPlayer.iconName);
        startActivityForResult(intentToPickTruthOrDare, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            Player p = MainGame.players.get(currentSelectedPlayer);
            Displayable d = Helper.getRandomDisplayableForPlayer(MainGame, p, DisplayableType.valueOf(data.getStringExtra("result")));
            TextView displayableText = (TextView) findViewById(R.id.displayableText);
            displayableText.setText(buildDisplayableText(d, MainGame.getRandomPlayers(d.targets, p)));
            displayableText.setTextSize(d.getFontSize());
        }
    }

    private void quickGameActivity() {
        //Check there are displayables left. If not, add 20 new displayables to the list
        if (currentIterationNumber >= currentDisplayables.size()) {
            currentDisplayables = new ArrayList<>(Helper.getRandomDisplayables(15, MainGame));
            currentIterationNumber = 0;
        }

        //Display next question
        TextView displayableText = (TextView) findViewById(R.id.displayableText);
        Displayable d = currentDisplayables.get(currentIterationNumber);
        displayableText.setText(buildDisplayableText(d, MainGame.getRandomPlayers(d.targets, null)));
        displayableText.setTextSize(d.getFontSize());
        currentIterationNumber++;
    }

    private String buildDisplayableText(Displayable d, ArrayList<Player> selectedPlayers) {
        String text = d.text;
        for (int i = 0; i < d.targets.size(); i++) {
            text = text.replace("{" + i + "}", selectedPlayers.get(i).iconName);
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thumbUp:
                startNextRound();
                break;
        }
    }
}
