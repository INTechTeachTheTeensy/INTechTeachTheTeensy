package teachtheteensy.minigames.ddr;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
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



    // compteur de points
    private int count=0;

    private int status=0;


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
            tick=0;
            return;
        }
        for (int i=1; i<=allNotes.size();i++){
            if (allNotes.get(i-1).y>Game.getInstance().getScreenHeight()) {
                allNotes.remove(i-1);
            } else {
                allNotes.get(i-1).y+=4;     // vitesse chute notes
            }
        }

        tick++;

    }


    @Override
    public void render(GraphicsContext ctx) {
        // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        //ctx.setFill(Color.BLACK);
        //ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        ctx.drawImage(background, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        // affichage score
        ctx.setFill(Color.LIGHTGRAY);
        ctx.fillRect(75, 50, 350, 75);
        ctx.setFont(new Font(50));      // nouvelle police avec la police par défaut en augmentant la taille
        ctx.setFill(Color.BLACK);
        ctx.fillText("Score: "+count, 100, 100);



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

        // affichage validation
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
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==1) & (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) & (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=1;
                    }
                }
                if (status==0) {
                    count--;
                }
                break;

            case UP:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==2) & (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) & (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=2;
                    }
                }
                if (status==0) {
                    count--;
                }
                break;

            case DOWN:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==3) & (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) & (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=3;
                    }
                }
                if (status==0) {
                    count--;
                }
                break;

            case RIGHT:
                for (int i=1; i<=allNotes.size(); i++) {
                    if ((allNotes.get(i-1).col==4) & (Game.getInstance().getScreenHeight()-240 <= allNotes.get(i-1).y) & (allNotes.get(i-1).y <= Game.getInstance().getScreenHeight()-100)){
                        allNotes.remove(i-1);
                        count++;
                        status=4;
                    }
                }
                if (status==0) {
                    count--;
                }
                break;
        }
    }

}
