package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;

public class DaoScenarier implements DaoInterface {
    @Override
    public ArrayList<Object> loadData() {
        ArrayList list = new ArrayList();
        Scenario testScenario = new Scenario();
        testScenario.setName("Test scenarie");
        testScenario.setTargetPressure(10);
        testScenario.setActualPressure(25);
        list.add(testScenario);
        return list;
    }

    @Override
    public void saveData(ArrayList<Object> data) {

    }
}
