package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

public class Scenario {
    private String name;
    private int actualPressure;
    private int targetPressure;
    private int actualFlowRate;
    private int targetFlowRate;
    private int volume;

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
