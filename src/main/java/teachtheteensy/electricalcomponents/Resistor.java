package teachtheteensy.electricalcomponents;

import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistResistor;
import teachtheteensy.Assets;
import teachtheteensy.electricalcomponents.simulation.NodeMap;

import java.util.Collections;
import java.util.List;


public class Resistor extends ElectricalComponent {

    private Pin pinGauche;
    private Pin pinDroite;
    private int valeur;

    public Resistor(int value) {
        super(Assets.getImage("components/resistor.png"));
        pinDroite = new Pin(this,"IN",-1,71/2.0,315/2.0);
        pinGauche = new Pin(this,"OUT",-1,34/2.0,378/2.0);
        valeur = value;
    }

    @Override
    public void step() {
        super.step();
    }

    @Override
    public ElectricalComponent clone() {
        return new Resistor(valeur);
    }

    @Override
    public void resetComponent() {

    }

    @Override
    public List<? extends NetlistComponent> toNetlistComponents(NodeMap nodeMap) {
        // on ne crée de composant que si les deux broches sont connectées
        if(pinDroite.hasAtLeastOneConnection() && pinGauche.hasAtLeastOneConnection()) {
            NetlistResistor diode = new NetlistResistor("Résistance(" + hashCode() + ")", valeur, nodeMap.getNode(pinDroite), nodeMap.getNode(pinGauche));
            return Collections.singletonList(diode);
        }
        return Collections.emptyList();
    }

}
