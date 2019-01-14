package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.math.MutableRectangle;

public class TitleScreen extends Screen {

    private final MutableRectangle redBox;

    public TitleScreen() {
        redBox = new MutableRectangle(396, 428, 1530-396, 854-428);
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.drawImage(Assets.getImage("screens/title.png"), 0, 0);
    }

    @Override
    public void tick() {

    }

    @Override
    public void leftClick(double sceneX, double sceneY) {
        if(redBox.isPointIn(sceneX, sceneY)) {
            Game.getInstance().showScreen(new PrototypeScreen());
        }
    }

    @Override
    public void rightClick(double sceneX, double sceneY) {
        if(redBox.isPointIn(sceneX, sceneY)) {
            System.out.println("Clic droit!");
        }
    }
}
