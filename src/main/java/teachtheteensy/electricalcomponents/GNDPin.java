package teachtheteensy.electricalcomponents;

/**
 * Pin représentant la massse.
 * Utilisé pour forcer le nom de noeud à '0' qui est un noeud spécial pour JSpice
 */
public class GNDPin extends VoltagePin {
    public GNDPin(ElectricalComponent owner, int index, double relativeX, double relativeY) {
        super(owner, "GND", 0.0, index, relativeX, relativeY);
    }

    @Override
    public boolean forcesNodeName() {
        return true;
    }

    @Override
    public String toNodeName() {
        return "0"; // noeud spécial de JSpice fait pour la terre
    }
}
