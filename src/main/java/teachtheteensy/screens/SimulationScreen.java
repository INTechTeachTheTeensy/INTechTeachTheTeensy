package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import org.knowm.jspice.netlist.*;
import org.knowm.jspice.simulate.SimulationResult;
import org.knowm.jspice.simulate.transientanalysis.TransientAnalysis;
import org.knowm.jspice.simulate.transientanalysis.TransientConfig;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.GNDPin;
import teachtheteensy.electricalcomponents.simulation.NetlistTeensyPin;
import teachtheteensy.electricalcomponents.simulation.NodeMap;
import teachtheteensy.math.MutableRectangle;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class SimulationScreen extends Screen {

    private final PrototypeScreen parent;
    private final List<ElectricalComponent> components;
    private final MutableRectangle pauseButton;

    /**
     * Analyseur de JSpice utilisé pour simuler le circuit
     */
    private final TransientAnalysis analyser;

    /**
     * Incrémentation du temps à chaque tick
     */
    private final BigDecimal dt = new BigDecimal("2.0");
    /**
     * De combien en combien JSpice doit simuler? (résolution en temps)
     */
    private final BigDecimal stepTime = new BigDecimal("1.0");
    private final NodeMap nodeMap;


    public SimulationScreen(PrototypeScreen parent) {
        this.parent = parent;
        this.nodeMap = new NodeMap();
        this.pauseButton = new MutableRectangle(1920/2-50,1080-100,100,100);

        components = parent.getGameArea().getComponents();
        parent.getGameArea().prepareComponents();
        analyser = createAnalyser();
    }

    private TransientAnalysis createAnalyser() {
        Netlist netlist = new Netlist() {
            {
                nodeMap.buildFrom(components);
                // on récupère les composants et on les transforme en composants compréhensibles pour JSpice
                components.stream()
                        .map(c -> c.toNetlistComponents(nodeMap))
                        .flatMap(Collection::stream)
                        .distinct()
                        .filter(c -> {
                            if(c instanceof NetlistTeensyPin) {
                                return !(((NetlistTeensyPin) c).getPin() instanceof GNDPin);
                            }
                            return true;
                        })
                        .forEach(this::addNetListComponent);
            }
        };

        if(netlist.getNetlistComponents().size() < 2)
            return null; // on a pas assez de composants pour faire une simulation

        System.out.println("!! "+netlist.toSpiceString());
        // initialisation de l'analyseur
        TransientConfig transientConfig = new TransientConfig(".02", ".002"); // TODO: changer les valeurs?
        netlist.setSimulationConfig(transientConfig);
        return new TransientAnalysis(netlist, transientConfig);
    }

    @Override
    public void tick() {
        components.forEach(ElectricalComponent::step);
        try {
            if(analyser != null) {
                SimulationResult result = analyser.stepFor(dt, stepTime);
                components.forEach(c -> c.interpretResult(nodeMap, result));
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO: gérer les cas d'erreur
        }
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.drawImage(Assets.getImage("screens/simulation_background.png"), 0, 0);

        components.forEach(c -> c.render(ctx));
        ctx.drawImage(Assets.getImage("ui/pause_button.png"), pauseButton.getX(), pauseButton.getY());
    }

    @Override
    public void leftClick(double sceneX, double sceneY) {
        if(pauseButton.isPointIn(sceneX, sceneY)) {
            parent.resetComponents();
            Game.getInstance().showScreen(parent);
        }
    }
}
