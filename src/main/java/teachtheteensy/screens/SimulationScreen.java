package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.math.MutableRectangle;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationScreen extends Screen {

    private final PrototypeScreen parent;
    private final List<ElectricalComponent> components;
    private final MutableRectangle pauseButton;

    public SimulationScreen(PrototypeScreen parent) {
        this.parent = parent;
        components = parent.getGameArea().getComponents();
        pauseButton = new MutableRectangle(1920/2-50,1080-100,100,100);
    }

    @Override
    public void tick() {
        components.forEach(ElectricalComponent::step);
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
