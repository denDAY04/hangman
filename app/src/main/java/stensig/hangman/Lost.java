package stensig.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Lost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        String correctWord = getIntent().getStringExtra("correctWord");
        TextView correctWordOoutput = (TextView) findViewById(R.id.lostCorrectWordTextView);
        correctWordOoutput.setText(correctWord);
    }
}
