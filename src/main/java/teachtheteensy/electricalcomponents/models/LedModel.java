package teachtheteensy.electricalcomponents.models;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Led;
import teachtheteensy.electricalcomponents.Pin;

public class LedModel extends ElectricalModel {
    private final Pin plus;
    private final Pin minus;
    private final Led led;
    private boolean isOn;

    public LedModel(Led led) {
        super(led);
        this.led = led;
        this.plus = led.getPlusPin();
        this.minus = led.getMinusPin();
    }

    @Override
    protected double getCurrentLeavingPin(Pin pin, double simulationTime) {
        double current = pin.getConnections().stream()
                .filter(p -> p != pin)
                .mapToDouble(p -> p.getOwner().getElectricalModel().getCurrentLeavingPin(pin, simulationTime))
                .sum();
        return -current; // loi des noeuds
    }

    @Override
    protected double getPotentialAtPin(Pin pin, double simulationTime) {
        return 0;
    }

    @Override
    protected void step(Pin pin, double simulationTime) {
        if(plus.hasAtLeastOneConnection() && minus.hasAtLeastOneConnection()) {

        }
    }

    @Override
    public void reset() {
        isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }
}
