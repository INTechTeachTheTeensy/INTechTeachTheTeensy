package teachtheteensy;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public final class Assets {

    /**
     * contient les images déjà chargées
     */
    private static Map<String, Image> loadedImages = new HashMap<>();

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
}
