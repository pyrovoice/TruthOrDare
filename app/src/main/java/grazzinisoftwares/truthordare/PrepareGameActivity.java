package grazzinisoftwares.truthordare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PrepareGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.displayText);
        String s = "Level : " + intent.getStringExtra(MainActivity.SELECTED_LEVEL) + ". Selected players : ";
        intent.getStringArrayListExtra(MainActivity.SELECTED_PLAYERS).forEach((n) s += n);
        textView.setText();
    }
}
