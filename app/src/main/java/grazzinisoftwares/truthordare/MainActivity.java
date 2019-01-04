package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LEVEL_LOWER_LIMIT = 2;
    private static final int LEVEL_HIGHER_LIMIT = 3;
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
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Player1Button:
            case R.id.Player2Button:
            case R.id.Player3Button:
            case R.id.Player4Button:
            case R.id.Player5Button:
            case R.id.Player6Button:
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
                changeLevel(-1);
                break;

            case R.id.rightArrowLevel:
                changeLevel(1);
                break;

            case R.id.button_validate:
                Intent intent = new Intent(this, GameActivity.class);
                TextView levelIndicator = (TextView) findViewById(R.id.levelIndicator);
                intent.putExtra("SelectedLevel", levelIndicator.getText().toString());
                intent.putExtra("Selected_players", Helper.PlayerMapToString(selectedPlayers));
                startActivity(intent);
                break;
        }
    }

    private boolean changeLevel(int i) {
        TextView levelIndicator = (TextView) findViewById(R.id.levelIndicator);
        int currentLevel = Integer.parseInt(levelIndicator.getText().toString());
        if (currentLevel + i < LEVEL_LOWER_LIMIT || currentLevel + i > LEVEL_HIGHER_LIMIT) {
            return false;
        }
        levelIndicator.setText("" + (currentLevel + i));
        return true;
    }
}
