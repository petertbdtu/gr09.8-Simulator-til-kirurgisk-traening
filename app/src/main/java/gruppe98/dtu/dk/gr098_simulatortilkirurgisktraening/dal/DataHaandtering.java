package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHaandtering {
    private static DataHaandtering instans = new DataHaandtering();
    private static DataAccessLayer<Scenario> daoScenarier;
    private String scenarierFileName = "ScenarierFil.data";
    private String filePath;

    public static DataHaandtering getInstance() {
        if(instans == null)
            instans = new DataHaandtering();
        return instans;
    }

    private DataHaandtering() {
        daoScenarier = new DataAccessLayer<Scenario>();
    }

    public void init(String filePath) {
        this.filePath = filePath;
        try {
            daoScenarier.loadData(filePath + "/" + scenarierFileName);
        } catch (java.lang.NullPointerException e) {
            Map<String,Scenario> tempList = new HashMap<>();
            daoScenarier.saveData(tempList, filePath + "/" + scenarierFileName);
        }
    }

    public void opretScenarie(Scenario scenarie) {
        Map<String,Scenario> tempScenarier = daoScenarier.loadData(filePath + "/" + scenarierFileName);
        tempScenarier.put(scenarie.getName(), scenarie);
        daoScenarier.saveData(tempScenarier, filePath + "/" + scenarierFileName);
    }

    public List<Scenario> hentAlleScenarier(){
        List<Scenario> tempScenarier = new ArrayList<Scenario>(daoScenarier.loadData(filePath + "/" + scenarierFileName).values());
        return tempScenarier;
    }

    public Scenario hentScenarie(String navn) {
        Map<String,Scenario> tempScenarier = daoScenarier.loadData(filePath + "/" + scenarierFileName);
        return tempScenarier.get(navn);
    }

    public boolean scenarieEksisterer(String navn) {
        Map<String,Scenario> tempScenarier = daoScenarier.loadData(filePath + "/" + scenarierFileName);
        return tempScenarier.containsKey(navn);
    }

    public void fjernScenarie(String s) {
        Map<String,Scenario> tempScenarier = daoScenarier.loadData(filePath + "/" + scenarierFileName);
        tempScenarier.remove(s);
        daoScenarier.saveData(tempScenarier, filePath + "/" + scenarierFileName);
    }
}
