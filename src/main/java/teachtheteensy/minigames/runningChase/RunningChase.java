package teachtheteensy.minigames.runningChase;

import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.minigames.Minigame;
import teachtheteensy.screens.TitleScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static teachtheteensy.boot.GameApp.scene;


public class RunningChase extends Minigame {


    private int abscissePlayer=2;

    private Image road = Assets.getImage("runningChase/road.png");
    private Image ecranTitre = Assets.getImage("runningChase/ecranTitre.png");
    private Image gameOver = Assets.getImage("runningChase/gameover.png");
    private int numberOfLife;

    private List<Box> boxOnTheScreen= new ArrayList<>();

    private int tick=0;

    private int speedOfTheGame=20; //fois 20 par rapport à tick


    private boolean gameover;
    private Prey player=new Prey();
    private boolean startOfGame=false;
    private Life life=new Life();

    private long lastUpdate;

    @Override
    public void tick() {
        System.out.println(">> dt: "+(System.currentTimeMillis()-lastUpdate));
        lastUpdate = System.currentTimeMillis();
        if(tick% (800/speedOfTheGame) ==0 && startOfGame){
            boxOnTheScreen.add(new Box());
        }

        if(tick%300==0 && startOfGame){
            speedOfTheGame++;
        }

        player.tick(abscissePlayer,boxOnTheScreen);
        numberOfLife=player.getNumberOfLife();
        life.tick(numberOfLife);

        Iterator<Box> iterator = boxOnTheScreen.iterator();
        while(iterator.hasNext()) {
            Box box = iterator.next();
            box.tick(speedOfTheGame);
            if((box.whereIsTheBox()[0]+box.whereIsTheBox()[2])<=0){
                iterator.remove();
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
                int position = -(speedOfTheGame*tick);
                if(position+Game.getInstance().getScreenWidth() < 0) {
                    position += Game.getInstance().getScreenWidth();
                    position %= Game.getInstance().getScreenWidth();
                }
                ctx.drawImage(road, position, 0);
                ctx.drawImage(road, position+Game.getInstance().getScreenWidth(), 0);
                player.render(ctx);
                life.render(ctx);
                for (Box box : boxOnTheScreen) {
                    box.render(ctx);
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
                break;
            case Q:
                Game.getInstance().showScreen(new TitleScreen());
                break;
        }
    }

    @Override
    public void open(Screen previousScreen) {
        super.open(previousScreen);
        scene.setCursor(Cursor.NONE);
    }

    @Override
    public void close(Screen newScreen) {
        super.close(newScreen);
        scene.setCursor(Cursor.DEFAULT);
    }
}
