package stensig.hangman;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ViewWords extends Fragment {
    private static final String LOG_TAG = "ViewWordsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView wordsView = new ListView(getContext());
        wordsView.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, GameSingleton.getInstance().getMuligeOrd()){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View itemView =  super.getView(position, convertView, parent);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedWord = GameSingleton.getInstance().getMuligeOrd().get(position);
                        Log.d(LOG_TAG, "onClick: ressting with words " + selectedWord);

                        GameSingleton.getInstance().nulstilMedOrd(selectedWord);
                        Bundle args = new Bundle();
                        args.putBoolean("restarting_with_word", Boolean.TRUE);
                        Fragment gameFragment = new Game();
                        gameFragment.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.main_fragment, gameFragment).commit();
                    }
                });
                return  itemView;
            }
        });
        return wordsView;
    }
}