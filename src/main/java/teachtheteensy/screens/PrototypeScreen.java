package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.math.MutableRectangle;

public class PrototypeScreen extends Screen {

    private final MutableRectangle playButton;
    private Drawer drawer = new Drawer(this);
    private GameArea gameArea = new GameArea(this);
    protected ElectricalComponent heldComponent = null;

    public PrototypeScreen() {
        playButton = new MutableRectangle(1920/2-50, 1080-100, 100, 100);
    }

    @Override
    public void render(GraphicsContext ctx) {
        // fond
        ctx.drawImage(Assets.getImage("screens/proto_background.png"), 0, 0);

        ctx.drawImage(Assets.getImage("ui/play_button.png"), playButton.getX(), playButton.getY());

        // zone de jeu
        gameArea.render(ctx);
        // tiroir
        drawer.render(ctx);

        if(heldComponent != null) {
            heldComponent.render(ctx);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void mouseReleased(double screenX, double screenY) {
        if(drawer.mouseReleased(screenX, screenY))
            return;

        if(gameArea.mouseReleased(screenX, screenY))
            return;

        if(heldComponent != null) { // si on lache le composant
            // reset des offsets pour mettre le composant au bon endroit
            heldComponent.xOffset = 0;
            heldComponent.yOffset = 0;

            // TODO: verif si dans tiroir (reposer le composant)
            if(drawer.getBoundingBox().isPointIn(screenX, screenY)) {
                gameArea.removeComponent(heldComponent);
            } else if(gameArea.getBoundingBox().isPointIn(screenX, screenY)) {
                if( ! gameArea.containsComponent(heldComponent)) {
                    gameArea.addComponent(heldComponent);
                }
            }
        }
        heldComponent = null;
        // TODO: check if in game area
    }

    @Override
    public void mousePressed(double screenX, double screenY) {
        if(drawer.mousePressed(screenX, screenY))
            return;

        if(gameArea.mousePressed(screenX, screenY))
            return;
    }

    @Override
    public void mouseDragged(double sceneX, double sceneY) {
        if(drawer.mouseDragged(sceneX, sceneY))
            return;

        if(heldComponent != null) {
            heldComponent.box.setX(sceneX + heldComponent.xOffset);
            heldComponent.box.setY(sceneY + heldComponent.yOffset);
        }
    }

    @Override
    public void leftClick(double sceneX, double sceneY) {
        if(playButton.isPointIn(sceneX, sceneY)) {
            Game.getInstance().showScreen(new SimulationScreen(this));
            return;
        }

        if(gameArea.leftClick(sceneX, sceneY)) {
            return;
        }

        if(drawer.leftClick(sceneX, sceneY)) // le tiroir a mangé le clic (on a cliqué sur le carré pour l'ouvrir)
            return;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(gameArea.keyPressed(event))
            return;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if(gameArea.keyReleased(event))
            return;
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if(gameArea.keyTyped(event)) {
            return;
        }
    }

    public GameArea getGameArea() {
        return gameArea;
    }

    public void resetComponents() {
        getGameArea().components.forEach(c -> c.resetComponent());
    }
}
