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

public class Taupe {
    private final Image imageTete;
    //private final Image silhouette = Assets.getImage("defonceuse/silhouette.png");
    private final Image cache = Assets.getImage("defonceuse/rose.png");
    MutableRectangle rectangle;
    boolean rotateTete;
    boolean cacheTaupe;
    float angleTete=8;
    float angleRotation = 0;
    boolean droite=true;
    int tick;
    double i;
    int tac;


    public Taupe (Image imageTete, int x, int y){
        this.imageTete = imageTete;
        this.rectangle = new MutableRectangle(x,y,imageTete.getWidth(),imageTete.getHeight()-200);
    }

    public void render (GraphicsContext ctx){
        //ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        //ctx.drawImage(silhouette, rectangle.getX()-rectangle.getWidth()*0.25,rectangle.getY()-rectangle.getHeight()*0.2,rectangle.getWidth()*1.5, rectangle.getHeight()*1.5);
        turnTete(ctx,angleRotation);
        cacheToiTaupe(ctx);
    }


    public boolean isPositionInTaupe(double sceneX, double sceneY){
        return  (sceneX<=rectangle.getX()+rectangle.getWidth()
                && sceneY<=rectangle.getY()+rectangle.getHeight()
                && sceneX>=rectangle.getX()
                && sceneY>=rectangle.getY());

    }

    public void turnTete(GraphicsContext ctx, float angle){
                ctx.save();
                //ctx.translate(rectangle.getX()+rectangle.getWidth()/4+imageTete.getWidth()/2,rectangle.getY()-rectangle.getHeight()/6+imageTete.getHeight()/2);
                ctx.translate(rectangle.getX() + imageTete.getWidth() / 2, rectangle.getY() + imageTete.getHeight() / 2);
                ctx.rotate(angle);
                ctx.drawImage(imageTete, -imageTete.getWidth() / 2, -imageTete.getHeight() * 0.7, imageTete.getWidth(), imageTete.getHeight());
                ctx.restore();
    }

    public void tick(){
        tick= tick +1;
        if (rotateTete && tick<20)
        {
            if (angleRotation<angleTete && droite==true){
                angleRotation=angleRotation+2;
            }
            if(Math.abs(angleRotation)==angleTete){
                droite=(!droite);
            }
            if (angleRotation>-angleTete && droite==false){
                angleRotation=angleRotation-2;
            }

        }
        if(rotateTete && tick>=20 && !cacheTaupe){
            cacheTaupe=true;
            i=Math.random()*100;
            tac = tick + (int)i+50;
        }
        if(!rotateTete){
            tick=0;
        }
        if (tick>=tac && cacheTaupe){
            cacheTaupe=false;
            rotateTete=false;
            tick=0;
            angleRotation=0;
            tac=0;
        }


    }

    public void cacheToiTaupe(GraphicsContext ctx){
        if (cacheTaupe){
            ctx.drawImage(cache,rectangle.getX(),rectangle.getY()-50,rectangle.getWidth(), rectangle.getHeight()+50);
        }

    }

}
