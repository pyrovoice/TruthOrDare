package grazzinisoftwares.truthordare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateChallengeActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addchallenge);

        // Add listeners
        ((ImageButton) findViewById(R.id.AddBoy)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.AddGirl)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.MinusLevel)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.PlusLevel)).setOnClickListener(this);
        ((Button) findViewById(R.id.button_validate)).setOnClickListener(this);
        Switch mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ((TextView)findViewById(R.id.switchTextView)).setText("Dare");
                }else{
                    ((TextView)findViewById(R.id.switchTextView)).setText("Truth");
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddBoy:
                addPlayerToText(Gender.Boy);
                break;
            case R.id.AddGirl:
                addPlayerToText(Gender.Girl);
                break;
            case R.id.AddAny:
                addPlayerToText(Gender.Any);
                break;
            case R.id.MinusLevel:
                changeLevel(-1);
                break;
            case R.id.PlusLevel:
                changeLevel(1);
                break;
            case R.id.button_validate:
                createUserChallenge();
                emptyTextBox();
                break;
        }
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    private void proceedCreateChallenge(){
        Challenge newChallenge = new Challenge();
        String enteredText = ((EditText) findViewById(R.id.ChallengeText)).getText().toString();
        newChallenge.level = Integer.parseInt(((TextView) findViewById(R.id.LevelText)).getText().toString());
        ArrayList<Gender> targets = new ArrayList<>();
        int playerCounter = 0;
        for (int i = enteredText.length() - 1; i >= 0; i--) {
            Gender g = getGenderFromChar(enteredText.charAt(i));
            if (g != null) {
                targets.add(g);
                //If we're not on the last character
                if (i < enteredText.length() - 1) {
                    enteredText = enteredText.substring(0, i) + "{" + playerCounter + "}" + enteredText.substring(i + 1);
                } else {
                    enteredText = enteredText.substring(0, i) + "{" + playerCounter + "}";
                }
                playerCounter++;
            }
        }
        newChallenge.text = enteredText;
        newChallenge.targets = targets;
        newChallenge.type = ((Switch) findViewById(R.id.switch1)).isChecked() ? ChallengeType.DARE : ChallengeType.QUESTION;
        Helper.createUserChallenge(this, newChallenge);
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ){
        proceedCreateChallenge();
    }

    private void createUserChallenge() {
        //Require permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();
        }else{
            proceedCreateChallenge();
        }
    }

    public Gender getGenderFromChar(char c) {
        switch (c) {
            case '♂':
                return Gender.Boy;
            case '♀':
                return Gender.Girl;
            case '⚥':
                return Gender.Any;
            default:
                return null;
        }
    }

    private void addPlayerToText(Gender gender) {
        EditText challengeText = (EditText) findViewById(R.id.ChallengeText);
        String s = challengeText.getText().toString();

        int position;
        char sexCharacter;
        switch (gender) {
            case Boy:
                sexCharacter = '♂';
                break;
            case Girl:
                sexCharacter = '♀';
                break;
            case Any:
            default:
                sexCharacter = '⚥';
        }
        if (challengeText.getSelectionStart() != -1) {
            position = challengeText.getSelectionStart();
            s = s.substring(0, position) + sexCharacter + s.substring(position);
        } else {
            s += sexCharacter;
        }

        challengeText.setText(s);
        challengeText.setSelection(s.length());
    }

    private boolean changeLevel(int i) {
        TextView levelIndicator = (TextView) findViewById(R.id.LevelText);
        int currentLevel = Integer.parseInt(levelIndicator.getText().toString());
        if (currentLevel + i < MainActivity.LEVEL_LOWER_LIMIT || currentLevel + i > MainActivity.LEVEL_HIGHER_LIMIT) {
            return false;
        }
        levelIndicator.setText("" + (currentLevel + i));
        return true;
    }

    private void emptyTextBox() {
        ((EditText) findViewById(R.id.ChallengeText)).setText("");
    }



}
