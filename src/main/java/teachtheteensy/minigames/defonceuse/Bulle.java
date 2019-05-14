package teachtheteensy.minigames.defonceuse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.math.MutableRectangle;
import java.awt.*;

public class Bulle {
    private final Image imageBulle;
    MutableRectangle rectangle;
    private int positionBulle[][] = {{260,350},{1560,350},{910,350},{585,50},{585,650},{1175+60,50},{1175,650}};
    int tick;
    int i;

    public Bulle (Image imageBulle){
        this.imageBulle =imageBulle;
        this.rectangle=new MutableRectangle(-1000, -1000,960/2,510/2);
    }

    public void render (GraphicsContext ctx){
        //ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        ctx.drawImage(imageBulle, rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());

    }

    public void tick(){
        tick=tick+1;
        if (tick>=50){
            i=(int)(Math.random()*6);
            rectangle.setX(positionBulle[i][0]);
            rectangle.setY(positionBulle[i][1]);
            tick=0;

        }

    }


}
