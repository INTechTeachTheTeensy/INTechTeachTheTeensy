package teachtheteensy.electricalcomponents.models;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.electricalcomponents.Teensy;

public class TeensyModel extends ElectricalModel {

    private final Teensy teensy;

    public TeensyModel(Teensy teensy) {
        super(teensy);
        this.teensy = teensy;
    }

    @Override
    protected double getCurrentLeavingPin(Pin pin, double simulationTime) {
        return 0;
    }

    @Override
    protected double getPotentialAtPin(Pin pin, double simulationTime) {
        return 0;
    }

    @Override
    protected void step(Pin pin, double simulationTime) {

    }

    @Override
    public double calculateTensionAtPin(double current, double prevTension, Pin pin, double stepTime) {
        return 0;
    }

    @Override
    public double calculateCurrentAtPin(double prevCurrent, double tension, Pin pin, double stepTime) {
        return 0;
    }
}
