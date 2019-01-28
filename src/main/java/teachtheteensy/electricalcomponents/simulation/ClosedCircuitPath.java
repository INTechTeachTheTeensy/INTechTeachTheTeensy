package teachtheteensy.electricalcomponents.simulation;

import teachtheteensy.electricalcomponents.ElectricalComponent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClosedCircuitPath {

    private List<ElectricalComponent> elements;
    private double currentInBranch = 0.0;
    private double tension = 0.0;

    public ClosedCircuitPath() {
        elements = new ArrayList<>();
    }

    /**
     *
     * On ne peut imposer que la tension OU le courant (pas les deux!)
     * @param tension
     */
    public void imposeTension(double tension) {
        this.tension = tension;
        currentInBranch = 0;

    }

    /**
     *
     * On ne peut imposer que la tension OU le courant (pas les deux!)
     * @param current
     */
    public void imposeCurrent(double current) {
        this.currentInBranch = current;
    }
}
