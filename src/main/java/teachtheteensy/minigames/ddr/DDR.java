package teachtheteensy.minigames.ddr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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

    Note note=new Note(Game.getInstance().getScreenWidth()*2/3, 0);

    @Override
    public void tick() {
        note.y++;
    }

    @Override
    public void render(GraphicsContext ctx) {
        // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());

        note.render(ctx);
    }

}
