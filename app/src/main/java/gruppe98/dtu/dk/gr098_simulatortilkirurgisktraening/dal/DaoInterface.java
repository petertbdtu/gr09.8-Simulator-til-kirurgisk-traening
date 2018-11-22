package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.util.List;

public interface DaoInterface<E> {
    public List loadData();

    public void saveData(List data);

}
