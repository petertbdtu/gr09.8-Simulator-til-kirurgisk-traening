package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
/*
import static gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.ScenarieHaandteringAlternative.PATH;
*/
public class DaoScenarierAlternative {
    /*
    public List loadData() {
        ArrayList list = new ArrayList<Scenario>();

        File rootFolder = new File(PATH);
        File[] fileList = rootFolder.listFiles();
        if(!(fileList==null)) {
            for (File f : fileList) {
                list.add(loadScenario(f));
            }
        }
        return list;
    }

    public void saveData(List data) {
        ArrayList<Scenario> array = (ArrayList<Scenario>) data;
        for(Scenario scenario : array) {
            saveScenario(scenario);
        }
    }

    private void saveScenario(Scenario scenario) {
        FileOutputStream fos = null;
        File file = new File(PATH+"/"+scenario.getName());
        try
        {
            fos = new FileOutputStream(file);
            new ObjectOutputStream(fos).writeObject(scenario);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Scenario loadScenario(File file) {

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Scenario)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    } */
}
