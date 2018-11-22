package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

//import android.os.Environment;
//import java.io.File;
import java.util.ArrayList;

public class ScenarieHaandtering {
    private static final ScenarieHaandtering instans = new ScenarieHaandtering();
    private static ArrayList<Scenario> tilgaengeligeScenarier;
    private static DaoScenarier dao;
    //public static String PATH;


    public static ScenarieHaandtering getInstance() {
        return instans;
    }

    private ScenarieHaandtering() {
        tilgaengeligeScenarier = new ArrayList<>();
        dao = new DaoScenarier();
       /*PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/availableScenarios";
        System.out.println("DEBUG"+PATH);
        File dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdir();
        }
        */
    }

    public void indlaesScenarier() {
        tilgaengeligeScenarier = (ArrayList<Scenario>) dao.loadData();
    }

    public void opretScenarie(Scenario scenarie) {
        tilgaengeligeScenarier.add(scenarie);
        gemScenarier();
    }

    public void gemScenarier() {
        dao.saveData(tilgaengeligeScenarier);
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
