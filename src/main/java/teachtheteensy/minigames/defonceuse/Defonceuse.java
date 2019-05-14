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
    boolean gameOver;
    int i;                                                 // on stocke la place de la tête associée à la bulle
    Teo teo = new Teo();
    private List<Bulle> listBulle= new ArrayList<Bulle>();
    private List<Compliment> listCompliment= new ArrayList<Compliment>();

    public Defonceuse () {
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 200, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/william.png"), 1500, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/ug.png"), 850, 400));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/remi.png"), 525, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/lucasdetoure.png"), 525, 700));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 1175, 100));
        listTaupe.add(new Taupe(Assets.getImage("defonceuse/victorPatate.png"), 1175, 700));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/bulle_nul.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/datasheet.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/infos.png")));
        listBulle.add(new Bulle(Assets.getImage("defonceuse/personnellement.png")));
        listCompliment.add(new Compliment(Assets.getImage("defonceuse/beau_travail.png")));
        listCompliment.add(new Compliment(Assets.getImage("defonceuse/Bien_joué.png")));
        listCompliment.add(new Compliment(Assets.getImage("defonceuse/bravo.png")));

    }



    @Override
    public void tick() {

        for (Taupe taupe: listTaupe) {
            taupe.tick();
            System.out.println(taupe.cacheTaupe);

        }
        success();
        for (Bulle bulle: listBulle) {
            bulle.tick();
        }
        for (Compliment compliment: listCompliment){
            compliment.tick();
        }
        teo.tick();

    }

    @Override
    public void render(GraphicsContext ctx) {

        ctx.drawImage(Assets.getImage("defonceuse/rose.png"), 0, 0, 1920, 1080);
        for (Taupe taupe:listTaupe)
        {
            taupe.render(ctx);
                for (Bulle bulle:listBulle) {
                    //if (!taupe.cacheTaupe) {
                       bulle.render(ctx);
                      //  break;
                    //}
                }
                for (Compliment compliment:listCompliment){
                    compliment.render(ctx);
                }
        };
        teo.render(ctx);
        if (success){
            ctx.drawImage(Assets.getImage("defonceuse/rose.png"), 0, 0, 1920, 1080);
            ctx.drawImage(Assets.getImage("defonceuse/success.png"),0,0,1920,1080);
        }
        if(gameOver){
            ctx.drawImage(Assets.getImage("defonceuse/game_over.png"),0,0,1920,1080);
        }



    }
    @Override
    public void leftClick(double sceneX, double sceneY){

        for (Taupe taupe:listTaupe)
        {
            if(taupe.isPositionInTaupe(sceneX,sceneY)){
                taupe.rotateTete =true;
                i=listTaupe.indexOf(taupe);
                for (Compliment compliment:listCompliment)
                {
                    if (i==compliment.i){
                        gameOver=true;
                    }
                }
            }

        }
        if(teo.isPositionInTeo(sceneX,sceneY)){
            gameOver=true;
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
