package stensig.hangman;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Won extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_won, container, false);

        Bundle args = getArguments();
        String correctWord = "N/A";
        if (args != null)
            correctWord = args.getString("correctWord");
        TextView correctWordOutput = (TextView) fragmentView.findViewById(R.id.wonCorrectWordTextView);
        correctWordOutput.setText(correctWord);

        fragmentView.findViewById(R.id.wonPlayAgainBtn).setOnClickListener(this);
        return fragmentView;
    }

    /**
     * Click event handler that restarts the game.
     * @param v Parent view object that fired the click event.
     */
    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.main_fragment, new Game()).commit();
    }
}
