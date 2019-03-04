package teachtheteensy.electricalcomponents.simulation;

import org.knowm.jspice.component.Component;
import org.knowm.jspice.component.source.ArbitraryUtils;
import org.knowm.jspice.component.source.DCVoltageArbitrary;
import org.knowm.jspice.component.source.Source;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;
import teachtheteensy.electricalcomponents.Pin;

import java.util.Map;

/**
 * Source de tension arbitraire qui permet de modéliser une pin de Teensy qui fournit une tension.
 * Utiliser {@link PinSource#setValue(double)} pour changer la valeur de la tension
 */
public class PinSource extends DCVoltageArbitrary {
    private double value;

    public PinSource(Pin pin) {
        super(pin.toNodeName(), "<none>");
    }

    public void setValue(double value) {
        this.value = value;
        setSweepValue(value);
    }

    @Override
    public double getSweepableValue() {
        return value;
    }

    public double getValue() {
        return value;
    }

    // Modifié pour pouvoir choisir la valeur programmatiquement (cf DCVoltageArbitrary)
    @Override
    public void stampRHS(double[] RHS, DCOperatingPointResult dcOperatingPointResult, Map<String, Integer> nodeID2ColumnIdxMap, String[] nodes, Double timeStep) {
        // create stamp
        double[] stamp = new double[3];

        stamp[0] = 0.0;
        stamp[1] = 0.0;

        double value = getValue();
        stamp[2] = value;

        // apply stamp
        int idxA = nodeID2ColumnIdxMap.get(nodes[0]);
        int idxB = nodeID2ColumnIdxMap.get(nodes[1]);
        int idxI = nodeID2ColumnIdxMap.get(getId());
        RHS[idxA] += stamp[0];
        RHS[idxB] += stamp[1];
        RHS[idxI] += stamp[2];

//        setValue(value);
   //     setSweepValue(value);
    }
}
