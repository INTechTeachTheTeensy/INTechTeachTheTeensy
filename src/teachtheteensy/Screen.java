package teachtheteensy;

import javafx.scene.canvas.GraphicsContext;

public abstract class Screen {

    public Screen() {

    }

    public abstract void render(GraphicsContext ctx);
    public abstract void tick();

    public void leftClick(double sceneX, double sceneY) {}
    public void rightClick(double sceneX, double sceneY) {}
    public void mousePressed(double sceneX, double sceneY) {}
    public void mouseReleased(double sceneX, double sceneY) {}
    public void mouseDragged(double sceneX, double sceneY) {}
}
