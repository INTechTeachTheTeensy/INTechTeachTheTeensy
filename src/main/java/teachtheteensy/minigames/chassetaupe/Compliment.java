package teachtheteensy.minigames.chassetaupe;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.math.MutableRectangle;

public class Compliment {
    private final Image imageCompliment;
    MutableRectangle rectangle;
    private int positionBulle[][] = {{260-50,350+20},{1560-50,350+20},{910-50,350+20},{585-50,50+20},{585-50,650+20},{1175+60-50,50+20},{1175-50,650+20}};
    int tick;
    int i = -1;

    public Compliment (Image imageCompliment){
        this.imageCompliment =imageCompliment;
        this.rectangle=new MutableRectangle(-100000, -100000,250,250);
    }

    public void render (GraphicsContext ctx){
        //ctx.strokeRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(), rectangle.getHeight());
        ctx.drawImage(imageCompliment, rectangle.getX()+50,rectangle.getY()-20,imageCompliment.getWidth()*0.25,imageCompliment.getHeight()*0.25);

    }

    public void tick(){
        tick=tick+1;
        if (tick>=150){
            i=(int)(Math.random()*6);
            rectangle.setX(positionBulle[i][0]);
            rectangle.setY(positionBulle[i][1]);
            tick=0;
        }

    }
}
