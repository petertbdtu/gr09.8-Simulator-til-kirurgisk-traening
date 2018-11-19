package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.ArrayList;

public interface DaoInterface {
    public ArrayList<Object> loadData();

    public void saveData(ArrayList<Object> data);

}
