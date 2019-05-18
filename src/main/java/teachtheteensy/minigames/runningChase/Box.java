package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.Game;

public class Box {
    Image imageBox = Assets.getImage("runningChase/box.png");
    Image imageBreakBox = Assets.getImage("runningChase/breakBox.png");
    private boolean playerInTheBox;
    private int line= (int) Math.ceil(Math.random()*3);  //1,2 ou 3 (3 lignes)
    private int abscisse= Game.getInstance().getScreenWidth();
    private int sizeBox = 200;
    private int tick=0;
    private boolean possibiltyOfHit=true;


    public void render (GraphicsContext ctx){
        Image boxInRealTime = imageBox;
        if(!possibiltyOfHit){
            boxInRealTime = imageBreakBox;
        }
        ctx.drawImage(boxInRealTime, abscisse, (1.5*(line-1))*240+80,sizeBox,sizeBox);
    }

    public int[] whereIsTheBox(){
        int[] placeOfTheBox = new int[3];
        placeOfTheBox[0]=abscisse;
        placeOfTheBox[1]=line;
        placeOfTheBox[2]=sizeBox;
        return placeOfTheBox;
    }

    public boolean hittable(){
        return possibiltyOfHit;
    }

    public void cantBeHit(){
        possibiltyOfHit=false;
    }

    public void tick(int speedOfTheGame){
        abscisse-=speedOfTheGame;
        tick++;
    }



}
