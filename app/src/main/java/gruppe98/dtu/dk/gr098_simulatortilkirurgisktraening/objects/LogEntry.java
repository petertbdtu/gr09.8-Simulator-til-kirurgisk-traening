package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import java.io.Serializable;

public class LogEntry implements Serializable {

    public LogEntry(String deviceId, String scenarioNavn, long start){
        this.deviceId = deviceId;
        this.scenarioNavn = scenarioNavn;
        this.start = start;
    }

    private String deviceId;
    private String scenarioNavn;
    private long start;

    public String getScenarioNavn() {
        return scenarioNavn;
    }

    public long getStart() {
        return start;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
