package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Game MainGame;
    private ArrayList<Challenge> currentChallenges = new ArrayList();
    private int currentIterationNumber = 0;
    private int currentSelectedPlayer = -1;
    private Date lastBackPressed = null;


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

    @Override
    public void onBackPressed() {
        if (lastBackPressed == null || lastBackPressed.getTime() + 4000 < new Date().getTime()) {
            lastBackPressed = new Date();
            Toast.makeText(this, "Press back again to quit current game.", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
            Challenge d = Helper.getRandomDisplayableForPlayer(MainGame, p, ChallengeType.valueOf(data.getStringExtra("result")));
            TextView displayableText = (TextView) findViewById(R.id.displayableText);
            displayableText.setText(Helper.buildDisplayableText(d, MainGame.getRandomPlayers(d.targets, p)));
            displayableText.setTextSize(d.getFontSize());
        }
    }

    private void quickGameActivity() {
        //Check there are displayables left. If not, add 20 new displayables to the list
        if (currentIterationNumber >= currentChallenges.size()) {
            currentChallenges = new ArrayList<>(Helper.getRandomDisplayables(15, MainGame));
            currentIterationNumber = 0;
        }

        //Display next question
        TextView displayableText = (TextView) findViewById(R.id.displayableText);
        Challenge d = currentChallenges.get(currentIterationNumber);
        displayableText.setText(Helper.buildDisplayableText(d, MainGame.getRandomPlayers(d.targets, null)));
        displayableText.setTextSize(d.getFontSize());
        currentIterationNumber++;
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
