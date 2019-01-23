package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.models.ElectricalModel;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class ElectricalComponent implements Renderable, Cloneable {

    private final Image texture;
    protected final List<Pin> pins = new LinkedList<>();
    public final MutableRectangle box;

    public double xOffset;
    public double yOffset;
    private ElectricalModel model;

    public ElectricalComponent(Image texture) {
        this(texture, texture.getWidth(), texture.getHeight());
    }

    public ElectricalComponent(Image texture, double width, double height) {
        this.texture = texture;
        box = new MutableRectangle(0, 0, width, height);

        model = createElectricalProperties();
    }

    protected abstract ElectricalModel createElectricalProperties();

    public ElectricalModel getElectricalModel() {
        return model;
    }

    public void step() {}

    public void render(GraphicsContext ctx) {
        ctx.drawImage(getTexture(), box.getX(), box.getY(), box.getWidth(), box.getHeight());

        pinUnderMouse().ifPresent(pin -> {
            ctx.setFill(Color.RED);
            ctx.fillOval(box.getX() +pin.getRelativeX()-Pin.PIN_RADIUS, box.getY() +pin.getRelativeY()-Pin.PIN_RADIUS, Pin.PIN_RADIUS*2, Pin.PIN_RADIUS*2);

            ctx.setFill(Color.BLACK);
            ctx.fillText(pin.getName(), box.getX() +pin.getRelativeX(), box.getY() +pin.getRelativeY());
        });

        for(Pin p : pins) {
            p.render(ctx);
        }
    }

    public Optional<Pin> getPin(int index) {
        if(index < 0)
            return Optional.empty();
        return pins.stream().filter(p -> p.getIndex() == index).findFirst();
    }

    public Optional<Pin> getPin(String name) {
        return pins.stream().filter(p -> p.getName().equals(name)).findFirst();
    }

    public Image getTexture() {
        return texture;
    }

    public boolean mousePressed(double x, double y) {
        // TODO: actions quand on commence à cliquer le composant
        return false;
    }
    public Optional<Pin> pinUnderMouse() {
        return pins.stream()
                .filter(Pin::isMouseOn)
                .findFirst();
    }

    public abstract ElectricalComponent clone();

    public List<Pin> getPins() {
        return pins;
    }

    public abstract void resetComponent();
}
