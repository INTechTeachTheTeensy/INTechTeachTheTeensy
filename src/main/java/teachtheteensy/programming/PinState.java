package teachtheteensy.programming;

public enum PinState {
    HIGH(3.3),
    LOW(0.0),
    BURNT(0.0),
    UNKNOWN(0.0);

    private double volts;

    PinState(double volts) {
        this.volts = volts;
    }

    public double inVolts() {
        return volts;
    }
}
