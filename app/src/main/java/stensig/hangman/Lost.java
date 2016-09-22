package stensig.hangman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Lost extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lost, container, false);

        Bundle args = getArguments();
        String correctWord = "N/A";
        if (args != null)
            correctWord = args.getString("correctWord");
        TextView correctWordOutput = (TextView) fragmentView.findViewById(R.id.lostCorrectWordTextView);
        correctWordOutput.setText(correctWord);

        fragmentView.findViewById(R.id.lostPlayAgainBtn).setOnClickListener(this);
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
