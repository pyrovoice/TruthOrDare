package grazzinisoftwares.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class PickTruthOrDareActivity extends AppCompatActivity implements View.OnClickListener {

    private Date lastBackPressed = null;
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
                result = ChallengeType.DARE.toString();
                break;
            case R.id.TruthButton:
                result = ChallengeType.QUESTION.toString();
                break;
        }
        returnIntent.putExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
}
