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

public class Teensy implements Renderable  {

    private final static int PIN_RADIUS = 10;
    private final Image texture;
    private final List<Pin> pins = new LinkedList<>();
    public final MutableRectangle box;

    public double xOffset;
    public double yOffset;

    public Teensy() {
        texture = Assets.getImage("components/teensy.png");

        box = new MutableRectangle(0, 0, texture.getWidth(), texture.getHeight());

        // pins
        pins.add(new Pin(this, "GND", -1, 9, 22.5));
        for (int i = 0; i <= 12; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i+1)*22.5));
        }
        pins.add(new Pin(this,"+3.3",-1,9,22.5*15));
        for (int i = 24; i <= 32; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i-9)*22.5));
        }
        pins.add(new Pin(this, "Vin",-1,145,22.5));
        pins.add(new Pin(this, "GND",-1,145,22.5*2));
        pins.add(new Pin(this, "+3.3",-1,145,22.5*3));
        for (int i = 23; i >= 13; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(26-i)*22.5));
        }
        pins.add(new Pin(this, "GND",-1,145,22.5*15));
        pins.add(new Pin(this, "A22",-1,145,22.5*16));
        pins.add(new Pin(this, "A21",-1,145,22.5*17));
        for (int i = 39; i >= 33; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(56-i)*22.5));
        }
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


}
