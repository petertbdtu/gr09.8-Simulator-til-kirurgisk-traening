package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

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
    private static ApplicationSingleton instance;

    private String filePath;
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

    public void init(String filePath) {
        this.filePath = filePath;
        try {
            dao.loadData(filePath + "/" + SCENARIO_FILENAME);
        } catch (java.lang.NullPointerException e) {
            Map<String,Scenario> tempList = new HashMap<>();
            dao.saveData(tempList, filePath + "/" + SCENARIO_FILENAME);
        }
        try {
        } catch (java.lang.NullPointerException e) {
            dao.loadData(filePath + "/" + LOG_FILENAME);
            Map<String,LogEntry> tempListLogs = new HashMap<>();
            dao.saveData(tempListLogs, filePath + "/" + LOG_FILENAME);
        }
    }

    public void opretScenarie(Scenario scenarie) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + SCENARIO_FILENAME);
        tempScenarier.put(scenarie.getName(), scenarie);
        dao.saveData(tempScenarier, filePath + "/" + SCENARIO_FILENAME);
    }

    public List<Scenario> hentAlleScenarier(){
        List<Scenario> tempScenarier = new ArrayList<Scenario>(dao.loadData(filePath + "/" + SCENARIO_FILENAME).values());
        if(tempScenarier == null)
            return new ArrayList<>();
        return tempScenarier;
    }

    public Scenario hentScenarie(String navn) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + SCENARIO_FILENAME);
        return tempScenarier.get(navn);
    }

    public boolean scenarieEksisterer(String navn) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + SCENARIO_FILENAME);
        return tempScenarier.containsKey(navn);
    }

    public void fjernScenarie(String s) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + SCENARIO_FILENAME);
        tempScenarier.remove(s);
        dao.saveData(tempScenarier, filePath + "/" + SCENARIO_FILENAME);
    }

    public List<LogEntry> hentAlleLogs() {
        List<LogEntry> logElementer = new ArrayList<>(dao.loadData(filePath + "/" + LOG_FILENAME).values());
        return  logElementer;
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
