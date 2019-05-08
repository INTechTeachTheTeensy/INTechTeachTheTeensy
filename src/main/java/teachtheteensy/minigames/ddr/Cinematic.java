package teachtheteensy.minigames.ddr;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Cinematic {
    private final List<Image> backgrounds;
    private String text;

    public Cinematic (String text, Image... images) {
        this.text=text;
        backgrounds = new ArrayList<>();
        for(Image image:images) {
            backgrounds.add(image);
        }
    }

}
