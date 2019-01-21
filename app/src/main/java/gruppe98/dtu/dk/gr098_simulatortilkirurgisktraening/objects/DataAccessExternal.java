package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataAccessExternal<E> {

    public Map<String, Scenario> loadDataExternalFiles(String dir) {
        Map<String, Scenario> data = new HashMap<>();
        JSONObject jsonObject;
        Scenario tempScenario;

        File directory = new File(dir);
        if(directory.listFiles() != null)
            for(File file: directory.listFiles()) {
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
                    tempScenario = scenarioFromJson(jsonObject);
                    data.put(file.getName().replace(".txt",""), tempScenario);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        return data;
    }

    private Scenario scenarioFromJson(JSONObject jsonObject) throws JSONException {
        Scenario scenario = new Scenario();
                scenario.setActualFlowRate(jsonObject.getInt("actualFlowRate"));
                scenario.setActualPressure(jsonObject.getInt("actualPressure"));
                scenario.setTargetFlowRate(jsonObject.getInt("targetFlowRate"));
                scenario.setTargetPressure(jsonObject.getInt("targetPressure"));
                scenario.setVolume(jsonObject.getInt("volume"));
                scenario.setGasSupply(jsonObject.getInt("gasSupply"));
                scenario.setOverPressureLED(jsonObject.getBoolean("overPressureLED"));
                scenario.setTubeBlockedLED(jsonObject.getBoolean("tubeBlockedLED"));
                return scenario;
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
          //  json.put("name", value.getName());
            json.put("actualPressure", value.getActualPressure());
            json.put("targetPressure",value.getTargetPressure());
            json.put("actualFlowRate",value.getActualFlowRate());
            json.put("targetFlowRate",value.getTargetFlowRate());
            json.put("volume",value.getVolume());
            json.put("gasSupply", value.getGasSupply());
            json.put("tubeBlockedLED", value.isTubeBlockedLED());
            json.put("overPressureLED", value.isOverPressureLED());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void removeFile(String path) {
        new File(path).delete();
    }

    public Scenario getScenario(String navn, String path) {

        if(fileExists(path+navn+".txt")) {
            try {
                String json = "";
                BufferedReader reader = new BufferedReader(new FileReader(new File(path + navn + ".txt")));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = reader.readLine();
                }

                json = sb.toString();
                reader.close();

                return scenarioFromJson(new JSONObject(json));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    return null;
    }

    public boolean fileExists(String path) {
        return new File(path).exists();
    }
}
