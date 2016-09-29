package stensig.hangman;

import android.app.Application;

import galgeleg.Galgelogik;

public class GameSingleton extends Application {

    private static Galgelogik gameLogic = null;

    public static Galgelogik getInstance() {
        if (gameLogic == null) {
            gameLogic = new Galgelogik();
        }
        return gameLogic;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gameLogic = new Galgelogik();
    }
}
