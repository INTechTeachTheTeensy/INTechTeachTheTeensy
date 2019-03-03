package teachtheteensy;

import org.knowm.jspice.simulate.SimulationPlotData;
import org.knowm.jspice.simulate.SimulationResult;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.electricalcomponents.simulation.NodeMap;

public final class JSpiceUtils {


    public static double voltage(SimulationResult result, NodeMap nodeMap, Pin pin) {
        String node = nodeMap.getNode(pin);
        if(node.equals("0")) // c'est la masse
            return 0.0;
        String key = "V("+node+")";
        SimulationPlotData data = result.getSimulationPlotDataMap().get(key);
        return data.getyData().get(0).doubleValue();
    }
}
