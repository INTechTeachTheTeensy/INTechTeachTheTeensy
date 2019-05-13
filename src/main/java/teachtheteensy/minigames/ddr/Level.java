package teachtheteensy.minigames.ddr;

import teachtheteensy.Assets;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Level extends Script {
    private int num;
    private int notesSpeed;
    private int time;
    private boolean spaceBarLevel;

    public Level(int num, int speed, int time) {
        this.num = num;
        this.notesSpeed = speed;
        this.time=time;
    }

    public void getLevelIntro() {
        num=0;
        notesSpeed=10;
        time=28;
        Image background1 = Assets.getImage("ddr/Background.png");
        Image background2 = Assets.getImage("ddr/BackgroundAllo.png");
        Image background3 = Assets.getImage("ddr/BackgroundJavaS.jpg");
        backgrounds.add(background1);
        backgrounds.add(background2);
        backgrounds.add(background3);
        spaceBarLevel=false;
    }

    public void getLevel1() {
        num=1;
        notesSpeed=10;
        time=103;
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

    public int getTime() {
        return time;
    }

    public void addImage(Image image) {
        backgrounds.add(image);
    }
}
