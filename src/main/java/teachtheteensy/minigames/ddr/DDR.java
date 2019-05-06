package teachtheteensy.minigames.ddr;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.minigames.Minigame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class DDR extends Minigame {

    /**
     * Compteur de tick. Permet de manière assez peu propre de régler la vitesse du jeu
     */
    private int tick=0;

    // déclaration des images
    private final Image leftArrow = Assets.getImage("ddr/leftArrow.png");
    private final Image upArrow;
    private final Image downArrow;
    private final Image rightArrow;
    private final Image barreLp = Assets.getImage("ddr/barreLp.png");
    private final Image cadreBarreLp = Assets.getImage("ddr/cadreBarreLp.png");
    private final Image background1 = Assets.getImage("ddr/Background.png");
    private final Image background2 = Assets.getImage("ddr/BackgroundAllo.png");
    private final Image background3 = Assets.getImage("ddr/BackgroundJavaS.jpg");
    private final List<Image> backgrounds = new ArrayList<>();
    private final Image currBackground;

    // couleurs
    private Color dimRed = new Color(1,0.0,0.0,0.5);
    private Color trueRed = new Color( 1, 0, 0, 0.8);
    private Color dimGreen = new Color(0,1, 0, 0.5);
    private Color dimBlue = new Color(0,0, 1, 0.5);
    private Color dimYellow = new Color(1,1, 0, 0.5);

    // notes
    //Note noteLeft=new Note(Game.getInstance().getScreenWidth()*2/3, 0);
    private final List<Note> allNotes=new ArrayList<>();



    // compteur de points
    private int speed=10;  // vitesse initiale du jeu
    private int speed0Meter=0;
    public int count=0;
    private int status=0;
    private int lp=Game.getInstance().getScreenWidth()-60;
    private boolean ouch=false; // =true si on doit perdre un pv
    private boolean gameStatus=true;  // =false si on a perdu

    private static final Effect GLOW;
    static {
        Glow glow = new Glow();
        glow.setLevel(0.5);
        GLOW = glow;
    }


    public DDR() {
        Level level1 = new Level(0, 0);
        level1.getLevel1();
        currBackground=level1.getBackgrounds().get(0);
        speed=level1.getNotesSpeed();
        //backgrounds.add(background1);
        //backgrounds.add(background2);
        //backgrounds.add(background3);
        //this.currBackground = backgrounds.get((int)(Math.random()*2));

        // rotation de l'image via ImageView
        SnapshotParameters para=new SnapshotParameters();
        para.setFill(Color.TRANSPARENT);

        ImageView ivArrow=new ImageView(leftArrow);

        ivArrow.setRotate(90);
        this.upArrow=ivArrow.snapshot(para, null);

        ivArrow.setRotate(270);
        this.downArrow=ivArrow.snapshot(para, null);

        ivArrow.setRotate(180);
        this.rightArrow=ivArrow.snapshot(para, null);

        /**
         * tentative de son
        Thread thread = new Thread() {
            @Override
            public void run() {
                String musiqueInge = "src/main/resources/ddr/sheep.wav";
                Media curSong = new Media(new File(musiqueInge).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(curSong);
                mediaPlayer.play();
            }
        };
        thread.start();
        */


    }
    @Override
    public void tick() {
        // game over
        if (lp<=0) {
            gameStatus=false;
            return;
        }
        // timer pour delay l'arriver des notes
        if (tick==30){
            int randInt= (int)(Math.random()*4);
            Note newNote=new Note(((Game.getInstance().getScreenWidth() * 2) / 3) +(110*randInt), 0, speed);
            if (randInt==0) {
                newNote.col=1;
            } else if (randInt==1) {
                newNote.col=2;
            } else if (randInt==2) {
                newNote.col=3;
            } else {
                newNote.col=4;
            }
            allNotes.add(newNote);
            if (lp < Game.getInstance().getScreenWidth()-800) {
                lp+=20;
            }
            speed0Meter++;
            tick=0;
            return;
        }
        for (int i=1; i<=allNotes.size();i++){
            if (allNotes.get(i-1).y>Game.getInstance().getScreenHeight()) {
                allNotes.remove(i-1);
                ouch=true;
            } else {
                allNotes.get(i-1).y+=allNotes.get(i-1).v;     // vitesse chute notes
            }
        }

        // enlever des pvs
        if (ouch) {
            lp-=100;
            ouch=false;
        }

        // augmenter vitesse
        if (speed0Meter==100) {
            speed++;
            speed0Meter=0;
        }

        tick++;

    }


    @Override
    public void render(GraphicsContext ctx) {
            // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        //ctx.setFill(Color.BLACK);
        //ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        ctx.drawImage(currBackground, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());



        ctx.setFill(dimBlue);
        ctx.fillRect(Game.getInstance().getScreenWidth()*2/3-2, 0, 104, Game.getInstance().getScreenHeight());
        ctx.setFill(dimYellow);
        ctx.fillRect(Game.getInstance().getScreenWidth()*2/3+110-2, 0, 104, Game.getInstance().getScreenHeight());
        ctx.setFill(dimGreen);
        ctx.fillRect(Game.getInstance().getScreenWidth()*2/3+110*2-2, 0, 104, Game.getInstance().getScreenHeight());
        ctx.setFill(dimRed);
        ctx.fillRect(Game.getInstance().getScreenWidth()*2/3+110*3-2, 0, 104, Game.getInstance().getScreenHeight());

            // render toutes les notes
        //noteLeft.render(ctx);
        for (int i=1; i<=allNotes.size();i++){
            allNotes.get(i-1).render(ctx);
        }


            // affichage des cases flèches
        ctx.drawImage(leftArrow, Game.getInstance().getScreenWidth()*2/3, Game.getInstance().getScreenHeight()-150, 100,100);
        ctx.drawImage(upArrow, Game.getInstance().getScreenWidth()*2/3+110, Game.getInstance().getScreenHeight()-150, 100,100);
        ctx.drawImage(downArrow, Game.getInstance().getScreenWidth()*2/3+220, Game.getInstance().getScreenHeight()-150, 100,100);
        ctx.drawImage(rightArrow, Game.getInstance().getScreenWidth()*2/3+330, Game.getInstance().getScreenHeight()-150, 100,100);

            // affichage barre de vie
        ctx.drawImage(barreLp, 50, 25, Game.getInstance().getScreenWidth()-100, 50);
        ctx.setFill(Color.RED);
        ctx.fillRect(lp, 30, Game.getInstance().getScreenWidth()-60-lp, 40);
        ctx.drawImage(cadreBarreLp, 45, 25, Game.getInstance().getScreenWidth()-90, 50);

            // affichage game over
        if (!gameStatus) {
            ctx.setFill(trueRed);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            // affichage score
            ctx.setFill(Color.LIGHTGRAY);
            ctx.fillRect(75, 50, 350, 75);
            ctx.setFont(new Font(50));     // nouvelle police avec la police par défaut en augmentant la taille
            ctx.setFill(Color.BLACK);
            ctx.fillText("Score: "+count, 100, 100);
            ctx.setFont(new Font(250));     // nouvelle police avec la police par défaut en augmentant la taille
            ctx.setFill(Color.BLACK);
            ctx.fillText("GAME - OVER", 100,Game.getInstance().getScreenHeight()/2);

        }
    }




    public void keyPressed (KeyEvent event) {
        status=0;
        //FIXME: revoir taille hitbox
        switch(event.getCode()) {
            case LEFT:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==1) && (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) && (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=1;
                    }
                }
                if (status==0) {
                    ouch=true;
                }
                break;

            case UP:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==2) && (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) && (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=1;
                    }
                }
                if (status==0) {
                    ouch=true;
                }
                break;

            case DOWN:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==3) && (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) && (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=1;
                    }
                }
                if (status==0) {
                    ouch=true;
                }
                break;

            case RIGHT:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==4) && (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) && (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=1;
                    }
                }
                if (status==0) {
                    ouch=true;
                }
                break;
        }
    }

}
