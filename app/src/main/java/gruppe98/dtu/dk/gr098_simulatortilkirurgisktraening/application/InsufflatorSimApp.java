package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

import android.app.Application;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public class InsufflatorSimApp extends Application {

    //

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationSingleton.getInstance().init(this.getFilesDir().getPath());
        TestingDataLayer();
    }

    private void TestingDataLayer() {
        ApplicationSingleton data = ApplicationSingleton.getInstance();
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
