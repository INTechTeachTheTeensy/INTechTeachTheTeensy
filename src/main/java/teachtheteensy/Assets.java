package teachtheteensy;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import teachtheteensy.music.MusicHandle;

import java.util.HashMap;
import java.util.Map;

public final class Assets {

    /**
     * contient les images déjà chargées
     */
    private static Map<String, Image> loadedImages = new HashMap<>();

    /**
     * contient les polices déjà chargées
     */
    private static Map<Double, Font> loadedSizes = new HashMap<>();

    /**
     * contient les musiques déjà chargées
     */
    private static Map<String, MusicHandle> loadedMusics = new HashMap<>();

    /**
     * Renvoie l'image dans /assets/ correspondant au nom donné. Cette méthode fait aussi en sorte de ne pas charger deux fois la même image
     * @param name le nom du fichier de l'image
     * @return l'image correspondante
     */
    public static Image getImage(String name) {
        if( ! loadedImages.containsKey(name)) {
            Image img = new Image("/"+name);
            loadedImages.put(name, img);
        }
        return loadedImages.get(name);
    }

    public static Font getConsolasFont(double size) {
        if( ! loadedSizes.containsKey(size)) {
            Font font = Font.loadFont(Assets.class.getResourceAsStream("/consolas.ttf"), size);
            loadedSizes.put(size, font);
            return font;
        }
        return loadedSizes.get(size);
    }

    public static MusicHandle getMusic(String name) {
        return getMusic(name, "mp3");
    }

    public static MusicHandle getMusic(String name, String extension) {
        if( ! loadedMusics.containsKey(name)) {
            MusicHandle handle = new MusicHandle(name, extension);
            loadedMusics.put(name, handle);
            return handle;
        }
        return loadedMusics.get(name);
    }
}
