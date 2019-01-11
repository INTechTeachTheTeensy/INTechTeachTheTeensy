package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import teachtheteensy.Renderable;

import java.util.LinkedList;
import java.util.List;

public class Pin implements Renderable {

    private final String name;
    private final int index;
    private final double relativeX;
    private final double relativeY;

    private final List<Pin> connections = new LinkedList<>();
    private final Teensy owner;

    public Pin(Teensy owner, String name, int index, double relativeX, double relativeY) {
        this.owner = owner;
        this.name = name;
        this.index = index;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.setLineWidth(5);
        for(Pin connection : connections) {
            ctx.strokeLine(getAbsoluteX(), getAbsoluteY(), connection.getAbsoluteX(), connection.getAbsoluteY());
        }
    }

    public void connectTo(Pin other) {
        if(other.owner == owner) {
            throw new IllegalArgumentException("Impossible de connecter deux pins d'un même composant!");
        }

        if(other == this) {
            throw new IllegalArgumentException("Impossible de connecter un pin à lui même!");
        }

        connections.add(other);
        other.connections.add(this);
    }

    public Teensy getOwner() {
        return owner;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getRelativeX() {
        return relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public double getAbsoluteX() {
        return getRelativeX() + owner.box.x;
    }

    public double getAbsoluteY() {
        return getRelativeY() + owner.box.y;
    }
}
