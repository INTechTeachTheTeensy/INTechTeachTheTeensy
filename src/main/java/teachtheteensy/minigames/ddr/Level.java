package teachtheteensy.minigames.ddr;

import teachtheteensy.Assets;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Level {
    private int num;
    private final List<Image> backgrounds;
    private int notesSpeed;
    private boolean spaceBarLevel;

    public Level(int num, int speed) {
        this.num = num;
        backgrounds = new ArrayList<>();
        this.notesSpeed = speed;
    }

    public void getLevel1() {
        num=1;
        notesSpeed=8;
        Image background = Assets.getImage("ddr/Backgroundlvl1.jpg");
        backgrounds.add(background);
        spaceBarLevel=false;
    }


    public int getNum() {
        return num;
    }

    public int getNotesSpeed() {
        return notesSpeed;
    }

    public List<Image> getBackgrounds() {
        return backgrounds;
    }

    public void addImage(Image image) {
        backgrounds.add(image);
    }
}
