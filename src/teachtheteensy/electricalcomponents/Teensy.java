package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.math.MutableRectangle;

public class Teensy {

    private final Image texture;
    public final MutableRectangle box;

    public double xOffset;
    public double yOffset;

    public Teensy() {
        texture = Assets.getImage("components/teensy.png");

        box = new MutableRectangle(0, 0, texture.getWidth(), texture.getHeight());
    }

    public void render(GraphicsContext ctx) {
        ctx.drawImage(texture, box.x, box.y);
    }
}
