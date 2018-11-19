package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal;

public class Scenario {
    private String name;
    private int actualPressure;
    private int targetPressure;
    private int actualFlowRate;
    private int targetFlowRate;
    private int volume;

    public Scenario() {
        this.name = "";
        this.actualPressure = 0;
        this.targetPressure = 0;
        this.actualFlowRate = 0;
        this.targetFlowRate = 0;
        this.volume = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActualPressure() {
        return actualPressure;
    }

    public void setActualPressure(int actualPressure) {
        this.actualPressure = actualPressure;
    }

    public int getTargetPressure() {
        return targetPressure;
    }

    public void setTargetPressure(int targetPressure) {
        this.targetPressure = targetPressure;
    }

    public int getActualFlowRate() {
        return actualFlowRate;
    }

    public void setActualFlowRate(int actualFlowRate) {
        this.actualFlowRate = actualFlowRate;
    }

    public int getTargetFlowRate() {
        return targetFlowRate;
    }

    public void setTargetFlowRate(int targetFlowRate) {
        this.targetFlowRate = targetFlowRate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
