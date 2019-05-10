package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import teachtheteensy.Game;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;
import teachtheteensy.math.MutableRectangle;

import java.util.Collection;
import java.util.Optional;

public class GameArea extends ComponentHolder implements Renderable {

    private PrototypeScreen parent;
    private MutableRectangle boundingBox;
    private Pin pressedPin;

    public GameArea(PrototypeScreen parent) {
        this.parent = parent;
        boundingBox = new MutableRectangle(0, 0, 1590, 1080);
    }

    public boolean mousePressed(double screenX, double screenY) {
        if(screenX < boundingBox.getWidth()) { // prend en compte les composants
            // on vérifie s'il y a un pin sous la souris
            // c'est séparé du check de composant car certains pins (ex: leds) dépassent de la boîte de collision
            Optional<Pin> pin = pinUnderMouse();
            if(pin.isPresent()) {
                pressedPin = pin.get();
                return true;
            }

            // on regarde s'il y a un composant sous la souris
            getComponentUnderPos(screenX, screenY).ifPresent(electricalComponent -> {
                parent.heldComponent = electricalComponent;
                parent.heldComponent.xOffset = electricalComponent.box.getX() - screenX;
                parent.heldComponent.yOffset = electricalComponent.box.getY() - screenY;
            });
            return true;
        }
        return false;
    }

    public Optional<Pin> pinUnderMouse() {
        return components.stream()
                .map(ElectricalComponent::getPins)
                .flatMap(Collection::stream)
                .filter(Pin::isMouseOn)
                .findFirst();
    }

    public boolean mouseReleased(double x, double y) {
        if(pressedPin != null) {
            pinUnderMouse().ifPresent(pin -> {
                if(pin != pressedPin && pin.getOwner() != pressedPin.getOwner()) {
                    pin.connectTo(pressedPin);
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
        super.render(ctx);
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
        component.getPins().forEach(Pin::disconnect);
    }

    public void prepareComponents() {
        components.forEach(ElectricalComponent::prepare);
    }
}
