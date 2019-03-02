package teachtheteensy.electricalcomponents;

import org.knowm.jspice.netlist.NetlistComponent;
import teachtheteensy.Assets;
import teachtheteensy.electricalcomponents.models.ElectricalModel;
import teachtheteensy.electricalcomponents.models.TeensyModel;
import teachtheteensy.electricalcomponents.simulation.NetlistTeensyPin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Teensy extends ElectricalComponent {

    /**
     * Liste des componsants JSpice correspondants aux pins
     */
    private final List<NetlistTeensyPin> netlistPins;

    /**
     * Map pour récupérer rapidement le composant JSpice en fonction du nom d'une pin
     */
    private final Map<String, NetlistTeensyPin> pinMap;

    public Teensy() {
        super(Assets.getImage("components/teensy.png"));
        pinMap = new HashMap<>();

        // pins
        pins.add(new Pin(this, "GND", -1, 9, 22.5));
        for (int i = 0; i <= 12; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i+1)*22.5));
        }
        pins.add(new Pin(this,"+3.3",-1,9,22.5*15));
        for (int i = 24; i <= 32; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i-9)*22.5));
        }
        pins.add(new Pin(this, "Vin",-1,145,22.5));
        pins.add(new Pin(this, "GND",-1,145,22.5*2));
        pins.add(new Pin(this, "+3.3",-1,145,22.5*3));
        for (int i = 23; i >= 13; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(26-i)*22.5));
        }
        pins.add(new Pin(this, "GND",-1,145,22.5*15));
        pins.add(new Pin(this, "A22",-1,145,22.5*16));
        pins.add(new Pin(this, "A21",-1,145,22.5*17));
        for (int i = 39; i >= 33; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(56-i)*22.5));
        }

        netlistPins = pins.stream()
                .map(NetlistTeensyPin::new)
                .distinct() // distinct() est utilisé pour n'avoir que des pins de même nom
                .map(p -> {
                    pinMap.put(p.getPin().getName(), p);
                    return p;
                })
                .collect(Collectors.toList());
    }

    @Override
    protected ElectricalModel createElectricalProperties() {
        return new TeensyModel(this);
    }

    @Override
    public ElectricalComponent clone() {
        return new Teensy();
    }

    @Override
    public void resetComponent() {

    }

    @Override
    public void step() {
        super.step();
        pinMap.get("+3.3").getSource().setValue(3.3);
        pinMap.get("GND").getSource().setValue(0.0);
    }

    @Override
    public List<? extends NetlistComponent> toNetlistComponents() {
        return netlistPins;
    }
}
