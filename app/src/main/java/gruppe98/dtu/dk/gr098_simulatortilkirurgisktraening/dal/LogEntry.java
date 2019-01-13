package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

import java.time.ZonedDateTime;

public class LogEntry {

    // private deviceidentifier <--
    private Scenario loggedScenario;
    //dateTime formats er lidt fucked up...
    private int start;
    private int completed;
    private OutcomeOptions outcome;
    private String comment;

    public Scenario getLoggedScenario() {
        return loggedScenario;
    }

    public void setLoggedScenario(Scenario loggedScenario) {
        this.loggedScenario = loggedScenario;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
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
