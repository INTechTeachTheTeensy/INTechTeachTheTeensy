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
     * Mise à jour du jeu (donc déplacement) tous les 10 ticks (~60/10 Hz => ~6Hz)
     */
    private static final int TICKS_PER_UPDATE = 10;

    /**
     * Compteur de tick. Permet de manière assez peu propre de régler la vitesse du jeu
     */
    private int tick;

    // déclaration des images
    private final Image leftArrow= Assets.getImage("ddr/leftArrow.png");
    private final Image background=Assets.getImage("ddr/Background.png");

    Note noteLeft=new Note(Game.getInstance().getScreenWidth()*2/3, 0);
    Note noteUp=new Note(Game.getInstance().getScreenWidth()*2/3+110, 0);
    Note noteDown=new Note(Game.getInstance().getScreenWidth()*2/3+220, 0);
    Note noteRight=new Note(Game.getInstance().getScreenWidth()*2/3+330, 0);


    @Override
    public void tick() {
        noteLeft.y+=10;
        noteUp.y+=10;
        noteDown.y+=10;
        noteRight.y+=10;
    }

    @Override
    public void render(GraphicsContext ctx) {
        // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        //ctx.setFill(Color.BLACK);
        //ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        ctx.drawImage(background, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        //ctx.setFill(Color.GRAY);
        //ctx.fillRect(Game.getInstance().getScreenWidth()*2/3-10, Game.getInstance().getScreenHeight()-160, 460, 120);

        noteLeft.render(ctx);
        noteUp.render(ctx);
        noteDown.render(ctx);
        noteRight.render(ctx);


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


    }

}
