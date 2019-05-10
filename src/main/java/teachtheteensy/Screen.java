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

    /**
     * Appelé quand on ferme ce menu, permet de faire des transitions, sauvegarder des changements, couper une musique etc.
     * @param newScreen
     */
    public void close(Screen newScreen) {}

    /**
     * Appelé quand on ouvre ce menu, permet de faire des transitions, sauvegarder des changements, lancer une musique etc.
     * @param previousScreen
     */
    public void open(Screen previousScreen) {}
}
