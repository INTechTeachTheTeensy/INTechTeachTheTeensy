package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;

import java.util.List;


public class Prey {
    private int tick=0;
    private Image imageInRealTime;
    private Image boom1 = Assets.getImage("runningChase/playerBrownHairroll1.png");
    private Image boom2 = Assets.getImage("runningChase/playerBrownHairroll2.png");
    private Image boom3 = Assets.getImage("runningChase/playerBrownHairroll3.png");
    private Image boom4 = Assets.getImage("runningChase/playerBrownHairroll4.png");
    private Image run1 = Assets.getImage("runningChase/playerBrownHairleft1.png");
    private Image run2 = Assets.getImage("runningChase/playerBrownHairleft2.png");
    private Image run3 = Assets.getImage("runningChase/playerBrownHairleft3.png");
    private Image run4 = Assets.getImage("runningChase/playerBrownHairleft4.png");
    private boolean touchABox = false;
    private int sizeOfPlayer=200;
    private int abscisseOfPlayer=1000;
    private int numberOfLife=3;
    private int hit=0;
    private int retour=0;
    private int line=2; // 1, 2 ou 3 -->ligne sur laquelle se trouve le joueur


    public void tick(int line, List<box> boxOnTheScreen) {
        int[] placeOfTheBox;
        for(box Box:boxOnTheScreen){
            placeOfTheBox = Box.whereIsTheBox();
            if(placeOfTheBox[1]==line && (placeOfTheBox[0]+placeOfTheBox[2])>=(abscisseOfPlayer-this.retour) && (placeOfTheBox[0])<=(abscisseOfPlayer+sizeOfPlayer-this.retour) && Box.hittable()){
                touchABox=true;
                Box.cantBeHit();
                numberOfLife--;
            }
        }
        this.line=line;
        tick++;
        if(touchABox){
            hit++;
            if(hit%7==0){
                retour+=75;
            }
        }
        if(!touchABox){
            if (this.tick%28<=7){
                imageInRealTime=run1;
            }
            else if (this.tick%28<=14){
                imageInRealTime=run2;
            }
            else if (this.tick%28<=21){
                imageInRealTime=run3;
            }
            else {
                imageInRealTime=run4;
            }
        }
        else {
            if(this.hit==0){
                imageInRealTime=boom1;
            }
            else if(this.hit==7){
                imageInRealTime=boom2;
            }
            else if(this.hit==14){
                imageInRealTime=boom3;
            }
            else if(this.hit==21){
                imageInRealTime=boom4;
            }
            else if(hit==28){
                imageInRealTime=boom4;
                hit=0;
                touchABox=false;
            }
        }
    }

    public int getNumberOfLife(){
        return numberOfLife;
    }

    public void render(GraphicsContext ctx) {

        ctx.drawImage(imageInRealTime,abscisseOfPlayer-this.retour, 80+((line-1)*355), sizeOfPlayer,sizeOfPlayer);
    }


}
