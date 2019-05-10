package teachtheteensy.minigames.ddr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teachtheteensy.Assets;
import teachtheteensy.Game;

import java.util.ArrayList;
import java.util.List;

public class Cinematic{
    private final List<Image> backgrounds;
    private String text;

    public Cinematic (String text, Image... images) {
        this.text=text;
        backgrounds = new ArrayList<>();
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
        for (int i=0; i<=(cinematic.backgrounds.size()-1); i++) {
            Image currBackground = cinematic.backgrounds.get(i);
            ctx.drawImage(currBackground, 0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());
        }
    }


}
