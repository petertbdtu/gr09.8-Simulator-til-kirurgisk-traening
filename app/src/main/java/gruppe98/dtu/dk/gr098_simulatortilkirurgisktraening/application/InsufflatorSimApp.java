package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

import android.app.Application;
import android.os.Environment;

public class InsufflatorSimApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationSingleton.getInstance().init(this.getFilesDir().getPath(), Environment.getExternalStorageDirectory().getAbsolutePath());
    }
}
