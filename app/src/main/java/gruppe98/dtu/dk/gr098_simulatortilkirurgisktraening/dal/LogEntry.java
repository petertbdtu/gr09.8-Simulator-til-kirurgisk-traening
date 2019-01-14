package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import android.icu.util.Calendar;

import java.time.ZonedDateTime;

public class LogEntry {

    // private deviceidentifier <--
    private Scenario loggedScenario;
    //dateTime formats er lidt fucked up...
    //long unixTime = System.currentTimeMillis();
    private long start;
    private long completed;
    private OutcomeOptions outcome;
    private String comment;

    public Scenario getLoggedScenario() {
        return loggedScenario;
    }

    public void setLoggedScenario(Scenario loggedScenario) {
        this.loggedScenario = loggedScenario;
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
}
