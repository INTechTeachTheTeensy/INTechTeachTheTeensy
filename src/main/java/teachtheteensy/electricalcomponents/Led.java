package teachtheteensy.electricalcomponents;

import javafx.scene.image.Image;
import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDiode;
import org.knowm.jspice.simulate.SimulationResult;
import teachtheteensy.Assets;
import teachtheteensy.JSpiceUtils;
import teachtheteensy.electricalcomponents.simulation.NodeMap;

import java.util.Collections;
import java.util.List;

/**
 * Composant représentant une LED. Une LED est allumée si le potentiel sur sa broche '+' est supérieur à celui sur sa broche '-'
 */
public class Led extends ElectricalComponent {

    private final Pin plusPin;
    private final Pin minusPin;

    /**
     * La LED est-elle allumée ?
     * @see #interpretResult(NodeMap, SimulationResult)
     */
    private boolean isOn;

    public Led() {
        super(Assets.getImage("components/led_eteinte.png"), 99/2.0, 378/2.0); // /2.0 pour diviser par 2 la taille du composant par rapport à sa texture
        plusPin = new Pin(this,"+",-1,71/2.0,315/2.0);
        minusPin = new Pin(this,"-",-1,34/2.0,378/2.0);
        pins.add(plusPin);
        pins.add(minusPin);
    }

    @Override
    public Image getTexture() {
        // on change la texture selon l'état de la LED
        return isOn() ? Assets.getImage("components/led_rouge.png") : super.getTexture();
    }

    @Override
    public ElectricalComponent clone() {
        return new Led();
    }

    @Override
    public void resetComponent() {
        isOn = false;
    }

    public Pin getMinusPin() {
        return minusPin;
    }

    public Pin getPlusPin() {
        return plusPin;
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public List<? extends NetlistComponent> toNetlistComponents(NodeMap nodeMap) {
        // on ne crée de composant que si les deux broches sont connectées
        if(plusPin.hasAtLeastOneConnection() && minusPin.hasAtLeastOneConnection()) {
            NetlistDiode diode = new NetlistDiode("Led(" + hashCode() + ")", 0.025, nodeMap.getNode(plusPin), nodeMap.getNode(minusPin));
            return Collections.singletonList(diode);
        }
        return Collections.emptyList();
    }

    @Override
    public void interpretResult(NodeMap nodeMap, SimulationResult result) {
        // récupération des potentiels
        double plusPotential = JSpiceUtils.voltage(result, nodeMap, plusPin);
        double minusPotential = JSpiceUtils.voltage(result, nodeMap, minusPin);

        // changement de l'état
        isOn = plusPotential > minusPotential;
    }
}
