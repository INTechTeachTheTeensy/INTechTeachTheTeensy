package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Led;
import teachtheteensy.electricalcomponents.Teensy;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Drawer extends ComponentHolder implements Renderable  {

    private final Image texture;
    private final MutableRectangle buttonToOpen;
    private final MutableRectangle boundingBox;
    private boolean open = false;
    private PrototypeScreen parent;

    private ElectricalComponent heldComponent = null;

    public Drawer(PrototypeScreen parent) {
        components = new LinkedList<>();
        this.parent = parent;
        texture = Assets.getImage("elements/drawer.png");
        buttonToOpen = new MutableRectangle(0, 480, 540-388, 639-480);

        Teensy teensy = new Teensy();
        teensy.box.setY(30);
        components.add(teensy);

        Led led = new Led();
        led.box.setY(teensy.box.getHeight() + teensy.box.getY());
        components.add(led);

        boundingBox = new MutableRectangle(0, 0, 382, 1080);
        updatePositions();
    }

    private void updatePositions() {
        boundingBox.setX(getPositionX());
        for (ElectricalComponent comp : components) {
            comp.box.setX(getPositionX() + 5) /*margin*/;
        }

    }

    public boolean leftClick(double sceneX, double sceneY) {
        buttonToOpen.setX(getPositionX()+382);
        if(buttonToOpen.isPointIn(sceneX, sceneY)) {
            open = !open; // TODO: animation?
            updatePositions();
            return true;
        }
        return false;
    }

    // TODO: call this method

    public boolean mouseReleased(double screenX, double screenY) {
        return false;
    }

    public boolean mousePressed(double screenX, double screenY) {
        if(open) { // prend en compte les composants
            if(screenX < buttonToOpen.getX()) { // si on est dans la zone
                getComponentUnderPos(screenX, screenY).ifPresent(component -> { // si on commence à cliquer sur un composant
                    parent.heldComponent = component.clone();

                    // le composant part de la même position, pour qu'on ai l'impression de le glisser sur la zone de jeu
                    parent.heldComponent.box.setX(component.box.getX());
                    parent.heldComponent.box.setY(component.box.getY());
                    parent.heldComponent.xOffset = component.box.getX() - screenX;
                    parent.heldComponent.yOffset = component.box.getY() - screenY;
                });
                return true;
            }
        }
        return false;
    }

    public void render(GraphicsContext ctx) {
        ctx.drawImage(texture, getPositionX(), 0);
        super.render(ctx);
    }

    private double getPositionX() {
        double positionX = -382; // le tiroir est en partie en dehors de l'écran
        if(open) {
            positionX = 0;
        }
        return positionX;
    }

    public boolean mouseDragged(double sceneX, double sceneY) {
        return false;
    }

    public MutableRectangle getBoundingBox() {
        return boundingBox;
    }
}
