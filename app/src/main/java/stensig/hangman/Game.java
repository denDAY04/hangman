package stensig.hangman;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import galgeleg.Galgelogik;

public class Game extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = "Game";
    private static final int TARGET_INPUT_LENGTH = 1;
    private static final int MAX_NUM_WRONG_GUESSES = 6;

    private boolean initialBoot;

    private ImageView hangmanImageView;
    private Galgelogik hangmanModule;
    private TextView userGuessInput;
    private TextView targetWordOutput;
    private TextView previousGuessesOutput;
    private Button submitGuessBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialBoot = true;
        hangmanModule = new Galgelogik();

        initUiControls();
        updateGui();
        initialBoot = false;
    }

    /**
     * Retrieve, store--and optionally initialize--the UI controls.
     */
    private void initUiControls() {
        hangmanImageView = (ImageView) findViewById(R.id.hangmanImageView);
        hangmanImageView.setImageResource(R.drawable.galge);

        userGuessInput = (TextView) findViewById(R.id.guessInputTextField);
        targetWordOutput = (TextView) findViewById(R.id.targetWordTextView);
        previousGuessesOutput = (TextView) findViewById(R.id.previousGuessesTextView);

        submitGuessBtn = (Button) findViewById(R.id.submitGuessBtn);
        submitGuessBtn.setOnClickListener(this);
    }

    /**
     * Update the GUI to reflect the current state of the game.
     * This includes updating the hangman image as necessary.
     */
    private void updateGui() {
        ArrayList<String> previousGueeses = hangmanModule.getBrugteBogstaver();
        StringBuilder sb = new StringBuilder();
        for (String str : previousGueeses) {
            sb.append(str);
        }

        // Due to a bad field initialization in hangmanmodule it will initially register as if the
        // last guess was wrong. Prevent by skipping update on initial boot of the activity.
        if (!hangmanModule.erSidsteBogstavKorrekt() && !initialBoot) {
            updateHangmanImage();
        }

        targetWordOutput.setText(hangmanModule.getSynligtOrd());
        previousGuessesOutput.setText(sb.toString());
    }

    /**
     * Update the image of the hangman based on the number of wrong guesses made.
     */
    private void updateHangmanImage() {
        int numWrongGuesses = hangmanModule.getAntalForkerteBogstaver();
        switch(numWrongGuesses) {
            case 1:
                hangmanImageView.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                hangmanImageView.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                hangmanImageView.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                hangmanImageView.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                hangmanImageView.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                hangmanImageView.setImageResource(R.drawable.forkert6);
                break;
            default:
                Log.e(LOG_TAG, String.format("error in updating hangman image. More than %d wrong guesses.", MAX_NUM_WRONG_GUESSES));
                break;
        }
        Log.d(LOG_TAG, String.format("%d wrong guesses", numWrongGuesses));
    }

    /**
     * Click event handler for the user submitting a guess.
     * Updates the GUI accordingly.
     * @param v parent view object that fired the click event.
     */
    @Override
    public void onClick(View v) {
        if (v != submitGuessBtn) {
            Log.e(LOG_TAG, String.format("unexpected click-event parent %s", v.toString()));
            return;
        }

        String input = getUserInput();
        if (input.length() != TARGET_INPUT_LENGTH) {
            String errorMsg = String.format(getString(R.string.error_guess_length), TARGET_INPUT_LENGTH);
            Toast toastMsg = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG);
            toastMsg.show();
            return;
        }

        hangmanModule.gætBogstav(input);
        if (hangmanModule.erSpilletVundet()) {
            Log.d(LOG_TAG, "game won");
            Intent wonIntent = new Intent(this, Won.class);
            wonIntent.putExtra("correctWord", hangmanModule.getOrdet());
            startActivity(wonIntent);
            return;
        } else if (hangmanModule.erSpilletTabt()) {
            Log.d(LOG_TAG, "game lost");
            Intent lostIntent = new Intent(this, Lost.class);
            lostIntent.putExtra("correctWord", hangmanModule.getOrdet());
            startActivity(lostIntent);
            return;
        }

        updateGui();
    }

    /**
     * Get the user's input and clear the input field.
     * @return the user's input.
     */
    @NonNull
    private String getUserInput() {
        String input = userGuessInput.getText().toString();
        userGuessInput.setText("");     // Clear the input
        return input;
    }

}
