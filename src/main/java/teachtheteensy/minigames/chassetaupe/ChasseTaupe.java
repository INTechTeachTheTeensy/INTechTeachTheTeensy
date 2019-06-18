package teachtheteensy.minigames.chassetaupe;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.minigames.Minigame;
import teachtheteensy.screens.TitleScreen;

import java.util.ArrayList;
import java.util.List;

public class ChasseTaupe extends Minigame {
    private List<Taupe> listTaupe= new ArrayList<Taupe>();
    boolean success;
    boolean gameOver;
    boolean gameOverBis;
    int i;                                                 // on stocke la place de la tête associée à la bulle
    Teo teo = new Teo();
    private List<Bulle> listBulle= new ArrayList<Bulle>();
    private List<Compliment> listCompliment= new ArrayList<Compliment>();

    public ChasseTaupe() {
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/victorPatate.png"), 200, 400));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/william.png"), 1500, 400));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/ug.png"), 850, 400));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/remi.png"), 525, 100));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/lucasdetoure.png"), 525, 700));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/victorPatate.png"), 1175, 100));
        listTaupe.add(new Taupe(Assets.getImage("chasse taupe/victorPatate.png"), 1175, 700));
        listBulle.add(new Bulle(Assets.getImage("chasse taupe/bulle_nul.png")));
        listBulle.add(new Bulle(Assets.getImage("chasse taupe/datasheet.png")));
        listBulle.add(new Bulle(Assets.getImage("chasse taupe/infos.png")));
        listBulle.add(new Bulle(Assets.getImage("chasse taupe/personnellement.png")));
        listCompliment.add(new Compliment(Assets.getImage("chasse taupe/beau_travail.png")));
        listCompliment.add(new Compliment(Assets.getImage("chasse taupe/Bien_joué.png")));
        listCompliment.add(new Compliment(Assets.getImage("chasse taupe/bravo.png")));

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

        ctx.drawImage(Assets.getImage("chasse taupe/rose.png"), 0, 0, 1920, 1080);
        for (Taupe taupe:listTaupe)
        {
            taupe.render(ctx);
                for (Bulle bulle:listBulle) {
                     if(bulle.i==listTaupe.indexOf(taupe) && !taupe.cacheTaupe) {
                       bulle.render(ctx);
                       break;
                     }
                }
                for (Compliment compliment:listCompliment){
                    compliment.render(ctx);
                }
        };
        teo.render(ctx);
        if (success){
            ctx.drawImage(Assets.getImage("chasse taupe/rose.png"), 0, 0, 1920, 1080);
            ctx.drawImage(Assets.getImage("chasse taupe/success.png"),0,0,1920,1080);
        }
        if(gameOver){
            ctx.drawImage(Assets.getImage("chasse taupe/game_over.png"),0,0,1920,1080);
        }
        if(gameOverBis){
            ctx.drawImage(Assets.getImage("chasse taupe/game_over2.png"),0,0,1920,1080);
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
                        gameOverBis=true;
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

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Q:
                Game.getInstance().showScreen(new TitleScreen());
                break;
        }
    }
}