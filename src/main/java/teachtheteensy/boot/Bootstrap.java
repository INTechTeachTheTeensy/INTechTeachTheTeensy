package teachtheteensy.boot;

import com.sun.javafx.application.LauncherImpl;

/**
 * Classe de bootstrap du jeu: lance l'appli JavaFX correspondante
 */
public class Bootstrap {

    public static void main(String[] args) {
        LauncherImpl.launchApplication(GameApp.class, LoadingWindow.class, args);
    }
}
