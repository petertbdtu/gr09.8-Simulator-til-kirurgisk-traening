package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;

public class ScenarieHaandtering {
    private static final ScenarieHaandtering instans = new ScenarieHaandtering();
    private static ArrayList<Scenario> tilgaengeligeScenarier;
    private static DaoScenarier dao;


    public static ScenarieHaandtering getInstance() {
        return instans;
    }

    private ScenarieHaandtering() {
        tilgaengeligeScenarier = new ArrayList<>();
        dao = new DaoScenarier();
    }

    public void hentScenarier() {
        for (Object scenarie : dao.loadData()) {
            tilgaengeligeScenarier.add((Scenario) scenarie);
        }
    }

    public void opretScenarie(Scenario scenarie) {


        tilgaengeligeScenarier.add(scenarie);
        gemScenarier();
    }

    public void gemScenarier() {


            ArrayList<Object> list = new ArrayList<>();

        for(Scenario scenarie: tilgaengeligeScenarier) {
            list.add(scenarie);
        }
        dao.saveData(list);
    }

    public ArrayList<Scenario> hentAlleScenarier(){
        return tilgaengeligeScenarier;
    }

    public Scenario hentScenarie(String navn) {
        for(Scenario scenarie: tilgaengeligeScenarier) {
            if(scenarie.getName().equals(navn)) {
                return scenarie;
            }
        }
        return null;
    }

    public boolean scenarieEksisterer(String navn) {
        for(Scenario scenarie: tilgaengeligeScenarier) {
            if(scenarie.getName().equals(navn)) {
                return true;
            }
        }
        return false;
    }
}
