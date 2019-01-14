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

public class Drawer implements Renderable {

    private final Image texture;
    private final MutableRectangle buttonToOpen;
    private final List<ElectricalComponent> list;
    private final MutableRectangle boundingBox;
    private boolean open = false;
    private PrototypeScreen parent;

    private ElectricalComponent heldComponent = null;

    public Drawer(PrototypeScreen parent) {
        list= new LinkedList<>();
        this.parent = parent;
        texture = Assets.getImage("elements/drawer.png");
        buttonToOpen = new MutableRectangle(0, 480, 540-388, 639-480);

        Teensy teensy = new Teensy();
        teensy.box.y = 30;
        list.add(teensy);

        Led led = new Led();
        led.box.y=teensy.box.height + teensy.box.y;
        list.add(led);

        boundingBox = new MutableRectangle(0, 0, 382, 1080);
        updatePositions();
    }

    private void updatePositions() {
        boundingBox.x = getPositionX();
        for (ElectricalComponent comp : list) {
            comp.box.x = getPositionX() + 5 /*margin*/;
        }

    }

    public boolean leftClick(double sceneX, double sceneY) {
        buttonToOpen.x = getPositionX()+382;
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
            if(screenX < buttonToOpen.x) { // si on est dans la zone
                Optional<ElectricalComponent> potentialcomp =
                        list.stream()
                                .filter(electricalComponent -> electricalComponent.box.isPointIn(screenX,screenY))
                                .findFirst();
                if (potentialcomp.isPresent()) {
                    ElectricalComponent component = potentialcomp.get();
                    parent.heldComponent = component.clone();
                    parent.heldComponent.xOffset = component.box.x - screenX;
                    parent.heldComponent.yOffset = component.box.y - screenY;
                }
                return true;
            }
        }
        return false;
    }

    public void render(GraphicsContext ctx) {
        ctx.drawImage(texture, getPositionX(), 0);
        list.forEach(comp -> comp.render(ctx));
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
