package teachtheteensy.electricalcomponents.simulation;

import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDCVoltageArbitrary;
import teachtheteensy.electricalcomponents.Pin;

import java.util.List;

public class NetlistTeensyPin extends NetlistDCVoltageArbitrary {
    private final Pin pin;
    private final PinSource source;

    public NetlistTeensyPin(Pin pin) {
        super(new PinSource(pin), "");
        this.pin = pin;
        this.source = (PinSource)getComponent();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NetlistTeensyPin) {
            return ((NetlistTeensyPin) obj).getId().equals(getId());
        }
        return false;
    }

    public PinSource getSource() {
        return source;
    }

    public Pin getPin() {
        return pin;
    }
}
