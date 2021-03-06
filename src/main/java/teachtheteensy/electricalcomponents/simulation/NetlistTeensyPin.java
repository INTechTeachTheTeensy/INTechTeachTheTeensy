package teachtheteensy.electricalcomponents.simulation;

import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDCVoltageArbitrary;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.electricalcomponents.VoltagePin;

import java.util.List;

/**
 * Elément JSpice qui représente une pin de Teensy
 */
public class NetlistTeensyPin extends NetlistDCVoltageArbitrary {
    private final Pin pin;
    /**
     * Composant JSpice qui permet de contrôler le voltage de cette pin si besoin
     */
    private final PinSource source;

    public NetlistTeensyPin(Pin pin) {
        super(new PinSource(pin), "");
        this.pin = pin;
        this.source = (PinSource)getComponent();
        if(pin instanceof VoltagePin) {
            source.setValue(((VoltagePin) pin).voltage);
        } else {
            source.setValue(0.0);
        }
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
