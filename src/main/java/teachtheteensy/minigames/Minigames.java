package teachtheteensy.minigames;

public enum Minigames {
    SNAKE(Snake.class),// mini-jeu example
    RUNNINGCHASE(RunningChase.class),
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
