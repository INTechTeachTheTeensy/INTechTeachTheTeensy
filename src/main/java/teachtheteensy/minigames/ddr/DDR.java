package teachtheteensy.minigames.ddr;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.minigames.Minigame;

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
    private final Image background = Assets.getImage("ddr/Background.png");
    private final Image barreLp = Assets.getImage("ddr/barreLp.png");
    private final Image cadreBarreLp = Assets.getImage("ddr/cadreBarreLp.png");

    // couleurs
    private Color dimRed = new Color(1,0.0,0.0,0.5);
    private Color dimGreen = new Color(0,1, 0, 0.5);
    private Color dimBlue = new Color(0,0, 1, 0.5);
    private Color dimYellow = new Color(1,1, 0, 0.5);

    // notes
    //Note noteLeft=new Note(Game.getInstance().getScreenWidth()*2/3, 0);
    private final List<Note> allNotes=new ArrayList<>();



    // compteur de points
    public int count=0;
    private int status=0;
    private int lp=Game.getInstance().getScreenWidth()-60;
    private boolean ouch=false; // =true si on doit perdre un pv

    private static final Effect GLOW;
    static {
        Glow glow = new Glow();
        glow.setLevel(0.5);
        GLOW = glow;
    }

    @Override
    public void tick() {
        // timer pour delay l'arriver des notes
        if (tick==35){
            int randInt= (int)(Math.random()*4);
            Note newNote=new Note(((Game.getInstance().getScreenWidth() * 2) / 3) +(110*randInt), 0);
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
            if (lp < Game.getInstance().getScreenWidth()-50) {
                lp+=2;
            }
            tick=0;
            return;
        }
        for (int i=1; i<=allNotes.size();i++){
            if (allNotes.get(i-1).y>Game.getInstance().getScreenHeight()) {
                allNotes.remove(i-1);
                ouch=true;
            } else {
                allNotes.get(i-1).y+=10;     // vitesse chute notes
            }
        }

        // enlever des pvs
        if (ouch) {
            lp-=100;
            ouch=false;
        }


        // fin de jeu

        tick++;

    }


    @Override
    public void render(GraphicsContext ctx) {
            // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        //ctx.setFill(Color.BLACK);
        //ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        ctx.drawImage(background, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            // affichage score
        //ctx.setFill(Color.LIGHTGRAY);
        //ctx.fillRect(75, 50, 350, 75);
        //ctx.setFont(new Font(50));      // nouvelle police avec la police par défaut en augmentant la taille
        //ctx.setFill(Color.BLACK);
        //ctx.fillText("Score: "+count, 100, 100);


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
            // rotation de l'image via ImageView
        SnapshotParameters para=new SnapshotParameters();
        para.setFill(Color.TRANSPARENT);

        ImageView ivArrow=new ImageView(leftArrow);

        ivArrow.setRotate(90);
        Image upArrow=ivArrow.snapshot(para, null);
        ctx.drawImage(upArrow, Game.getInstance().getScreenWidth()*2/3+110, Game.getInstance().getScreenHeight()-150, 100,100);

        ivArrow.setRotate(270);
        Image downArrow=ivArrow.snapshot(para, null);
        ctx.drawImage(downArrow, Game.getInstance().getScreenWidth()*2/3+220, Game.getInstance().getScreenHeight()-150, 100,100);

        ivArrow.setRotate(180);
        Image rightArrow=ivArrow.snapshot(para, null);
        ctx.drawImage(rightArrow, Game.getInstance().getScreenWidth()*2/3+330, Game.getInstance().getScreenHeight()-150, 100,100);

            // affichage barre de vie
        ctx.drawImage(barreLp, 50, 25, Game.getInstance().getScreenWidth()-100, 50);
        ctx.setFill(Color.RED);
        ctx.fillRect(lp, 30, Game.getInstance().getScreenWidth()-60-lp, 40);
        ctx.drawImage(cadreBarreLp, 45, 25, Game.getInstance().getScreenWidth()-90, 50);

            // affichage validation
        if (status==1) {
            ctx.setFill(dimBlue);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            status=0;
        }
    }




    public void keyPressed (KeyEvent event) {
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
                    count--;
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
                    count--;
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
                    count--;
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
                    count--;
                }
                break;
        }
    }

}
