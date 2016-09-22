package stensig.hangman;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Welcome extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = "WelcomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_welcome, container, false);
        Button startBtn = (Button) fragView.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);
        return fragView;
    }

    @Override
    public void onClick(View view) {
        Log.d(LOG_TAG, "staring game");
        getFragmentManager().beginTransaction().replace(R.id.main_fragment, new Game()).commit();
    }

}
