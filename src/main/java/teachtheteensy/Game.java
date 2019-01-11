package teachtheteensy;

import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
        currentScreen.render(ctx);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
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

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void mouseMoved(MouseEvent event) {
        updateMousePos(event);
    }

    public void showScreen(Screen screen) {
        currentScreen = screen;
    }
}
