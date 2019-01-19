package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.DataAccess;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class ApplicationSingleton {

    private static final String SCENARIO_FILENAME = "ScenarioFile.data";
    private static final String LOG_FILENAME = "LogFile.data";
    private static final String EXTERNAL_DIR = "/InsufflatorSimApp";
    private static ApplicationSingleton instance;

    private String internalStorageFilePath;
    private String externalStorageFilePath;
    private DataAccess dao;

    public Scenario aktivtScenarie;
    public WifiP2P WifiP2P;
    //public WifiP2P insufflatorWifiP2P;

    private HashMap<String, String> knownDevices;

    public static ApplicationSingleton getInstance() {
        if(instance == null)
            instance = new ApplicationSingleton();
        return instance;
    }

    private ApplicationSingleton() {
        dao = new DataAccess<>();
    }


    public void init(String internalFilePath, String externalFilePath) {
        this.internalStorageFilePath = internalFilePath;
        this.externalStorageFilePath = externalFilePath;

        try {
            dao.loadData(internalFilePath + "/" + LOG_FILENAME);
        } catch (java.lang.NullPointerException e) {
            Map<String,List <LogEntry>> tempListLogs = new HashMap<>();
            dao.saveData(tempListLogs, internalFilePath + "/" + LOG_FILENAME);
        }
    }

    public void opretScenarie(Scenario scenarie, String name) {
        Map<String,Scenario> tempScenarier = new HashMap<>();
        tempScenarier.put(name, scenarie);
        dao.saveDataExternalFiles(tempScenarier,externalStorageFilePath+EXTERNAL_DIR+"/Available Scenarios");
    }

    public Map<String,Scenario> hentAlleScenarier(){
        Map<String,Scenario> tempScenarier = dao.loadDataExternalFiles(new File(externalStorageFilePath+EXTERNAL_DIR+"/Available Scenarios"));

        if(tempScenarier == null)
            return new HashMap<>();
        return tempScenarier;
    }

    public Scenario hentScenarie(String navn) {
        return dao.getScenario(navn, externalStorageFilePath+EXTERNAL_DIR+"/Available Scenarios/");
    }

    public boolean scenarieEksisterer(String navn) {
        return dao.fileExists(externalStorageFilePath+EXTERNAL_DIR+navn+".txt");
    }

    public String fjernScenarie(String s) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+EXTERNAL_DIR+"/Available Scenarios/"+s+".txt";
        dao.removeFile(new File(filePath));
        return filePath;
    }

    public List<LogEntry> hentAlleLogs() {
        List<LogEntry> logElementer = new ArrayList<>(dao.loadData(internalStorageFilePath + "/" + LOG_FILENAME).values());
        return  logElementer;
    }

    public void initExternalStorage() {
        String path = externalStorageFilePath+EXTERNAL_DIR+"/Available Scenarios";
        System.out.println("DEBUG: "+path);
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        } else {
            System.out.println("DEBUG: dir exist");
        }
        createTestScenario();
    }

    private void createTestScenario() {
        Scenario s1 = new Scenario();
        s1.setTargetFlowRate(20);
        s1.setActualFlowRate(30);
        s1.setTargetPressure(40);
        s1.setActualPressure(50);
        s1.setVolume(60);
        s1.setGasSupply(32);
        s1.setTubeBlockedLED(false);
        s1.setOverPressureLED(true);
        opretScenarie(s1,"TestScenarie");
    }

    public HashMap<String, String> getKnownDevices() {
        if(knownDevices == null){
            knownDevices = new HashMap<>();
            knownDevices.put("address", "name");
        }
        return knownDevices;
    }

    public void addKnownDevice(String key, String value) {
        if(knownDevices.isEmpty()){
            knownDevices = new HashMap<>();
        }
        knownDevices.put(key, value);
    }
}
