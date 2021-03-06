package teachtheteensy;

import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import teachtheteensy.boot.GameApp;
import teachtheteensy.music.MusicHandle;
import teachtheteensy.music.MusicThread;
import teachtheteensy.screens.PrototypeScreen;
import teachtheteensy.screens.TitleScreen;

/**
 * Représente le jeu
 */
public class Game {

    /**
     * Permet de ne pas avoir à balader un objet Game partout
     */
    private static Game INSTANCE;

    /**
     * Thread qui joue la musique
     */
    private final MusicThread musicPlayer;

    private double mouseX;
    private double mouseY;
    private final int screenWidth;
    private final int screenHeight;
    private Screen currentScreen;

    /**
     * @param screenWidth largeur de l'écran en pixels
     * @param screenHeight hauteur de l'écran en pixels
     */
    public Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        INSTANCE = this;

        currentScreen = new TitleScreen();
        musicPlayer = new MusicThread();
        musicPlayer.start();
    }

    /**
     * Appelé à chaque frame pour mettre à jour l'état du jeu
     */
    public void tick() {
        // TODO
        currentScreen.tick();
    }

    /**
     * Appelé à chaque frame pour dessiner le contenu du jeu
     * @param ctx le contexte permettant de dessiner à l'écran
     */
    public void render(GraphicsContext ctx) {
        // TODO
        ctx.save();
        ctx.scale(1.0/xScale(), 1.0/yScale());
        currentScreen.render(ctx);
        ctx.restore();
    }

    public int getScreenWidth() {
        return 1920;
    }

    public int getScreenHeight() {
        return 1080;
    }

    public static Game getInstance() {
        return INSTANCE;
    }

    // permet d'avoir des plus petites résolutions sans se casser la tête
    private double xScale() {
        return 1920. / screenWidth;
    }
    private double yScale() {
        return 1080. / screenHeight;
    }

    private void updateMousePos(MouseEvent event) {
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    public void keyPressed(KeyEvent event) {
        currentScreen.keyPressed(event);
    }

    public void keyReleased(KeyEvent event) {
        switch (event.getCode()){
            case ESCAPE:
                GameApp.stage.close();
                break;
            default:
                currentScreen.keyReleased(event);
        }
    }

    public void mouseClick(MouseEvent event) {
        updateMousePos(event);
        if(event.getButton() == MouseButton.PRIMARY) {
            currentScreen.leftClick(event.getSceneX()*xScale(), event.getSceneY()*yScale());
        } else if(event.getButton() == MouseButton.SECONDARY) {
            currentScreen.rightClick(event.getSceneX()*xScale(), event.getSceneY()*yScale());
        }
    }

    public void mouseReleased(MouseEvent event) {
        updateMousePos(event);
        if(event.getButton() == MouseButton.PRIMARY) {
            currentScreen.mouseReleased(event.getSceneX()*xScale(), event.getSceneY()*yScale());
        }
    }

    public void mousePressed(MouseEvent event) {
        updateMousePos(event);
        if(event.getButton() == MouseButton.PRIMARY) {
            currentScreen.mousePressed(event.getSceneX()*xScale(), event.getSceneY()*yScale());
        }
    }

    public void mouseDragged(MouseEvent event) {
        updateMousePos(event);
        if(event.getButton() == MouseButton.PRIMARY) {
            currentScreen.mouseDragged(event.getSceneX()*xScale(), event.getSceneY()*yScale());
        }
    }

    public void keyTyped(KeyEvent event) {
        currentScreen.keyTyped(event);
    }

    public double getMouseX() {
        return mouseX * xScale();
    }

    public double getMouseY() {
        return mouseY * yScale();
    }

    public void mouseMoved(MouseEvent event) {
        updateMousePos(event);
    }

    public void showScreen(Screen screen) {
        if(currentScreen != null) {
            currentScreen.close(screen);
        }
        if(currentScreen != screen) {
            screen.open(currentScreen);
        }
        currentScreen = screen;
    }

    public void playMusic(MusicHandle music) {
        musicPlayer.play(music);
    }

    public void stopMusic() {
        musicPlayer.stopMusic();
    }

    public boolean isMusicPlaying(MusicHandle handle) {
        return musicPlayer.getCurrentMusic() == handle;
    }
}
