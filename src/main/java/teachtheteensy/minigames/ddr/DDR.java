package teachtheteensy.minigames.ddr;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
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
    private final Image leftArrow= Assets.getImage("ddr/leftArrow.png");
    private final Image background=Assets.getImage("ddr/Background.png");

    // couleurs
    private Color dimRed = new Color(1,0.0,0.0,0.5);
    private Color dimGreen = new Color(0,1, 0, 0.5);
    private Color dimBlue = new Color(0,0, 1, 0.5);
    private Color dimYellow = new Color(1,1, 0, 0.5);

    // notes
    //Note noteLeft=new Note(Game.getInstance().getScreenWidth()*2/3, 0);
    private final List<Note> allNotes=new ArrayList<>();
    private final List<Note> leftNotes=new ArrayList<>();
    private final List<Note> upNotes=new ArrayList<>();
    private final List<Note> downNotes=new ArrayList<>();
    private final List<Note> rightNotes=new ArrayList<>();


    // compteur de points
    private int count=0;

    private int status=0;


    @Override
    public void tick() {
        // timer pour afficher règles
        //noteLeft.y+=10;
        if (tick==35){
            int randInt= (int)(Math.random()*4);
            Note newNote=new Note(((Game.getInstance().getScreenWidth() * 2) / 3) +(110*randInt), 0);
            if (randInt==0) {
                leftNotes.add(newNote);
            } else if (randInt==1) {
                upNotes.add(newNote);
            } else if (randInt==2) {
                downNotes.add(newNote);
            } else {
                rightNotes.add(newNote);
            }
            allNotes.add(newNote);
            tick=0;
            return;
        }
        for (int i=1; i<=allNotes.size();i++){
            allNotes.get(i-1).y+=4;
        }

        tick++;

    }

    @Override
    public void render(GraphicsContext ctx) {
        // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        //ctx.setFill(Color.BLACK);
        //ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        ctx.drawImage(background, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        //ctx.setFill(Color.GRAY);
        //ctx.fillRect(Game.getInstance().getScreenWidth()*2/3-10, Game.getInstance().getScreenHeight()-160, 460, 120);
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

        if (status==1) {
            ctx.setFill(dimBlue);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            status=0;
        } else if (status==2) {
            ctx.setFill(dimYellow);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            status=0;
        } else if (status==3) {
            ctx.setFill(dimGreen);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            status=0;
        } else if (status==4) {
            ctx.setFill(dimRed);
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            status=0;
        }
    }



    public void keyPressed (KeyEvent event) {
        switch(event.getCode()) {
            case LEFT:
                for (int i=1; i<=leftNotes.size(); i++) {
                    if ((Game.getInstance().getScreenHeight()-240 <= leftNotes.get(i-1).y) & (leftNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        status=1;
                    }
                }
                break;

            case UP:
                for (int i=1; i<=upNotes.size(); i++) {
                    if ((Game.getInstance().getScreenHeight()-240 <= upNotes.get(i-1).y) & (upNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        status=2;
                    }
                }
                break;

            case DOWN:
                for (int i=1; i<=downNotes.size(); i++) {
                    if ((Game.getInstance().getScreenHeight()-240 <= downNotes.get(i-1).y) & (downNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        status=3;
                    }
                }
                break;
            case RIGHT:
                for (int i=1; i<=rightNotes.size(); i++) {
                    if ((Game.getInstance().getScreenHeight()-240 <= rightNotes.get(i-1).y) & (rightNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        status=4;
                    }
                }
                break;
        }
    }

}
