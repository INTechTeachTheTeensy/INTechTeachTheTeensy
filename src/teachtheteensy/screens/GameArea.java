package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.electricalcomponents.Teensy;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameArea {

    private List<Teensy> components = new LinkedList<>();
    private PrototypeScreen parent;
    private MutableRectangle boundingBox;

    public GameArea(PrototypeScreen parent) {
        this.parent = parent;
        boundingBox = new MutableRectangle(0, 0, 1590, 1080);
    }

    public boolean mousePressed(double screenX, double screenY) {
        if(screenX < boundingBox.width) { // prend en compte les composants

            // on regarde s'il y a un composant sous la souris
            Optional<Teensy> potentialComponent = components.stream()
                    .filter((c) -> c.box.isPointIn(screenX, screenY))
                    .findFirst();

            potentialComponent.ifPresent(teensy -> {
                parent.heldComponent = teensy;
                parent.heldComponent.xOffset = teensy.box.x - screenX;
                parent.heldComponent.yOffset = teensy.box.y - screenY;
            });
            return true;
        }
        return false;
    }

    public void addComponent(Teensy component) {
        components.add(component);
    }

    public void render(GraphicsContext ctx) {
        components.forEach((comp) -> comp.render(ctx));
    }

    public MutableRectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean containsComponent(Teensy component) {
        return components.contains(component);
    }

    public void removeComponent(Teensy component) {
        components.remove(component);
    }
}
