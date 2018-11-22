package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Application;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.ScenarieHaandtering;

public class InsufflatorSimApp extends Application {

    public static ScenarieHaandtering scenarieHaandtering;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("DEBUG InsufflatorSimApp");
        scenarieHaandtering = ScenarieHaandtering.getInstance();

    }

}
