package teachtheteensy.electricalcomponents.simulation;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.screens.SimulationScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Graph {

    private List<Pin> nodes;
    private List<Wire> edges;

    // vecteurs utilisés pour la résolution des équa diff (méthode d'Euler)
    // vector[numéro pin]
    private double[] tensionVector;
    private double[] currentVector;
    private double[] lastTensionVector;
    private double[] lastCurrentVector;

    private Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Optional<Wire> getWireConnecting(Pin a, Pin b) {
        return edges.stream()
                .filter(w -> w.getNodeA() == a && w.getNodeB() == b
                        || w.getNodeA() == b && w.getNodeB() == a)
                .findFirst();
    }

    public static Graph buildGraph(List<ElectricalComponent> components) {
        Graph g = new Graph();
        g.nodes.clear();
        g.edges.clear();
        for(ElectricalComponent comp: components) {
            for(Pin a: comp.getPins()) {
                if(!g.nodes.contains(a)) {
                    g.nodes.add(a);
                }

                for(Pin b: a.getConnections()) {
                    if( ! g.getWireConnecting(a, b).isPresent()) { // s'il n'y a pas déjà cette connection
                        g.edges.add(new Wire(a, b));
                    }
                }
            }
        }
        g.tensionVector = new double[g.nodes.size()];
        g.currentVector = new double[g.edges.size()];
        g.lastTensionVector = new double[g.nodes.size()];
        g.lastCurrentVector = new double[g.edges.size()];
        return g;
    }

    public void stepGraph(double stepTime) {
        // méthode d'Euler

        for(int i = 0;i<nodes.size();i++) {
            tensionVector[i] = nodes.get(i).calculateTension(lastCurrentVector[i], lastTensionVector[i], stepTime);
        }

        for(int i = 0;i<edges.size();i++) {
            currentVector[i] = edges.get(i).calculateCurrent(lastCurrentVector[i], lastTensionVector[i], stepTime);
        }
        // copie pour avoir les anciennes valeurs
        System.arraycopy(tensionVector, 0, lastTensionVector, 0, tensionVector.length);
        System.arraycopy(currentVector, 0, lastCurrentVector, 0, currentVector.length);

    }

}
