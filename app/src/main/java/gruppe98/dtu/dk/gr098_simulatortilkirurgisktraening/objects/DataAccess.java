package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;


public class DataAccess<E> {

    public Map<String, Scenario> loadDataExternalFiles(File dir) {
        Map<String, Scenario> data = new HashMap<>();
        JSONObject jsonObject;
        Scenario scenario;
        for(File file:dir.listFiles()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String json = "";
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = reader.readLine();
                }
                json = sb.toString();
                reader.close();
                jsonObject = new JSONObject(json);
                /*scenario = new Scenario();
                scenario.setName(jsonObject.getString("name"));
                scenario.setActualFlowRate(jsonObject.getInt("actualFlowRate"));
                scenario.setActualPressure(jsonObject.getInt("actualPressure"));
                scenario.setTargetFlowRate(jsonObject.getInt("targetFlowRate"));
                scenario.setTargetPressure(jsonObject.getInt("targetPressure"));
                scenario.setVolume(jsonObject.getInt("volumen"));
                System.out.println("boellehat "+jsonObject.toString());
                System.out.println("DEBUG: boellehat "+file.getName());*/

                data.put(jsonObject.getString("name"), scenarioFromJson(jsonObject));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public Map<String,E> loadData(String fileName) {

        Map<String, E> data = new HashMap<>();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);

        ObjectInputStream is = new ObjectInputStream(fis);
           data = (HashMap<String,E>) is.readObject();
           is.close();
           fis.close();
        } catch (Exception e) {}

        return data;
    }

    private Scenario scenarioFromJson(JSONObject jsonObject) throws JSONException {

        Scenario scenario = new Scenario();
                scenario.setName(jsonObject.getString("name"));
                scenario.setActualFlowRate(jsonObject.getInt("actualFlowRate"));
                scenario.setActualPressure(jsonObject.getInt("actualPressure"));
                scenario.setTargetFlowRate(jsonObject.getInt("targetFlowRate"));
                scenario.setTargetPressure(jsonObject.getInt("targetPressure"));
                scenario.setVolume(jsonObject.getInt("volume"));
        System.out.println("DEBUG: "+scenario.getName()+" "+scenario.getVolume());
                return scenario;
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

    public void saveDataExternalFiles(Map<String, E> data, String dir) {

        for (Map.Entry<String, E> entry : data.entrySet()) {
            saveScenario(dir+"/"+entry.getKey(),entry.getValue());
        }


    }

    private void saveScenario(String path, E value) {
        FileOutputStream fos = null;
        File file = new File(path+".txt");
        try
        {
            FileWriter fw = new FileWriter(file);

            fw.write(createJSON((Scenario)value).toString(2));
            fw.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createJSON(Scenario value) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", value.getName());
            json.put("actualPressure", value.getActualPressure());
            json.put("targetPressure",value.getTargetPressure());
            json.put("actualFlowRate",value.getActualFlowRate());
            json.put("targetFlowRate",value.getTargetFlowRate());
            json.put("volume",value.getVolume());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void removeFile(File file) {
        file.delete();
    }
}
