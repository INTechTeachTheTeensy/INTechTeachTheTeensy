package teachtheteensy.screens.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import teachtheteensy.Assets;
import teachtheteensy.Renderable;
import teachtheteensy.math.IRectangle;
import teachtheteensy.minigames.Minigame;
import teachtheteensy.minigames.Minigames;

public class Button implements Renderable {

    private final IRectangle boundingBox;
    private final Image image;
    private Paint textColor;
    private Paint background;
    private Minigames minigame;

    public Button(Minigames minigame, IRectangle boundingBox) {
        this.minigame = minigame;
        this.image = Assets.getImage("elements/"+minigame.getImgPath()+".png");
        this.boundingBox = boundingBox;
        this.background = Color.AQUAMARINE;
        this.textColor = Color.BLACK;
    }

    @Override
    public void render(GraphicsContext ctx) {
        /*ctx.setFill(background);
        ctx.fillRoundRect(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight(), 10.0, 10.0);
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setFill(textColor);
        ctx.fillText(text, boundingBox.getX()+boundingBox.getWidth()/2, boundingBox.getY()+boundingBox.getHeight()/2);*/
        ctx.drawImage(image, boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
    }

    public Minigames getMinigame() {
        return minigame;
    }

    public Paint getTextColor() {
        return textColor;
    }

    public void setTextColor(Paint textColor) {
        this.textColor = textColor;
    }

    public Paint getBackground() {
        return background;
    }

    public void setBackground(Paint background) {
        this.background = background;
    }

    public IRectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean leftClick(double sceneX, double sceneY) {
        return boundingBox.isPointIn(sceneX, sceneY);
    }

    public boolean rightClick(double sceneX, double sceneY) {
        return boundingBox.isPointIn(sceneX, sceneY);
    }

    public boolean mousePressed(double sceneX, double sceneY) {
        return boundingBox.isPointIn(sceneX, sceneY);
    }

    public boolean mouseReleased(double sceneX, double sceneY) {
        return boundingBox.isPointIn(sceneX, sceneY);
    }

    public boolean mouseDragged(double sceneX, double sceneY) {
        return boundingBox.isPointIn(sceneX, sceneY);
    }
}
