package teachtheteensy.minigames.defonceuse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.minigames.Minigame;

import java.util.ArrayList;
import java.util.List;

public class Defonceuse extends Minigame {
    private List<Taupe> listTaupe= new ArrayList<Taupe>();
    public Defonceuse () {
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/william.png"), 500, 500, Math.round(200*1.5f), Math.round(280*1.5f),"droite"));

    }

    @Override
    public void tick() {
        for (Taupe taupe: listTaupe) {
            taupe.tick();
        }

    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.drawImage(Assets.getImage("defonceuse/rose.png"), 0, 0, 1920, 1080);
        for (Taupe taupe:listTaupe)
        {
            taupe.render(ctx);

        };

    }
    @Override
    public void leftClick(double sceneX, double sceneY){
        for (Taupe taupe:listTaupe) {
            if(taupe.isPositionInTaupe(sceneX,sceneY)){
                taupe.rotateTete =true;
                return;
            }

        }

    }
}
