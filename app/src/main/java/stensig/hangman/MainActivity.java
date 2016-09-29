package stensig.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Log.d(LOG_TAG, "starting fresh instance; loading welcome fragment");
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, new Welcome()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_game) {
            Log.d(LOG_TAG, "starting new game from menu");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new Game()).commit();
        } else if (item.getItemId() == R.id.menu_item_close_game) {
            Log.d(LOG_TAG, "ending game from menu");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new Welcome()).commit();
        } else if(item.getItemId() == R.id.menu_item_view_words) {
            Log.d(LOG_TAG, "displaying list of possible words");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new ViewWords()).addToBackStack(null).commit();
        }
        return true;
    }
}
