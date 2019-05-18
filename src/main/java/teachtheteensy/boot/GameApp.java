package teachtheteensy.boot;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teachtheteensy.Assets;
import teachtheteensy.Game;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Classe contenant l'Application JavaFX. Initialises la fenêtre et le jeu et laisse ce dernier prendre le contrôle
 */
public class GameApp extends Application {

    @Override
    public void init() throws Exception {
        // Prototypage
        Assets.getImage("components/led_eteinte.png");
        Assets.getImage("components/led_rouge.png");
        Assets.getImage("components/resistor.PNG");
        Assets.getImage("components/teensy.png");
        Assets.getMusic("Chill Wave");
        Assets.getMusic("Deliberate Thought");
        Assets.getMusic("Inspired");
        Assets.getMusic("Unwritten Return");
        Assets.getMusic("Windswept");
        Assets.getImage("elements/drawer.png");
        Assets.getImage("screens/proto_background.png");
        Assets.getImage("screens/simulation_background.png");
        Assets.getImage("screens/title.png");
        Assets.getImage("ui/pause_button.png");
        Assets.getImage("ui/play_button.png");

        // DDR
        Assets.getImage("ddr/Background.png");
        Assets.getImage("ddr/BackgroundAllo.png");
        Assets.getImage("ddr/BackgroundJavaS.jpg");
        Assets.getImage("ddr/Backgroundlvl1.jpg");
        Assets.getImage("ddr/barreLp.png");
        Assets.getImage("ddr/cadreBarreLp.png");
        Assets.getImage("ddr/leftArrow.png");
        Assets.getImage("ddr/lightLeftArrow.png");
        Assets.getImage("ddr/note.png");
        Assets.getImage("ddr/noteMissed.png");
        Assets.getMusic("ddr/sheep.wav");

        // taupes
        Assets.getImage("defonceuse/rose.png");
        Assets.getImage("defonceuse/silhouette.png");
        Assets.getImage("defonceuse/victorPatate.png");
        Assets.getImage("defonceuse/victory.png");
        Assets.getImage("defonceuse/william.png");


//        Thread.sleep(5000);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // initialisation de JavaFX et de la fenêtre
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

        // main loop
        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                while(now - lastUpdate >= 16_000_000) { // 16ms
                    game.tick();
                    lastUpdate += 16_000_000;
                }
                game.render(ctx);
            }

        };

        // events utiles pour le jeu
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, game::mouseClick);
        stage.addEventFilter(MouseEvent.MOUSE_PRESSED, game::mousePressed);
        stage.addEventFilter(MouseEvent.MOUSE_RELEASED, game::mouseReleased);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, game::mouseDragged);
        stage.addEventFilter(MouseEvent.MOUSE_MOVED, game::mouseMoved);
        stage.addEventFilter(KeyEvent.KEY_PRESSED, game::keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, game::keyReleased);
        stage.addEventFilter(KeyEvent.KEY_TYPED, game::keyTyped);

        timer.start();
        stage.show();
        LoadingWindow.close();
    }
}
