package grazzinisoftwares.truthordare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PickTruthOrDareActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picktruthordare);

        // Add listeners
        ((ImageButton) findViewById(R.id.DareButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.TruthButton)).setOnClickListener(this);

        // Get user selected data
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.PlayerIcon);
        textView.setText(intent.getStringExtra("playerName"));
        textView.setTextSize(50);
    }

    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        String result = null;
        switch (v.getId()) {
            case R.id.DareButton:
                result = DisplayableType.DARE.toString();
                break;
            case R.id.TruthButton:
                result = DisplayableType.QUESTION.toString();
                break;
        }
        returnIntent.putExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
