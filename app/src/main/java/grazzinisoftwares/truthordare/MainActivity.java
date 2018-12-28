package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LEVEL_LOWER_LIMIT = 1;
    private static final int LEVEL_HIGHER_LIMIT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ImageButton)findViewById(R.id.rightArrowLevel)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.leftArrowLevel)).setOnClickListener(this);
        ((Button)findViewById(R.id.button_validate)).setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.leftArrowLevel:
                changeLevel(-1);
                break;

            case R.id.rightArrowLevel:
                changeLevel(1);
                break;

            case R.id.button_validate:
                Intent intent = new Intent(this, GameActivity.class);
                TextView levelIndicator =(TextView)findViewById(R.id.levelIndicator);
                intent.putExtra("SelectedLevel", levelIndicator.getText().toString());
                intent.putStringArrayListExtra("Selected_players", getSelectedPlayers());
                startActivity(intent);
                break;
        }
    }

    private boolean changeLevel(int i) {
        TextView levelIndicator =(TextView)findViewById(R.id.levelIndicator);
        int currentLevel = Integer.parseInt(levelIndicator.getText().toString());
        if(currentLevel + i < LEVEL_LOWER_LIMIT || currentLevel + i > LEVEL_HIGHER_LIMIT){
            return false;
        }
        levelIndicator.setText("" + (currentLevel + i));
        return true;
    }

    private ArrayList<String> getSelectedPlayers(){
        ArrayList<String> selectedPlayersNames = new ArrayList<>();
        CheckBox a = (CheckBox)findViewById(R.id.panda_icon1);
        if(a.isChecked()){
            selectedPlayersNames.add(getResources().getResourceEntryName(a.getId()));
        }
         a = (CheckBox)findViewById(R.id.panda_icon2);
        if(a.isChecked()){
            selectedPlayersNames.add(getResources().getResourceEntryName(a.getId()));
        }
         a = (CheckBox)findViewById(R.id.panda_icon3);
        if(a.isChecked()){
            selectedPlayersNames.add(getResources().getResourceEntryName(a.getId()));
        }
         a = (CheckBox)findViewById(R.id.panda_icon4);
        if(a.isChecked()){
            selectedPlayersNames.add(getResources().getResourceEntryName(a.getId()));
        }

        return selectedPlayersNames;
    }
}
