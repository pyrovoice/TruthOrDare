package grazzinisoftwares.truthordare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int LEVEL_LOWER_LIMIT = 1;
    private static final int LEVEL_HIGHER_LIMIT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                
                break;
        }
    }

    private boolean changeLevel(int i) {
        TextView levelIndicator =(TextView)findViewById(R.id.levelIndicator);
        int currentLevel = Integer.parseInt(levelIndicator.getText().toString());
        if(currentLevel + i < LEVEL_LOWER_LIMIT || currentLevel + i > LEVEL_HIGHER_LIMIT){
            return false;
        }
        levelIndicator.setText(currentLevel + i);
        return true;
    }
}
