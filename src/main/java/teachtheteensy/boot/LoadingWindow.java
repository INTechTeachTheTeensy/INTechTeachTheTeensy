package teachtheteensy.boot;

import com.sun.javafx.tk.Toolkit;
import com.sun.scenario.animation.shared.PulseReceiver;
import com.sun.scenario.animation.shared.TimerReceiver;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingWindow extends Preloader {

    private final static int GEAR_WIDTH = 328;
    private final static int GEAR_HEIGHT = 308;

    private static LoadingWindow instance;
    private double angle;
    private Stage stage;
    private AnimationTimer timer;

    public LoadingWindow() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image intech = new Image(getClass().getResourceAsStream("/loading/intech.png"));
        Image gear = new Image(getClass().getResourceAsStream("/loading/gear.png"));
        Image gearBlack = new Image(getClass().getResourceAsStream("/loading/gear_black.png"));

        int margin = 50;
        int width = (int) intech.getWidth() + margin*2;
        int height = (int) intech.getHeight() + margin*2;
        Canvas canvas = new Canvas(width, height);
        Group group = new Group();
        group.getChildren().add(canvas);
        Scene scene = new Scene(group, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOpacity(0.0);
        stage.centerOnScreen();

        GraphicsContext ctx = canvas.getGraphicsContext2D();

        stage.initStyle(StageStyle.TRANSPARENT);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if(now - lastUpdate >= 16_000_000) { // 16ms
                    newFrame();
                    lastUpdate = now;

                    ctx.clearRect(0,0,width,height);
                    ctx.save();
                    ctx.translate(margin, margin);
                    ctx.save();
                    ctx.translate(10,10);
                    ctx.translate(GEAR_WIDTH/2, GEAR_HEIGHT/2);
                    ctx.rotate(angle);
                    ctx.drawImage(gearBlack, -GEAR_WIDTH/2, -GEAR_HEIGHT/2);
                    ctx.restore();

                    ctx.save();
                    ctx.translate(GEAR_WIDTH/2, GEAR_HEIGHT/2);
                    ctx.rotate(angle);
                    ctx.drawImage(gear, -GEAR_WIDTH/2, -GEAR_HEIGHT/2);
                    ctx.restore();

                    ctx.drawImage(intech, 0, 0);

                    ctx.restore();
                }
                System.out.println("handle "+now);
            }

        };

        timer.start();

        this.timer = timer;

        this.stage = stage;
        stage.show();
    }

    public static void close() {
        instance.stage.close();
        instance.timer.stop();
    }

    private void newFrame() {
        angle++;
    }

}
