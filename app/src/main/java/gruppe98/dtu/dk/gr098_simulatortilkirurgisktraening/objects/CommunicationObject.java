package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import java.io.Serializable;

public class CommunicationObject implements Serializable {

    private String recipientMacAddress;
    private String senderMacAddress;
    private Scenario scenario;

    public CommunicationObject(String recipientMacAddress, String senderMacAddress, Scenario scenario) {
        this.recipientMacAddress = recipientMacAddress;
        this.senderMacAddress = senderMacAddress;
        this.scenario = scenario;
    }

    public String getRecipientMacAddress() {
        return recipientMacAddress;
    }

    public void setRecipientMacAddress(String recipientMacAddress) {
        this.recipientMacAddress = recipientMacAddress;
    }

    public String getSenderMacAddress() {
        return senderMacAddress;
    }

    public void setSenderMacAddress(String senderMacAddress) {
        this.senderMacAddress = senderMacAddress;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

}
