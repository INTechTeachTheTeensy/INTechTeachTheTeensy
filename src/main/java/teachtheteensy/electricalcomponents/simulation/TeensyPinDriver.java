package teachtheteensy.electricalcomponents.simulation;

import org.knowm.jspice.simulate.transientanalysis.driver.Driver;

import java.math.BigDecimal;

/**
 * Permet de contrôler le voltage d'une pin
 * @author jglrxavpok
 */
public class TeensyPinDriver extends Driver {


    public TeensyPinDriver(String id) {
        super(id, 0.0, "0.0", 3.3, "0");
    }

    @Override
    public double getSignal(BigDecimal time) {
        return 0; // TODO: vrai signal
    }
}
