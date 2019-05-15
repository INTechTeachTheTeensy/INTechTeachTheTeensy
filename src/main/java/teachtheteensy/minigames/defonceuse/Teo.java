package teachtheteensy.minigames.defonceuse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.math.MutableRectangle;

import java.awt.*;
public class Teo {
    private final Image imageTeo=Assets.getImage("defonceuse/teo.png");
    MutableRectangle rectangle;
    private int positionTeo[][]={{200, -150+600},{1500, -180+600},{840, -230+600},{525, -550+600},{525, 100+600},{1175, -450+600},{1175, 100+600}};
    int tick;
    int i;

    public Teo(){
        this.rectangle=new MutableRectangle(-1000, -1000,imageTeo.getWidth(),imageTeo.getHeight()*0.4);
    }

    public void render (GraphicsContext ctx){
        //ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        ctx.drawImage(imageTeo, rectangle.getX(),rectangle.getY()-600,imageTeo.getWidth()*1.3,imageTeo.getHeight()*1.3);

    }

    public void tick(){
        tick=tick+1;
        if (tick>=100){
            i=(int)(Math.random()*6);
            rectangle.setX(positionTeo[i][0]);
            rectangle.setY(positionTeo[i][1]);
            tick=0;

        }

    }

    public boolean isPositionInTeo(double sceneX, double sceneY){
        return  (sceneX<=rectangle.getX()+rectangle.getWidth()
                && sceneY<=rectangle.getY()+rectangle.getHeight()
                && sceneX>=rectangle.getX()
                && sceneY>=rectangle.getY());

    }


}
