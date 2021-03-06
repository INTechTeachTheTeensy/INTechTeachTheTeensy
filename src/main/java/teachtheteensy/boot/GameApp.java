package teachtheteensy.boot;

import javafx.application.Application;
import javafx.application.Platform;
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

/**
 * Classe contenant l'Application JavaFX. Initialises la fenêtre et le jeu et laisse ce dernier prendre le contrôle
 */
public class GameApp extends Application {
    public static Scene scene;
    public static Stage stage;

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
        Assets.getImage("chasse taupe/rose.png");
        Assets.getImage("chasse taupe/silhouette.png");
        Assets.getImage("chasse taupe/victorPatate.png");
        Assets.getImage("chasse taupe/victory.png");
        Assets.getImage("chasse taupe/william.png");


//        Thread.sleep(5000);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // initialisation de JavaFX et de la fenêtre
        this.stage=stage;
        stage.setFullScreen(true);
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        int width = resolution.width;
        int height = resolution.height;
        stage.setTitle("Teach The Teensy");
        Canvas canvas = new Canvas(width, height);
        Group group = new Group();
        group.getChildren().add(canvas);
        scene = new Scene(group);
        stage.setScene(scene);
        stage.setResizable(false);

        GraphicsContext ctx = canvas.getGraphicsContext2D();
        Game game = new Game(width, height);

        // main loop
        /*AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = System.nanoTime();

            private long dt = 16_000_000;

            @Override
            public void handle(long now) {
                while(now - lastUpdate >= dt) { // 16ms
                    game.tick();
                    lastUpdate += dt;
                    game.render(ctx);
                }
            }

        };*/
        Thread animThread = new Thread("Update thread") {
            private long lastUpdate = System.nanoTime();

            private long dt = 16_000_000;

            {
                setDaemon(true);
                setPriority(Thread.MAX_PRIORITY);
            }

            @Override
            public void run() {
                while(true) {
                    long now = System.nanoTime();
                    while(now - lastUpdate >= dt) { // 16ms
                        game.tick();
                        lastUpdate += dt;
                        Platform.runLater(() -> game.render(ctx));
                    }

                    Thread.yield();
                }
            }
        };
        animThread.start();

        // events utiles pour le jeu
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, game::mouseClick);
        stage.addEventFilter(MouseEvent.MOUSE_PRESSED, game::mousePressed);
        stage.addEventFilter(MouseEvent.MOUSE_RELEASED, game::mouseReleased);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, game::mouseDragged);
        stage.addEventFilter(MouseEvent.MOUSE_MOVED, game::mouseMoved);
        stage.addEventFilter(KeyEvent.KEY_PRESSED, game::keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, game::keyReleased);
        stage.addEventFilter(KeyEvent.KEY_TYPED, game::keyTyped);

        //timer.start();
        stage.show();
        LoadingWindow.close();
    }
}
