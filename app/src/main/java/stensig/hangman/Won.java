package stensig.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Won extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        String correctWord = getIntent().getStringExtra("correctWord");
        TextView correctWordOutput = (TextView) findViewById(R.id.correctWordTextView);
        correctWordOutput.setText(correctWord);
    }
}
