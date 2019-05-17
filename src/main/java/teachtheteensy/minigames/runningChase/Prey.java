package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;


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
    private int hit=0;
    private int retour=0;
    private int line=2; // 1, 2 ou 3 -->ligne sur laquelle se trouve le joueur


    public void tick(int line) {
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
            else if(hit==32){
                imageInRealTime=boom4;
                hit=0;
                touchABox=false;
            }
        }
    }

    public void render(GraphicsContext ctx) {

        ctx.drawImage(imageInRealTime,1000-this.retour, 80+((line-1)*355), 200,200);
    }


}
