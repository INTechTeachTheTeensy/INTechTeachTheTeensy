package teachtheteensy;

import javafx.scene.input.KeyEvent;

public abstract class Screen implements Renderable  {

    public Screen() {

    }

    /**
     * Action effectuée à chaque frame
     */
    public abstract void tick();

    public void leftClick(double sceneX, double sceneY) {}
    public void rightClick(double sceneX, double sceneY) {}
    public void mousePressed(double sceneX, double sceneY) {}
    public void mouseReleased(double sceneX, double sceneY) {}
    public void mouseDragged(double sceneX, double sceneY) {}
    public void keyPressed(KeyEvent event) {}
    public void keyReleased(KeyEvent event) {}

    public void keyTyped(KeyEvent event) {}
}
