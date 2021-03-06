package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyEvent;
import teachtheteensy.Game;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.math.IRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class ComponentHolder implements Renderable {

    protected List<ElectricalComponent> components = new LinkedList<>();

    private static final Effect HOVERED_EFFECT;
    static {
        Glow glow = new Glow();
        glow.setLevel(0.5);
        HOVERED_EFFECT = glow;
    }

    public Optional<ElectricalComponent> getComponentUnderPos(double x, double y) {
        return components.stream()
                .filter((c) -> c.box.isPointIn(x, y))
                .findFirst();
    }

    @Override
    public void render(GraphicsContext ctx) {
        Optional<ElectricalComponent> hovered = getComponentUnderPos(Game.getInstance().getMouseX(), Game.getInstance().getMouseY());
        if(hovered.isPresent()) {
            ElectricalComponent hoveredComponent = hovered.get();
            for (ElectricalComponent comp : components) {
                if(comp == hoveredComponent) {
                    ctx.setEffect(HOVERED_EFFECT);
                    hoveredComponent.render(ctx);
                    ctx.setEffect(null); // désactives les effets
                } else {
                    comp.render(ctx);
                }
            }
        } else {
            components.forEach(comp -> comp.render(ctx));
        }
    }

    public boolean keyReleased(KeyEvent event) {
        for(ElectricalComponent component : components) {
            if(component.keyReleased(event))
                return true;
        }
        return false;
    }

    public boolean keyPressed(KeyEvent event) {
        for(ElectricalComponent component : components) {
            if(component.keyPressed(event))
                return true;
        }
        return false;
    }

    public boolean keyTyped(KeyEvent event) {
        for(ElectricalComponent component : components) {
            if(component.keyTyped(event))
                return true;
        }
        return false;
    }

    public boolean leftClick(double mouseX, double mouseY) {
        for(ElectricalComponent component : components) {
            if(component.leftClick(mouseX, mouseY))
                return true;
        }
        return false;
    }

    public List<ElectricalComponent> getComponents() {
        return components;
    }
}
