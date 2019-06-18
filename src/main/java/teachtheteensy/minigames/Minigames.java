package teachtheteensy.minigames;

import teachtheteensy.minigames.chassetaupe.ChasseTaupe;
import teachtheteensy.minigames.runningChase.RunningChase;

public enum Minigames {

    SNAKE(Snake.class, "snake"),// mini-jeu example
    RUNNINGCHASE(RunningChase.class, "running chase"),
    DDR(teachtheteensy.minigames.ddr.DDR.class, "ddr"), // test copy Snake
    CHASSETAUPE(ChasseTaupe.class, "chasse taupe"),
    ;

    private Class<? extends Minigame> minigameClass;
    private String imgPath;

    Minigames(Class<? extends Minigame> minigameClass, String imgPath) {
        this.minigameClass = minigameClass;
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Class<? extends Minigame> getMinigameClass() {
        return minigameClass;
    }

    public Minigame createInstance() throws IllegalAccessException, InstantiationException {
        return minigameClass.newInstance();
    }
}
