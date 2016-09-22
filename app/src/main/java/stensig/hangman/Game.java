package stensig.hangman;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import galgeleg.Galgelogik;

public class Game extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = "GameFragment";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_game, container, false);
        initialBoot = true;
        hangmanModule = new Galgelogik();

        // Load words online in separate thread, with progress dialog on main thread.
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", getString(R.string.loading_online_words), true);
        new AsyncTask() {

            @Override
            protected void onPostExecute(Object gotWordFromInternet) {
                progressDialog.dismiss();
                Boolean internetFetchSuccess = (Boolean) gotWordFromInternet;
                if (!internetFetchSuccess) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.error_loading_online_words), Toast.LENGTH_LONG);
                    toast.show();
                }
                initUiControls(fragmentView);
                updateGui();
                initialBoot = false;
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    hangmanModule.hentOrdFraDr();
                    return Boolean.TRUE;
                } catch (Exception e) {
                    e.printStackTrace();
                    hangmanModule.nulstil();
                    return Boolean.FALSE;
                }
            }
        }.execute();

        return fragmentView;
    }


    /**
     * Retrieve, store--and optionally initialize--the UI controls.
     */
    private void initUiControls(View fragmentView) {
        hangmanImageView = (ImageView) fragmentView.findViewById(R.id.hangmanImageView);
        hangmanImageView.setImageResource(R.drawable.galge);

        userGuessInput = (TextView) fragmentView.findViewById(R.id.guessInputTextField);
        targetWordOutput = (TextView) fragmentView.findViewById(R.id.targetWordTextView);
        previousGuessesOutput = (TextView) fragmentView.findViewById(R.id.previousGuessesTextView);

        submitGuessBtn = (Button) fragmentView.findViewById(R.id.submitGuessBtn);
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
            Toast toastMsg = Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG);
            toastMsg.show();
            return;
        }

        hangmanModule.gætBogstav(input);
        if (hangmanModule.erSpilletVundet()) {

            Log.d(LOG_TAG, "game won");
            Fragment wonFragment = new Won();
            Bundle args = new Bundle();
            args.putString("correctWord", hangmanModule.getOrdet());
            wonFragment.setArguments(args);

            getFragmentManager().beginTransaction().replace(R.id.main_fragment, wonFragment).commit();
            return;

        } else if (hangmanModule.erSpilletTabt()) {
            Log.d(LOG_TAG, "game lost");

            Fragment lostFragment = new Lost();
            Bundle args = new Bundle();
            args.putString("correctWord", hangmanModule.getOrdet());
            lostFragment.setArguments(args);

            getFragmentManager().beginTransaction().replace(R.id.main_fragment, lostFragment).commit();
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
