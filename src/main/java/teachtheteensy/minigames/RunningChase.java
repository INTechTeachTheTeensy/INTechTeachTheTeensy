package teachtheteensy.minigames;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunningChase extends Minigame {

    enum SlotType {
        EMPTY, PLAYER, CHASER, BOX;

    }


    enum Direction {
        UP, DOWN, NONE/* valeur de départ*/;
    }
    class Point {

        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Auto-généré par IDEA

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }
        // Auto-généré par IDEA

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    private static final int WIDTH = 16*3;

    private static final int HEIGHT = 9*3;
    private static final int CELL_SIZE = 40;

    private static final int TICKS_PER_UPDATE = 10;

    private Direction currentDirection = Direction.NONE;

    private Direction newDirection = Direction.NONE;

    private Point boxPos;

    private final SlotType[][] grid = new SlotType[WIDTH][HEIGHT];

    private int tick;

    private boolean gameOver;

    @Override
    public void tick() {

    }

    @Override
    public void render(GraphicsContext ctx) {

    }
}
