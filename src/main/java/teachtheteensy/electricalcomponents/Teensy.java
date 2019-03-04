package teachtheteensy.electricalcomponents;

import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDCVoltage;
import teachtheteensy.Assets;
import teachtheteensy.electricalcomponents.simulation.NetlistTeensyPin;
import teachtheteensy.electricalcomponents.simulation.NodeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Représentation d'une Teensy avec ses pins
 */
public class Teensy extends ElectricalComponent {

    /**
     * Liste des componsants JSpice correspondants aux pins
     */
    private final List<NetlistComponent> netlistPins;

    /**
     * Map pour récupérer rapidement le composant JSpice en fonction du nom d'une pin
     */
    private final Map<String, NetlistTeensyPin> pinMap;

    public Teensy() {
        super(Assets.getImage("components/teensy.png"));
        pinMap = new HashMap<>();

        // initialisation des pins
        pins.add(new GNDPin(this, -1, 9, 22.5));
        for (int i = 0; i <= 12; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i+1)*22.5));
        }
        pins.add(new VoltagePin(this,"+3.3", 3.3 ,-1,9,22.5*15));
        for (int i = 24; i <= 32; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i-9)*22.5));
        }
        pins.add(new Pin(this, "Vin",-1,145,22.5));
        pins.add(new GNDPin(this, -1,145,22.5*2));
        pins.add(new Pin(this, "+3.3",-1,145,22.5*3));
        for (int i = 23; i >= 13; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(26-i)*22.5));
        }
        pins.add(new GNDPin(this, -1,145,22.5*15));
        pins.add(new Pin(this, "A22",-1,145,22.5*16));
        pins.add(new Pin(this, "A21",-1,145,22.5*17));
        for (int i = 39; i >= 33; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(56-i)*22.5));
        }

        // liste des équivalents des pins pour JSpice
        netlistPins = pins.stream()
                .map(NetlistTeensyPin::new)
                .distinct() // distinct() est utilisé pour n'avoir que des pins de nom différents
                .map(p -> {
                    pinMap.put(p.getPin().getName(), p);
                    return p;
                })
                .collect(Collectors.toList());
     //   netlistPins.add(new NetlistDCVoltage(toString()+"("+hashCode()+").3.3->GND", 3.3, pinMap.get("+3.3").getId(), "0"));
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
        if(pinMap.containsKey("+3.3"))
            pinMap.get("+3.3").getSource().setValue(3.3);
    }

    @Override
    public List<? extends NetlistComponent> toNetlistComponents(NodeMap nodeMap) {
        // mets à jour les noeuds connectés aux pins
        netlistPins.forEach(pin -> {
            if(pin instanceof NetlistTeensyPin) {
                if(((NetlistTeensyPin) pin).getPin() instanceof VoltagePin) {
                    pin.setNodes(new String[] { nodeMap.getNode(((NetlistTeensyPin) pin).getPin()) } );
                } else {
                    pin.setNodes(new String[] { nodeMap.getNode(((NetlistTeensyPin) pin).getPin()), "0" } );
                }
            }
        });

        // retire les pins non connectées
        return netlistPins.stream()
                .filter(p -> {
                    if(p instanceof NetlistTeensyPin) {
                        return ((NetlistTeensyPin) p).getPin().hasAtLeastOneConnection();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
