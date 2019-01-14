package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

import android.app.Application;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class InsufflatorSimApp extends Application {

    public static Scenario aktivtScenarie;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("---------------> DEBUG InsufflatorSimApp" + this.getFilesDir().getPath());
        DataHaandtering.getInstance().init(this.getFilesDir().getPath());
        TestingDataLayer();
    }

    private void TestingDataLayer() {
        DataHaandtering data = DataHaandtering.getInstance();
        if (!data.scenarieEksisterer("TestScenarie")){
            Scenario s1 = new Scenario();
            s1.setName("TestScenarie");
            s1.setTargetFlowRate(20);
            s1.setActualFlowRate(30);
            s1.setTargetPressure(40);
            s1.setActualPressure(50);
            s1.setVolume(60);
            data.opretScenarie(s1);
        }
    }

}
