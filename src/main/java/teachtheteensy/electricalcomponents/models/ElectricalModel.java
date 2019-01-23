package teachtheteensy.electricalcomponents.models;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;

public abstract class ElectricalModel {

    protected ElectricalComponent component;

    public ElectricalModel(ElectricalComponent component) {
        this.component = component;
    }

    /**
     * Quel est le courant que cette pin impose ? Renvoyer Double.NaN si cette pin n'impose aucun courant
     * @param pin
     * @param simulationTime
     * @return
     */
    protected abstract double getCurrentLeavingPin(Pin pin, double simulationTime);
    /**
     * Quel est le courant que cette pin impose ? Renvoyer Double.NaN si cette pin n'impose aucun potentiel
     * @param pin
     * @param simulationTime
     * @return
     */
    protected abstract double getPotentialAtPin(Pin pin, double simulationTime);

    /**
     * Mets à jour le modèle
     * @param pin
     * @param simulationTime
     */
    protected abstract void step(Pin pin, double simulationTime);

    public void reset() {}

    public abstract double calculateTensionAtPin(double current, double prevTension, Pin pin, double stepTime);

    public abstract double calculateCurrentAtPin(double prevCurrent, double tension, Pin pin, double stepTime);
}
