package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHaandtering {
    private static DataHaandtering instans = new DataHaandtering();
    private static DataAccessLayer dao;
    private String scenarierFileName = "ScenarierFil.data";
    private String logsFileName = "LogsFil.data";
    private String filePath;
    private ArrayList<String> forbundneTablets;

    public static DataHaandtering getInstance() {
        if(instans == null)
            instans = new DataHaandtering();
        return instans;
    }

    private DataHaandtering() {
        dao = new DataAccessLayer<>();
    }

    public void init(String filePath) {
        this.filePath = filePath;
        try {
            dao.loadData(filePath + "/" + scenarierFileName);
        } catch (java.lang.NullPointerException e) {
            Map<String,Scenario> tempList = new HashMap<>();
            dao.saveData(tempList, filePath + "/" + scenarierFileName);
        }
        try {
        } catch (java.lang.NullPointerException e) {
            dao.loadData(filePath + "/" + logsFileName);
            Map<String,LogEntry> tempListLogs = new HashMap<>();
            dao.saveData(tempListLogs, filePath + "/" + logsFileName);
        }
    }

    public void opretScenarie(Scenario scenarie) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + scenarierFileName);
        tempScenarier.put(scenarie.getName(), scenarie);
        dao.saveData(tempScenarier, filePath + "/" + scenarierFileName);
    }

    public ArrayList<String> hentForbundneTablets(){
        forbundneTablets = new ArrayList<>();
        forbundneTablets.add("Samsund");
        forbundneTablets.add("oneplus");
        return forbundneTablets;
    }


    public List<Scenario> hentAlleScenarier(){
        List<Scenario> tempScenarier = new ArrayList<Scenario>(dao.loadData(filePath + "/" + scenarierFileName).values());
        return tempScenarier;
    }

    public Scenario hentScenarie(String navn) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + scenarierFileName);
        return tempScenarier.get(navn);
    }

    public boolean scenarieEksisterer(String navn) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + scenarierFileName);
        return tempScenarier.containsKey(navn);
    }

    public void fjernScenarie(String s) {
        Map<String,Scenario> tempScenarier = dao.loadData(filePath + "/" + scenarierFileName);
        tempScenarier.remove(s);
        dao.saveData(tempScenarier, filePath + "/" + scenarierFileName);
    }

    public List<LogEntry> hentAlleLogs() {
        List<LogEntry> logElementer = new ArrayList<>(dao.loadData(filePath + "/" + logsFileName).values());
        return  logElementer;
    }
}
