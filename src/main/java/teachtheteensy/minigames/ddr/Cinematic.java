package teachtheteensy.minigames.ddr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.Game;

import java.util.ArrayList;
import java.util.List;

public class Cinematic extends Script{
    private String text;
    private int timer;

    public Cinematic (String text, Image... images) {
        this.text=text;
        timer=25;
        for(Image image:images) {
            backgrounds.add(image);
        }
    }

    public void getCinematic1() {
        String text = "Ogni nocte, globos turnis";
        Cinematic cinematic =new Cinematic(text);
        Image background1 = Assets.getImage("ddr/Background.png");
        Image background2 = Assets.getImage("ddr/BackgroundAllo.png");
        Image background3 = Assets.getImage("ddr/BackgroundJavaS.jpg");
        backgrounds.add(background1);
        backgrounds.add(background2);
        backgrounds.add(background3);
    }

    public void lauchCinematic(GraphicsContext ctx, Cinematic cinematic) {
        int time = timer;
        int i = 0;
        if (i<(cinematic.backgrounds.size()-1)) {
            time--;
            Image currBackground = cinematic.backgrounds.get(i);
            ctx.drawImage(currBackground, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
            if (time == 0) {
                i++;
                time=timer;
            }
        }
    }


}
