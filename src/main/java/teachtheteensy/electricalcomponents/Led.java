package teachtheteensy.electricalcomponents;

import javafx.scene.image.Image;
import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDiode;
import org.knowm.jspice.simulate.SimulationPlotData;
import org.knowm.jspice.simulate.SimulationResult;
import teachtheteensy.Assets;
import teachtheteensy.electricalcomponents.models.ElectricalModel;
import teachtheteensy.electricalcomponents.models.LedModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Led extends ElectricalComponent {

    private final Pin plusPin;
    private final Pin minusPin;
    private boolean isOn;

    public Led() {
        super(Assets.getImage("components/led_eteinte.png"), 99/2.0, 378/2.0); // /2.0 pour diviser par 2 la taille du composant par rapport à sa texture
        plusPin = new Pin(this,"+",-1,71/2.0,315/2.0);
        minusPin = new Pin(this,"-",-1,34/2.0,378/2.0);
        pins.add(plusPin);
        pins.add(minusPin);
    }

    @Override
    protected ElectricalModel createElectricalProperties() {
        return new LedModel(this);
    }

    @Override
    public Image getTexture() {
        return isOn() ? Assets.getImage("components/led_rouge.png") : super.getTexture();
    }

    @Override
    public void step() {
        super.step();
    }

    @Override
    public ElectricalComponent clone() {
        return new Led();
    }

    @Override
    public void resetComponent() {
        getElectricalModel().reset();
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
    public List<? extends NetlistComponent> toNetlistComponents() {
        return Collections.singletonList(new NetlistDiode("Led(" + hashCode() + ")", 0.025, plusPin.getName(), minusPin.getName()));
    }

    @Override
    public void interpretResult(SimulationResult result) {
        SimulationPlotData plusPotential = result.getSimulationPlotDataMap().get(plusPin.toNodeName());
        SimulationPlotData minusPotential = result.getSimulationPlotDataMap().get(minusPin.toNodeName());

        // on se base que sur le premier point de la simulation
        double plusActualPotential = plusPotential.getyData().get(0).doubleValue();
        double minusActualPotential = minusPotential.getyData().get(0).doubleValue();
        isOn = plusActualPotential > minusActualPotential;
    }
}
