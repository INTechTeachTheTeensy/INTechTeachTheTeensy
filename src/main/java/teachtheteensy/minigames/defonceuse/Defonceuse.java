package teachtheteensy.minigames.defonceuse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.minigames.Minigame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Defonceuse extends Minigame {
    private List<Taupe> listTaupe= new ArrayList<Taupe>();
    boolean success;
    private List<Bulle> listBulle= new ArrayList<Bulle>();
    public Defonceuse () {
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 200, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/william.png"), 1500, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/ug.png"), 850, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/remi.png"), 525, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/lucasdetoure.png"), 525, 700));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/remi.png"), 1175, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 1175, 700));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/bulle_nul.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/datasheet.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/infos.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/personnellement.png")));

    }



    @Override
    public void tick() {
        for (Taupe taupe: listTaupe) {
            taupe.tick();
            System.out.println(taupe.cacheTaupe);
            System.out.println(taupe.i);
            System.out.println(taupe.tick);
            System.out.println(taupe.tac);

        }
        success();
        for (Bulle bulle: listBulle) {
            bulle.tick();
        }
    }

    @Override
    public void render(GraphicsContext ctx) {

        ctx.drawImage(Assets.getImage("defonceuse/rose.png"), 0, 0, 1920, 1080);
        for (Taupe taupe:listTaupe)
        {
            taupe.render(ctx);
        };
        if (success){
            ctx.drawImage(Assets.getImage("defonceuse/rose.png"), 0, 0, 1920, 1080);
            ctx.drawImage(Assets.getImage("defonceuse/victory.png"),0,0,1920,1080);
        }
        for (Bulle bulle:listBulle) {
            bulle.render(ctx);
        }

    }
    @Override
    public void leftClick(double sceneX, double sceneY){

        for (Taupe taupe:listTaupe)
        {
            if(taupe.isPositionInTaupe(sceneX,sceneY)){
                taupe.rotateTete =true;
            }

        }

    }
    public void success(){
        for (Taupe taupe:listTaupe)
        {
            if(!taupe.cacheTaupe){
                return;
            }

        }
        success=true;
    }
}
