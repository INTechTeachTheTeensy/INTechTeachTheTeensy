package teachtheteensy.minigames;

import teachtheteensy.minigames.runningChase.RunningChase;

public enum Minigames {

    SNAKE(Snake.class),// mini-jeu example
    RUNNINGCHASE(RunningChase.class),
    DDR(teachtheteensy.minigames.ddr.DDR.class), // test copy Snake
    DEFONCEUSE(teachtheteensy.minigames.defonceuse.Defonceuse.class),
    ;

    private Class<? extends Minigame> minigameClass;

    Minigames(Class<? extends Minigame> minigameClass) {
        this.minigameClass = minigameClass;
    }

    public Class<? extends Minigame> getMinigameClass() {
        return minigameClass;
    }

    public Minigame createInstance() throws IllegalAccessException, InstantiationException {
        return minigameClass.newInstance();
    }
}
