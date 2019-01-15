package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;
import java.io.Serializable;

public class Scenario implements Serializable {
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

    /*public Scenario(byte[] byteArray) {
        byte[] b = new byte[1024];




        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        this.actualPressure = byteBuffer.getInt();
        this.targetPressure = byteBuffer.getInt();
        this.actualFlowRate = byteBuffer.getInt();
        this.targetFlowRate = byteBuffer.getInt();
        this.volume = byteBuffer.getInt();
    }*/

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
        if(actualPressure > 99) {actualPressure = 99;}
        if(actualPressure < 0) {actualPressure = 0;}
        this.actualPressure = actualPressure;
    }

    public int getTargetPressure() {
        return targetPressure;
    }

    public void setTargetPressure(int targetPressure) {
        if(targetPressure > 99) {targetPressure = 99;}
        if(targetPressure < 0) {targetPressure = 0;}
        this.targetPressure = targetPressure;
    }

    public int getActualFlowRate() {
        return actualFlowRate;
    }

    public void setActualFlowRate(int actualFlowRate) {
        if(actualFlowRate > 99) {actualFlowRate = 99;}
        if(actualFlowRate < 0) {actualFlowRate = 0;}
        this.actualFlowRate = actualFlowRate;
    }

    public int getTargetFlowRate() {
        return targetFlowRate;
    }

    public void setTargetFlowRate(int targetFlowRate) {
        if(targetFlowRate > 99) {targetFlowRate = 99;}
        if(targetFlowRate < 0) {targetFlowRate = 0;}
        this.targetFlowRate = targetFlowRate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if(volume > 99) {volume = 99;}
        if(volume < 0) {volume = 0;}
        this.volume = volume;
    }

    /*public byte[] toByteArray() {
        byte[] bb = new byte[20];


        for(int i = 0 ; i < 20 ; i++) {
            if(i < 4) {

            }
        }

        return ByteBuffer.allocate(1024-17)
                .putInt(actualPressure)
                .putInt(targetPressure)
                .putInt(actualFlowRate)
                .putInt(targetFlowRate)
                .putInt(volume)
                .array();
    }*/
}
