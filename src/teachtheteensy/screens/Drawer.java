package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.electricalcomponents.Teensy;
import teachtheteensy.math.MutableRectangle;

public class Drawer {

    private final Image texture;
    private final MutableRectangle buttonToOpen;
    private final Teensy teensy;
    private final MutableRectangle boundingBox;
    private boolean open = false;
    private PrototypeScreen parent;

    private Teensy heldComponent = null;

    public Drawer(PrototypeScreen parent) {
        this.parent = parent;
        texture = Assets.getImage("elements/drawer.png");
        buttonToOpen = new MutableRectangle(0, 480, 540-388, 639-480);

        teensy = new Teensy();
        teensy.box.y = 30;

        boundingBox = new MutableRectangle(0, 0, 382, 1080);
        updatePositions();
    }

    private void updatePositions() {
        boundingBox.x = getPositionX();
        teensy.box.x = getPositionX() + 5 /*margin*/;
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
                if (teensy.box.isPointIn(screenX, screenY)) {
                    parent.heldComponent = new Teensy();
                    parent.heldComponent.xOffset = teensy.box.x - screenX;
                    parent.heldComponent.yOffset = teensy.box.y - screenY;
                }
                return true;
            }
        }
        return false;
    }

    public void render(GraphicsContext ctx) {
        ctx.drawImage(texture, getPositionX(), 0);

        // TODO: component list
        teensy.render(ctx);
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
