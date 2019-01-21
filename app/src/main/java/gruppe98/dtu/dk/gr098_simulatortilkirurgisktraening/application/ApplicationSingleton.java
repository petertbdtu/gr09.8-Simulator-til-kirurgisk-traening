package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.DataAccessExternal;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.DataAccessInternal;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class ApplicationSingleton {

    private static final String DEVICES_FILENAME = "Devices.data";
    private static final String LOG_FILENAME = "LogFile.data";
    private static final String SCENARIOS_DIR = "/InsufflatorSimApp/scenarios/";

    private static ApplicationSingleton instance;

    private DataAccessInternal<ArrayList<LogEntry>> daoLogs;
    private DataAccessExternal<Scenario> daoScenarier;
    private DataAccessInternal<String> daoDevices;
    public ArrayList<String> connectedDevices;
    public Scenario activeScenario;
    private String scenarioDir;
    private String deviceDir;
    public WifiP2P WifiP2P;
    private String logDir;

    public static ApplicationSingleton getInstance() {
        if(instance == null)
            instance = new ApplicationSingleton();
        return instance;
    }

    private ApplicationSingleton() {
        daoScenarier = new DataAccessExternal<>();
        daoLogs = new DataAccessInternal<>();
        daoDevices = new DataAccessInternal<>();
        connectedDevices = new ArrayList<>();
    }

    public void init(String internalFilePath, String externalFilePath) {
        deviceDir = internalFilePath + "/" + DEVICES_FILENAME;
        logDir = internalFilePath + "/" +LOG_FILENAME;
        scenarioDir = externalFilePath + "/" + SCENARIOS_DIR;

        File dir = new File(scenarioDir);
        if(!dir.exists())
            dir.mkdirs();
    }

    public void createScenario(Scenario scenarie, String name) {
        Map<String,Scenario> tempScenarier = new HashMap<>();
        tempScenarier.put(name, scenarie);
        daoScenarier.saveDataExternalFiles(tempScenarier,scenarioDir);
    }

    public Map<String,Scenario> hentAlleScenarier(){
        Map<String,Scenario> tempScenarier = daoScenarier.loadDataExternalFiles(scenarioDir);

        if(tempScenarier == null)
            return new HashMap<>();
        return tempScenarier;
    }

    public Scenario hentScenarie(String navn) {
        return daoScenarier.getScenario(navn, scenarioDir);
    }

    public boolean scenarieEksisterer(String scenarioName) {
        return daoScenarier.fileExists(scenarioDir+scenarioName+".txt");
    }

    public String fjernScenarie(String scenarioName) {
        String tmpPath = scenarioDir+scenarioName+".txt";
        daoScenarier.removeFile(tmpPath);
        return tmpPath;
    }

    public ArrayList<LogEntry> hentLogs(String id) {
        Map<String,ArrayList<LogEntry>> tmpLogMap = daoLogs.loadData(logDir);
        if(tmpLogMap.containsKey(id))
            return tmpLogMap.get(id);
        return new ArrayList<>();
    }

    public void addLog(String id, String brugsNavn) {
        Map<String,ArrayList<LogEntry>> tmpLogMap = daoLogs.loadData(logDir);
        LogEntry newLog = new LogEntry(id,brugsNavn,System.currentTimeMillis());

        if(!tmpLogMap.containsKey(id))
            tmpLogMap.put(id,new ArrayList<LogEntry>());

        ArrayList<LogEntry> tmpListLogs = tmpLogMap.get(id);
        tmpListLogs.add(newLog);

        tmpLogMap.put(id,tmpListLogs);
        daoLogs.saveData(tmpLogMap,logDir);
    }

    public HashMap<String,String> getDevices(){
        return (HashMap<String,String>) daoDevices.loadData(deviceDir);
    }

    public void addDevice(String macAddress, String deviceName) {
        HashMap<String,String> tmpDevices = (HashMap<String,String>) daoDevices.loadData(deviceDir);
        tmpDevices.put(macAddress,deviceName);
        daoDevices.saveData(tmpDevices,deviceDir);
    }

    public void deleteDevice(String macAddress) {
        HashMap<String,String> tmpDevices = (HashMap<String,String>) daoDevices.loadData(deviceDir);
        if(tmpDevices.containsKey(macAddress)) {
            tmpDevices.remove(macAddress);
            daoDevices.saveData(tmpDevices, deviceDir);
        }
    }
}
