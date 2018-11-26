package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;
import java.util.List;

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
            List<Scenario> tempList = new ArrayList<>();
            daoScenarier.saveData(tempList, filePath + "/" + scenarierFileName);
        }

    }

    public void opretScenarie(Scenario scenarie) {
        List<Scenario> tempScenarier = daoScenarier.loadData(filePath + "/" + scenarierFileName);
        tempScenarier.add(scenarie);
        daoScenarier.saveData(tempScenarier, filePath + "/" + scenarierFileName);
    }

    public List<Scenario> hentAlleScenarier(){
        return daoScenarier.loadData(filePath + "/" + scenarierFileName);
    }

    public Scenario hentScenarie(String navn) {
        List<Scenario> tempScenarier = daoScenarier.loadData(scenarierFileName);
        for(Scenario scenarie: tempScenarier) {
            if(scenarie.getName().equals(navn)) {
                return scenarie;
            }
        }
        return null;
    }

    public boolean scenarieEksisterer(String navn) {
        List<Scenario> tempScenarier = daoScenarier.loadData(scenarierFileName);
        for(Scenario scenarie: tempScenarier) {
            if(scenarie.getName().equals(navn)) {
                return true;
            }
        }
        return false;
    }
}
