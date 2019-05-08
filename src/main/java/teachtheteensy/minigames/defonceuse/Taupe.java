package teachtheteensy.minigames.defonceuse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.math.MutableRectangle;

public class Taupe {
    private final Image imageTete;
    private final Image silhouette = Assets.getImage("defonceuse/silhouette.png");
    MutableRectangle rectangle;
    boolean rotateTete;
    float angleTete;
    String directionTete;

    public Taupe (Image imageTete, int x, int y, int width, int height, String directionTete){
        this.imageTete = imageTete;
        this.rectangle = new MutableRectangle(x,y,width,height);
        this.directionTete= directionTete;
    }

    public void render (GraphicsContext ctx){
        ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        ctx.drawImage(silhouette, rectangle.getX()-rectangle.getWidth()*0.25,rectangle.getY()-rectangle.getHeight()*0.2,rectangle.getWidth()*1.5, rectangle.getHeight()*1.5);
        turnTete(ctx,angleTete,directionTete);

    }


    public boolean isPositionInTaupe(double sceneX, double sceneY){
        return  (sceneX<=rectangle.getX()+rectangle.getWidth()
                && sceneY<=rectangle.getY()+rectangle.getHeight()
                && sceneX>=rectangle.getX()
                && sceneY>=rectangle.getY());

    }

    public void turnTete(GraphicsContext ctx, float angle, String directionTete){
        ctx.save();
        ctx.translate(rectangle.getX()+rectangle.getWidth()/4+imageTete.getWidth()/2,rectangle.getY()-rectangle.getHeight()/6+imageTete.getHeight()/2);
        ctx.rotate(angle);
        ctx.drawImage(imageTete, -imageTete.getWidth()/2,-imageTete.getHeight()/2,rectangle.getWidth()/2,rectangle.getHeight()/1.5);
        ctx.restore();
    }

    public void tick(){
        System.out.println(directionTete);
        int angle = 0;
        if (rotateTete)
        {
            if (directionTete.equals("droite")){
                angle=angle+1;
                if (angle==angleTete){
                    directionTete="gauche";
                }
            }
            if(directionTete.equals("gauche")){
                angle=angle-1;
                if (Math.abs(angle)==angleTete){
                    directionTete="none";
                }
            }
        }
    }
}
