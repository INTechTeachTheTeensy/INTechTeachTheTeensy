package teachtheteensy;

import org.knowm.jspice.simulate.SimulationPlotData;
import org.knowm.jspice.simulate.SimulationResult;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.electricalcomponents.simulation.NodeMap;

public final class JSpiceUtils {

    /**
     * Extrait l'information de potentiel en un noeud donné pour un résultat de simulation
     * On se base que sur le premier point de la simulation
     * @param result le résultat de simulation
     * @param nodeMap la {@link NodeMap} correspondant au circuit
     * @param pin la pin concernée
     * @return le potentiel à la pin donnée
     */
    public static double voltage(SimulationResult result, NodeMap nodeMap, Pin pin) {
        String node = nodeMap.getNode(pin);
        if(node.equals("0")) // c'est la masse
            return 0.0;
        String key = "V("+node+")";
        SimulationPlotData data = result.getSimulationPlotDataMap().get(key);
        double value = data.getyData().get(0).doubleValue();
        if(Math.abs(value) <= ElectricalComponent.EPSILON) {
            return 0.0;
        }
        return value;
    }
}
