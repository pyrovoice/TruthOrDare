package grazzinisoftwares.truthordarecomplete;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int LEVEL_LOWER_LIMIT = 1;
    public static final int LEVEL_HIGHER_LIMIT = 4;
    private Map<String, Gender> selectedPlayers = new HashMap<String, Gender>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ImageButton) findViewById(R.id.rightArrowLevel)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.leftArrowLevel)).setOnClickListener(this);
        ((Button) findViewById(R.id.button_validate)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player1Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player2Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player3Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player4Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player5Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player6Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player7Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player8Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player9Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player10Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player11Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player12Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player13Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player14Button)).setOnClickListener(this);
        ((Button) findViewById(R.id.Player15Button)).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Player1Button:
            case R.id.Player2Button:
            case R.id.Player3Button:
            case R.id.Player4Button:
            case R.id.Player5Button:
            case R.id.Player6Button:
            case R.id.Player7Button:
            case R.id.Player8Button:
            case R.id.Player9Button:
            case R.id.Player10Button:
            case R.id.Player11Button:
            case R.id.Player12Button:
            case R.id.Player13Button:
            case R.id.Player14Button:
            case R.id.Player15Button:
                String buttonText = ((Button) v).getText().toString();
                if (selectedPlayers.containsKey(buttonText)) {
                    if (selectedPlayers.get(buttonText) == Gender.Boy) {
                        selectedPlayers.put(buttonText, Gender.Girl);
                        ((Button) v).setBackground(getResources().getDrawable(R.drawable.btn_bckgrd_girl));
                    } else {
                        selectedPlayers.remove(buttonText);
                        ((Button) v).setBackground(getResources().getDrawable(R.drawable.btn_bckgrd_grey));
                    }
                } else {
                    selectedPlayers.put(buttonText, Gender.Boy);
                    ((Button) v).setBackground(getResources().getDrawable(R.drawable.btn_bckgrd_boy));
                }
                break;

            case R.id.leftArrowLevel:
                changeLevel(-0.5f);
                break;

            case R.id.rightArrowLevel:
                changeLevel(0.5f);
                break;

            case R.id.button_validate:
                if(selectedPlayers.size() <= 0){
                    Toast.makeText(this, "Add players by tapping their icons.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, GameActivity.class);
                TextView levelIndicator = (TextView) findViewById(R.id.levelIndicator);
                intent.putExtra("speedMode", ((CheckBox) findViewById(R.id.SpeedMode)).isChecked());
                intent.putExtra("SelectedLevel", levelIndicator.getText().toString());
                intent.putExtra("Selected_players", Helper.PlayerMapToString(selectedPlayers));
                startActivity(intent);
                break;
        }
    }

    private boolean changeLevel(float i) {
        TextView levelIndicator = (TextView) findViewById(R.id.levelIndicator);
        float currentLevel = Float.parseFloat(levelIndicator.getText().toString());
        if (currentLevel + i < LEVEL_LOWER_LIMIT || currentLevel + i > LEVEL_HIGHER_LIMIT) {
            return false;
        }
        levelIndicator.setText("" + (currentLevel + i));
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            /*case R.id.settings:
                //your code
                return true;*/
            case R.id.addNewChallenge:
                Intent intent = new Intent(this, CreateChallengeActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
