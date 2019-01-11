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

    @Override
    public void render(GraphicsContext ctx) {

    }
}
