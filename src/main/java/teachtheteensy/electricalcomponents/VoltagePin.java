package teachtheteensy.electricalcomponents;

/**
 * Pin qui impose un voltage
 */
public class VoltagePin extends Pin {

    /**
     * Le voltage à imposer
     */
    public final double voltage;

    public VoltagePin(ElectricalComponent owner, String name, double voltage, int index, double relativeX, double relativeY) {
        super(owner, name, index, relativeX, relativeY);
        this.voltage = voltage;
    }

    @Override
    public boolean forcesNodeName() {
        return true;
    }
}
