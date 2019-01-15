package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.Assets;
import teachtheteensy.Screen;
import teachtheteensy.electricalcomponents.ElectricalComponent;

import java.util.List;
import java.util.stream.Collectors;

public class SimulationScreen extends Screen {

    private final PrototypeScreen parent;
    private final List<ElectricalComponent> components;

    public SimulationScreen(PrototypeScreen parent) {
        this.parent = parent;
        components = parent.getGameArea().getComponents();
    }

    @Override
    public void tick() {
        components.forEach(ElectricalComponent::step);
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.drawImage(Assets.getImage("screens/simulation_background.png"), 0, 0);

        components.forEach(c -> c.render(ctx));
    }
}
