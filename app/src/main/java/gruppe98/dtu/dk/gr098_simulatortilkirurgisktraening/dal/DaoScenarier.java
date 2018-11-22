package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;


import java.util.ArrayList;
import java.util.List;


public class DaoScenarier implements DaoInterface {
    @Override
    public List loadData() {
        ArrayList list = new ArrayList<Scenario>();

        return list;
    }
    @Override
    public void saveData(List data) {
        ArrayList<Scenario> array = (ArrayList<Scenario>) data;
        for(Scenario scenario : array) {
            saveScenario(scenario);
        }
    }

    private void saveScenario(Scenario scenario) {

    }

}
