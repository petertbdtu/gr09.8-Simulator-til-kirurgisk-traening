package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class DataAccessLayer<E> {

    public Map<String, E> loadData(String fileName) {
        Map<String,E> data = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            data = (HashMap<String,E>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {}
        return data;
    }

    public void saveData(Map<String,E> data, String fileName) {
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
        }catch (Exception e){}
    }
}
