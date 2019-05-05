package teachtheteensy.screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.Screen;
import teachtheteensy.math.IRectangle;
import teachtheteensy.math.MutableRectangle;
import teachtheteensy.minigames.Minigame;
import teachtheteensy.minigames.Minigames;
import teachtheteensy.screens.elements.Button;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen extends Screen {

    private final MutableRectangle redBox;
    private final List<Button> minigameButtons;

    public TitleScreen() {
        redBox = new MutableRectangle(396, 428, 1530-396, 854-428);
        minigameButtons = new ArrayList<>();

        // positionnement des boutons (adaptation automatique au nombre de mini-jeux)
        int count = Minigames.values().length;
        double spacing = 10.0;
        double buttonWidth = 300.0;

        double totalWidth = spacing*(count-1) + count * buttonWidth;
        double x = Game.getInstance().getScreenWidth()/2.0 - totalWidth/2;
        for(Minigames minigame : Minigames.values()) {
            minigameButtons.add(new Button(minigame.name(), new MutableRectangle(x, 900, buttonWidth, 100)));
            x += buttonWidth + spacing;
        }
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.drawImage(Assets.getImage("screens/title.png"), 0, 0);
        ctx.setFill(Color.AQUAMARINE);
        for(Button button : minigameButtons) {
            button.render(ctx);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void leftClick(double sceneX, double sceneY) {
        if(redBox.isPointIn(sceneX, sceneY)) {
            Game.getInstance().showScreen(new PrototypeScreen());
        }

        for(Button button : minigameButtons) {
            if(button.leftClick(sceneX, sceneY)) {
                String name = button.getText();
                try {
                    Minigame minigame = Minigames.valueOf(name).createInstance();
                    Game.getInstance().showScreen(minigame);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                break; // ne vérifie pas les boutons d'après
            }
        }
    }

    @Override
    public void rightClick(double sceneX, double sceneY) {
        if(redBox.isPointIn(sceneX, sceneY)) {
            System.out.println("Clic droit!");
        }
    }
}
