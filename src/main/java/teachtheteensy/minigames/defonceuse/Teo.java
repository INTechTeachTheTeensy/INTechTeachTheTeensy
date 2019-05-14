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
    private int positionTeo[][]={{200, -300},{1500, -300},{850, -300},{525, -600},{525, 0},{1175, -600},{1175, 0}};
    int tick;
    int i;

    public Teo(){
        this.rectangle=new MutableRectangle(-1000, -1000,250*1.5,500*2);
    }

    public void render (GraphicsContext ctx){
        //ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        ctx.drawImage(imageTeo, rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());

    }

    public void tick(){
        tick=tick+1;
        if (tick>=500){
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
