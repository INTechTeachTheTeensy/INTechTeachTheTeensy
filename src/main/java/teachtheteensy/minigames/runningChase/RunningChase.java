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

    enum SlotType {
        EMPTY, PLAYER, CHASER, BOX;

    }


    enum Placement {
        UP, DOWN, MID;
    }

    private int abscissePlayer=2;   //1,2 ou 3 (3 lignes)
    private int ordonneePlayer=1400;    //fixé au début puis recule lors d'impact
    private int rayPlayer;
    private Image road = Assets.getImage("runningChase/road.png");
    private Image road2 = Assets.getImage("runningChase/road.png");
    private Image ecranTitre = Assets.getImage("runningChase/ecranTitre.png");
    private Placement currentDirection = Placement.MID;
    private Placement newDirection = Placement.MID;
    private int numberOfLife=2;

    private List<box> boxOnTheScreen= new ArrayList<>();

    private static final int WIDTH = 16*3;

    private static final int HEIGHT = 9*3;
    private static final int CELL_SIZE = 40;
    private int tick=0;

    private static final int TICKS_PER_UPDATE = 10;


    private final SlotType[][] grid = new SlotType[WIDTH][HEIGHT];

    private boolean gameOver;
    private Prey player=new Prey();
    private boolean startOfGame=false;
    private life life=new life();

    @Override
    public void tick() {

        player.tick(abscissePlayer);
        life.tick(numberOfLife);
        tick++;
    }

    @Override
    public void render(GraphicsContext ctx) {
        if(startOfGame) {
            ctx.setFill(Color.GREEN);
            ctx.drawImage(road, Game.getInstance().getScreenWidth() - 2 * ((tick + Game.getInstance().getScreenWidth() / 2) % Game.getInstance().getScreenWidth()), 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            ctx.drawImage(road2, Game.getInstance().getScreenWidth() - 2 * (tick % Game.getInstance().getScreenWidth()), 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            player.render(ctx);
            life.render(ctx);

        }
        else{
            ctx.drawImage(ecranTitre,0,0,Game.getInstance().getScreenWidth(),Game.getInstance().getScreenHeight());
        }
    }

    public boolean isPlayerInTheBox(box box){
        int[] placeOfTheBox=new int[3];
        placeOfTheBox= box.whereIsTheBox();
        boolean playerInTheBox=false;
        if(abscissePlayer==placeOfTheBox[0] && (ordonneePlayer+rayPlayer)<(-(int)(placeOfTheBox[3]/2)+placeOfTheBox[2]) && (ordonneePlayer-rayPlayer)>((int)(placeOfTheBox[3]/2)+placeOfTheBox[2])){
            playerInTheBox=true;
        }
        return playerInTheBox;
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
