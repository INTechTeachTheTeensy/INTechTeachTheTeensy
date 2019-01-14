package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Renderable;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class ElectricalComponent implements Renderable,Cloneable  {

    private final static int PIN_RADIUS = 10;
    private final Image texture;
    protected final List<Pin> pins = new LinkedList<>();
    public final MutableRectangle box;

    public double xOffset;
    public double yOffset;

    public ElectricalComponent(Image texture) {
        this.texture = texture;

        box = new MutableRectangle(0, 0, texture.getWidth(), texture.getHeight());


    }

    public void render(GraphicsContext ctx) {
        ctx.drawImage(texture, box.x, box.y);

        pinUnderMouse().ifPresent(pin -> {
            ctx.setFill(Color.RED);
            ctx.fillOval(box.x+pin.getRelativeX()-PIN_RADIUS, box.y+pin.getRelativeY()-PIN_RADIUS, PIN_RADIUS*2, PIN_RADIUS*2);

            ctx.setFill(Color.BLACK);
            ctx.fillText(pin.getName(), box.x+pin.getRelativeX(), box.y+pin.getRelativeY());
        });

        for(Pin p : pins) {
            p.render(ctx);
        }
    }

    public boolean mousePressed(double x, double y) {
        // TODO: actions quand on commence à cliquer le composant
        return false;
    }

    public Optional<Pin> pinUnderMouse() {
        return pins.stream()
                .filter(pin -> {
                    double dx = box.x+pin.getRelativeX() - Game.getInstance().getMouseX();
                    double dy = box.y+pin.getRelativeY() - Game.getInstance().getMouseY();
                    return dx*dx+dy*dy <= PIN_RADIUS*PIN_RADIUS;
                })
                .findFirst();
    }
    public abstract ElectricalComponent clone();



}
