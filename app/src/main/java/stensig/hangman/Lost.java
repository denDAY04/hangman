package stensig.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lost extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        String correctWord = getIntent().getStringExtra("correctWord");
        TextView correctWordOutput = (TextView) findViewById(R.id.lostCorrectWordTextView);
        correctWordOutput.setText(correctWord);

        findViewById(R.id.lostPlayAgainBtn).setOnClickListener(this);
    }

    /**
     * Click event handler that restarts the game.
     * @param v Parent view object that fired the click event.
     */
    @Override
    public void onClick(View v) {
        Intent gameIntent = new Intent(this, Game.class);
        gameIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gameIntent);
        finish();
    }
}
