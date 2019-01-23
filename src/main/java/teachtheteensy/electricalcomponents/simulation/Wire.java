package teachtheteensy.electricalcomponents.simulation;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;

public class Wire {

    private final Pin nodeA;
    private final Pin nodeB;
    private double current;
    private double potential;

    public Wire(Pin nodeA, Pin nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public Pin getNodeA() {
        return nodeA;
    }

    public Pin getNodeB() {
        return nodeB;
    }

    public double calculateCurrent(double tension, double current, double stepTime) {
        return 0;
    }
}
