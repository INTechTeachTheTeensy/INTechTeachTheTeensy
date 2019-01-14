package teachtheteensy.boot;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teachtheteensy.Game;

import java.awt.*;

/**
 * Classe contenant l'Application JavaFX. Initialises la fenêtre et le jeu et laisse ce dernier prendre le contrôle
 */
public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        int width = resolution.width;
        int height = resolution.height;
        stage.setTitle("Teach The Teensy");
        Canvas canvas = new Canvas(width, height);
        Group group = new Group();
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setResizable(false);

        GraphicsContext ctx = canvas.getGraphicsContext2D();
        Game game = new Game(width, height);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.tick();
                game.render(ctx);
            }
        };

        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, game::mouseClick);
        stage.addEventFilter(MouseEvent.MOUSE_PRESSED, game::mousePressed);
        stage.addEventFilter(MouseEvent.MOUSE_RELEASED, game::mouseReleased);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, game::mouseDragged);
        stage.addEventFilter(MouseEvent.MOUSE_MOVED, game::mouseMoved);

        timer.start();
        stage.show();
    }
}
