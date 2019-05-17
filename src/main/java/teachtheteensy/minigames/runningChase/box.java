package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;

public class box {
    Image imageBox = Assets.getImage("runningChase/box.png");
    Image imageBreakBox = Assets.getImage("runningChase/breakBox.png");
    private boolean playerInTheBox;
    private int abscisse= (int) Math.ceil(Math.random()*3);  //1,2 ou 3 (3 lignes)
    private int ordonnee;
    private int sizeBox = 200;
    private int tick=0;

    public void render (GraphicsContext ctx){
        Image boxInRealTime = imageBox;
        if(playerInTheBox){
            boxInRealTime = imageBreakBox;
        }
        ctx.drawImage(boxInRealTime, abscisse, ordonnee);
    }

    public int[] whereIsTheBox(){
        int[] placeOfTheBox = new int[3];
        placeOfTheBox[0]=abscisse;
        placeOfTheBox[1]=ordonnee;
        placeOfTheBox[2]=sizeBox;
        return placeOfTheBox;
    }
    public void tick(){
        tick++;
    }



}
