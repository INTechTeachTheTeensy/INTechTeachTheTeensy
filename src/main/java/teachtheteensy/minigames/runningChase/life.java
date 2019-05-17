package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;

public class life {
    private Image lifeUp = Assets.getImage("runningChase/heart.png");
    private Image lifeDown = Assets.getImage("runningChase/brokenHeart.png");
    private int tick=0;
    private int numberOfLife;


    public void tick(int numberOfLife){
        this.numberOfLife=numberOfLife;
        tick++;
    }

    public void render(GraphicsContext ctx) {
        if(numberOfLife==3){
            ctx.drawImage(lifeUp, 30, 30, 50, 50);
            ctx.drawImage(lifeUp, 85, 30, 50, 50);
            ctx.drawImage(lifeUp, 140, 30, 50, 50);
        }
        else if(numberOfLife==2){
            ctx.drawImage(lifeUp, 30, 30, 50, 50);
            ctx.drawImage(lifeUp, 85, 30, 50, 50);
            ctx.drawImage(lifeDown, 140, 30, 50, 50);
        }
        else if(numberOfLife==1){
            ctx.drawImage(lifeUp, 30, 30, 50, 50);
            ctx.drawImage(lifeDown, 85, 30, 50, 50);
            ctx.drawImage(lifeDown, 140, 30, 50, 50);
        }
        else{
            ctx.drawImage(lifeDown, 30, 30, 50, 50);
            ctx.drawImage(lifeDown, 85, 30, 50, 50);
            ctx.drawImage(lifeDown, 140, 30, 50, 50);
        }
    }
}
