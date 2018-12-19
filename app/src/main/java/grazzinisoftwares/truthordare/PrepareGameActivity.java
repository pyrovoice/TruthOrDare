package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Iterator;

public class PrepareGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.displayText);
        String s = "Level : " + intent.getStringExtra(MainActivity.SELECTED_LEVEL) + ". Selected players : ";
        for(Iterator<String> iter = intent.getStringArrayListExtra(MainActivity.SELECTED_PLAYERS).iterator(); iter.hasNext();){
            s += iter.next() + ", ";
        }
        textView.setText(s);
    }
}
