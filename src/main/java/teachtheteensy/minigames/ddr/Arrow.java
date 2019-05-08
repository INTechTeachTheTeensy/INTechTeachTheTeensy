package teachtheteensy.minigames.ddr;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;

public class Arrow {
    private int col;
    Image imageArrow;
    Image imageActivatedArrow;

    public Arrow(int col) {
        this.col=col;
        Image leftArrow = Assets.getImage("ddr/leftArrow.png");
        Image lightLeftArrow = Assets.getImage("ddr/lightLeftArrow.png");

        SnapshotParameters para=new SnapshotParameters();
        para.setFill(Color.TRANSPARENT);
        ImageView ivArrow=new ImageView(leftArrow);
        ImageView ivActivatedArrow=new ImageView(lightLeftArrow);
        switch (this.col) {
            case 0:
                this.imageArrow=leftArrow;
                this.imageActivatedArrow=lightLeftArrow;
                break;
            case 1:
                ivArrow.setRotate(90);
                ivActivatedArrow.setRotate(90);
                this.imageArrow=ivArrow.snapshot(para, null);
                this.imageActivatedArrow=ivActivatedArrow.snapshot(para, null);
                break;
            case 2:
                ivArrow.setRotate(270);
                ivActivatedArrow.setRotate(270);
                this.imageArrow=ivArrow.snapshot(para, null);
                this.imageActivatedArrow=ivActivatedArrow.snapshot(para, null);
                break;
            case 3:
                ivArrow.setRotate(180);
                ivActivatedArrow.setRotate(180);
                this.imageArrow=ivArrow.snapshot(para, null);
                this.imageActivatedArrow=ivActivatedArrow.snapshot(para, null);
                break;
        }
    }


    public void render(GraphicsContext ctx, String status) {
        if (status.equals("LIGHT")) {
            ctx.drawImage(imageActivatedArrow, Game.getInstance().getScreenWidth() * 2 / 3 + col*110, Game.getInstance().getScreenHeight() - 150, 100, 100);
        } else {
            ctx.drawImage(imageArrow, Game.getInstance().getScreenWidth() * 2 / 3 + col*110, Game.getInstance().getScreenHeight() - 150, 100, 100);
        }
    }
}
