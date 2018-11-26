package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Application;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class InsufflatorSimApp extends Application {

//    public static DataHaandtering dataHaandtering;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("DEBUG InsufflatorSimApp");
        DataHaandtering.getInstance().init(getFilesDir().toString());
        TestingDataLayer();
    }

    private void TestingDataLayer() {
        DataHaandtering data = DataHaandtering.getInstance();
        if (!data.scenarieEksisterer("TestScenarie")){
            Scenario s1 = new Scenario();
            s1.setName("TestScenarie");

        }
    }

}
