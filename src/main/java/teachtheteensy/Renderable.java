package teachtheteensy;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {

    /**
     * Rendu de l'élément à l'écran
     * @param ctx le contexte graphique qui permet de dessiner
     */
    void render(GraphicsContext ctx);
}
