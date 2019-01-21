package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DataAccessInternal<E> {

    public Map<String,E> loadData(String fileName) {
        Map<String, E> data = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            data = (HashMap<String,E>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void saveData(Map<String,E> data, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
