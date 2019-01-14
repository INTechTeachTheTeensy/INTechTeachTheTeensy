package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.Game;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameArea implements Renderable {

    private List<ElectricalComponent> components = new LinkedList<>();
    private PrototypeScreen parent;
    private MutableRectangle boundingBox;
    private Pin pressedPin;

    public GameArea(PrototypeScreen parent) {
        this.parent = parent;
        boundingBox = new MutableRectangle(0, 0, 1590, 1080);
    }

    public boolean mousePressed(double screenX, double screenY) {
        if(screenX < boundingBox.width) { // prend en compte les composants

            // on regarde s'il y a un composant sous la souris
            Optional<ElectricalComponent> potentialComponent = components.stream()
                    .filter((c) -> c.box.isPointIn(screenX, screenY))
                    .findFirst();

            potentialComponent.ifPresent(electricalComponent -> {

                Optional<Pin> pin = electricalComponent.pinUnderMouse();
                if(pin.isPresent()) {
                    pressedPin = pin.get();
                } else { // on ne clique pas sur un pin, on est en train de déplacer un composant
                    parent.heldComponent = electricalComponent;
                    parent.heldComponent.xOffset = electricalComponent.box.x - screenX;
                    parent.heldComponent.yOffset = electricalComponent.box.y - screenY;
                }

            });
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double x, double y) {
        if(pressedPin != null) {
            Optional<ElectricalComponent> potentialComponent = components.stream()
                    .filter((c) -> c.box.isPointIn(x, y))
                    .findFirst();

            potentialComponent.ifPresent(electricalComponent -> {
                Optional<Pin> pinOptional = electricalComponent.pinUnderMouse();
                if (pinOptional.isPresent()) {
                    Pin pin = pinOptional.get();
                    if(pin != pressedPin && pin.getOwner() != pressedPin.getOwner()) {
                        pin.connectTo(pressedPin);
                    }
                }
            });
            pressedPin = null;
            return true;
        }
        return false;
    }

    public void addComponent(ElectricalComponent component) {
        components.add(component);
    }

    public void render(GraphicsContext ctx) {
        components.forEach((comp) -> comp.render(ctx));
        if (pressedPin != null) {
            ctx.strokeLine(Game.getInstance().getMouseX(), Game.getInstance().getMouseY(), pressedPin.getAbsoluteX(), pressedPin.getAbsoluteY());
        }
    }

    public MutableRectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean containsComponent(ElectricalComponent component) {
        return components.contains(component);
    }

    public void removeComponent(ElectricalComponent component) {
        components.remove(component);
    }
}
