package teachtheteensy.minigames.ddr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;

public class Note {
    private final Image imageNote;
    private final Image imageMissed;
    int x;
    int y;
    int col;
    int v;  // vitesse initiale note

    public Note (int x, int y, int v){
        this.x=x;
        this.y=y;
        this.v=v;
        this.imageNote = Assets.getImage("ddr/note.png");           // Assets.getImage appelle une image préchargée
        this.imageMissed = Assets.getImage("ddr/noteMissed.png");
    }

    public void render (GraphicsContext ctx) {
        Image image=imageNote;
        if (rate()){
            image=imageMissed;
        }
        ctx.drawImage(image, x+10, y, 80, 80);
    }

    public boolean rate (){
        return y > Game.getInstance().getScreenHeight()-100;
    }


}





