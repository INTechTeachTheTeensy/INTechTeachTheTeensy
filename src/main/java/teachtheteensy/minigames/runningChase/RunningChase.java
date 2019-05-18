package teachtheteensy.minigames.runningChase;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.minigames.Minigame;

import java.util.ArrayList;
import java.util.List;


public class RunningChase extends Minigame {


    private int abscissePlayer=2;

    private Image road = Assets.getImage("runningChase/road.png");
    private Image ecranTitre = Assets.getImage("runningChase/ecranTitre.png");
    private Image gameOver = Assets.getImage("runningChase/gameover.png");
    private int numberOfLife;

    private List<box> boxOnTheScreen= new ArrayList<>();

    private int tick=0;

    private int speedOfTheGame=10; //fois 4 par rapport à tick


    private boolean gameover;
    private Prey player=new Prey();
    private boolean startOfGame=false;
    private life life=new life();

    @Override
    public void tick() {
        if(tick%((int)(800/speedOfTheGame))==0 && startOfGame){
            boxOnTheScreen.add(new box());
        }

        if(tick%1000==0){
            speedOfTheGame++;
        }

        player.tick(abscissePlayer,boxOnTheScreen);
        numberOfLife=player.getNumberOfLife();
        life.tick(numberOfLife);
        for(box Box:boxOnTheScreen){
            Box.tick(speedOfTheGame);
            if((Box.whereIsTheBox()[0]+Box.whereIsTheBox()[2])<=0){
                boxOnTheScreen.remove(Box);
            }
        }
        if(numberOfLife==0){
            gameover=true;
        }
        tick++;
    }

    @Override
    public void render(GraphicsContext ctx) {
        if(gameover){
            ctx.drawImage(gameOver,0,0,Game.getInstance().getScreenWidth(),Game.getInstance().getScreenHeight());
        }
        else {
            if (startOfGame) {
                ctx.setFill(Color.GREEN);
                for (int k = 0; k < speedOfTheGame; k++) {
                    ctx.drawImage(road, Game.getInstance().getScreenWidth() - speedOfTheGame * ((tick + k * Game.getInstance().getScreenWidth() / speedOfTheGame) % Game.getInstance().getScreenWidth()), 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
                }
                player.render(ctx);
                life.render(ctx);
                for (box Box : boxOnTheScreen) {
                    Box.render(ctx);
                }
            } else {
                ctx.drawImage(ecranTitre, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent event) {
        // permet de changer la direction du serpent en fonction de la touche pressée
        switch (event.getCode()) {
            case DOWN:
                if(abscissePlayer != 3) {
                    abscissePlayer+=1;
                }
                break;

            case UP:
                if(abscissePlayer != 1) {
                    abscissePlayer-=1;
                }
                break;
            case ENTER:
                startOfGame=true;
        }
    }
}
