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
    private String status;
    private int counter;

    public Arrow(int col) {
        this.col=col;
        this.status="OFF";
        this.counter=4;
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


    public void render(GraphicsContext ctx) {
        if (status.equals("ON")) {
            ctx.drawImage(imageActivatedArrow, Game.getInstance().getScreenWidth() * 2 / 3 + col*110, Game.getInstance().getScreenHeight() - 150, 100, 100);
            counter--;
        } else {
            ctx.drawImage(imageArrow, Game.getInstance().getScreenWidth() * 2 / 3 + col*110, Game.getInstance().getScreenHeight() - 150, 100, 100);
        }
    }

    public void setStatus(String state) {
        status=state;
    }

    public String getStatus() {
        return (status);
    }

    public int getCounter() {
        return (counter);
    }

    public void decCounter() {
        counter--;
    }

    public void setCounter(int i) {
        counter=i;
    }
}
