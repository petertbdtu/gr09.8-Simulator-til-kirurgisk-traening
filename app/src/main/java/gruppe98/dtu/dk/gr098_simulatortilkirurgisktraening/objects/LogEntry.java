package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

public class LogEntry {

    public LogEntry(String deviceId, String scenarioNavn, long start, long completed){
        this.deviceId = deviceId;
        this.scenarioNavn = scenarioNavn;
        this.start = start;
        this.completed = completed;
    }

    private String deviceId;
    private String scenarioNavn;
    //dateTime formats er lidt fucked up...
    //long unixTime = System.currentTimeMillis();
    private long start;
    private long completed;
    private OutcomeOptions outcome;
    private String comment;

    public String getScenarioNavn() {
        return scenarioNavn;
    }

    public void setScenarioNavn(String scenarioNavn) {
        this.scenarioNavn = scenarioNavn;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public OutcomeOptions getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeOptions outcome) {
        this.outcome = outcome;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
