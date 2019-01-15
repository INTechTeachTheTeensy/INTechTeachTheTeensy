package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import teachtheteensy.Game;
import teachtheteensy.Renderable;

import java.util.LinkedList;
import java.util.List;

public class Pin implements Renderable {

    public final static int PIN_RADIUS = 10;
    private final String name;
    private final int index;
    private final double relativeX;
    private final double relativeY;

    private final List<Pin> connections = new LinkedList<>();
    private final ElectricalComponent owner;

    public Pin(ElectricalComponent owner, String name, int index, double relativeX, double relativeY) {
        this.owner = owner;
        this.name = name;
        this.index = index;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.setLineWidth(5);
        ctx.setStroke(Color.GREENYELLOW);
        for(Pin connection : connections) {
            ctx.strokeLine(getAbsoluteX(), getAbsoluteY(), connection.getAbsoluteX(), connection.getAbsoluteY());
        }
        ctx.setStroke(Color.BLACK);
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

    public List<Pin> getConnections() {
        return connections;
    }

    public ElectricalComponent getOwner() {
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
        return getRelativeX() + owner.box.getX();
    }

    public double getAbsoluteY() {
        return getRelativeY() + owner.box.getY();
    }

    public boolean isMouseOn() {
        double dx = getAbsoluteX() - Game.getInstance().getMouseX();
        double dy = getAbsoluteY() - Game.getInstance().getMouseY();
        return dx*dx+dy*dy <= PIN_RADIUS*PIN_RADIUS;
    }
}
