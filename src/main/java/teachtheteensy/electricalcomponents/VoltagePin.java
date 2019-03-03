package teachtheteensy.electricalcomponents;

public class VoltagePin extends Pin {

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
