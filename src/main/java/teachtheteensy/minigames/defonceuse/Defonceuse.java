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
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 200, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/william.png"), 1500, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 850, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 525, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 525, 700));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 1175, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 1175, 700));

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
        System.out.println(sceneX);
        System.out.println(sceneY);
        System.out.println(listTaupe.size());
        for (Taupe taupe:listTaupe)
        {
            System.out.println(taupe.isPositionInTaupe(sceneX,sceneY));
            if(taupe.isPositionInTaupe(sceneX,sceneY)){
                taupe.rotateTete =true;
            }
            else {
                taupe.rotateTete=false;
            }

        }

    }
}
