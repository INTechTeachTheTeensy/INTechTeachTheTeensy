package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.simulate.SimulationResult;
import teachtheteensy.Renderable;
import teachtheteensy.electricalcomponents.simulation.NodeMap;
import teachtheteensy.math.MutableRectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Représente un composant électrique dans le jeu (boite de collisions, modélisation JSpice, texture, etc.)
 */
public abstract class ElectricalComponent implements Renderable, Cloneable {

    /**
     * La texture utilisée pour ce composant
     */
    private final Image texture;
    /**
     * La liste des pins de ce composant
     */
    protected final List<Pin> pins = new LinkedList<>();
    /**
     * La boîte de collision de ce composant. Sert aussi à définir sa position
     */
    public final MutableRectangle box;

    // champs utilisés pour que le composant se mette à la bonne position par rapport à la souris lors d'un déplacement
    public double xOffset;
    public double yOffset;

    /**
     * Crée un composant avec une texture donnée, qui utilise les dimensions de la texture pour donner les dimensions de la boîte de collisions
     * @param texture la texture
     */
    public ElectricalComponent(Image texture) {
        this(texture, texture.getWidth(), texture.getHeight());
    }

    /**
     * Crée un composant avec une texture donnée, une largeur et une hauteur donnée
     * @param texture la texture
     * @param width la largeur
     * @param height la hauteur
     */
    public ElectricalComponent(Image texture, double width, double height) {
        this.texture = texture;
        box = new MutableRectangle(0, 0, width, height);
    }

    /**
     * Méthode appelée une fois par tick juste avant la simulation
     */
    public void step() {}

    /**
     * Dessine le composant à l'écran
     * @param ctx le contexte graphique pour dessiner
     */
    public void render(GraphicsContext ctx) {
        // dessin de la texture
        ctx.drawImage(getTexture(), box.getX(), box.getY(), box.getWidth(), box.getHeight());

        // disque rouge + nom en noir s'il y a une pin sous la souris
        pinUnderMouse().ifPresent(pin -> {
            ctx.setFill(Color.RED);
            ctx.fillOval(box.getX() +pin.getRelativeX()-Pin.PIN_RADIUS, box.getY() +pin.getRelativeY()-Pin.PIN_RADIUS, Pin.PIN_RADIUS*2, Pin.PIN_RADIUS*2);

            ctx.setFill(Color.BLACK);
            ctx.fillText(pin.getName(), box.getX() +pin.getRelativeX(), box.getY() +pin.getRelativeY());
        });

        // dessin des pins individuelles (liens entre les pins)
        for(Pin p : pins) {
            p.render(ctx);
        }
    }

    /**
     * Renvoies la pin à l'indice donné, si elle existe
     * @param index l'indice
     * @return la pin, si elle existe
     */
    public Optional<Pin> getPin(int index) {
        if(index < 0)
            return Optional.empty();
        return pins.stream().filter(p -> p.getIndex() == index).findFirst();
    }

    /**
     * Renvoies la pin avec le nom donné, si elle existe
     * @param name le nom
     * @return la pin, si elle existe
     */
    public Optional<Pin> getPin(String name) {
        return pins.stream().filter(p -> p.getName().equals(name)).findFirst();
    }

    /**
     * Texture du composant. Appelé par {@link #render(GraphicsContext)}, ce qui permet d'avoir une texture dynamique (ex: LED allumée/éteinte)
     * @return la texture
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Actions quand on commence à cliquer le composant
     * @param x position X de la souris sur l'écran
     * @param y position Y de la souris sur l'écran
     * @return y-a-t'il eu une action?
     */
    public boolean mousePressed(double x, double y) {
        // TODO: actions quand on commence à cliquer le composant
        return false;
    }
    public Optional<Pin> pinUnderMouse() {
        return pins.stream()
                .filter(Pin::isMouseOn)
                .findFirst();
    }

    public abstract ElectricalComponent clone();

    public List<Pin> getPins() {
        return pins;
    }

    public abstract void resetComponent();

    public abstract List<? extends NetlistComponent> toNetlistComponents(NodeMap nodeMap);

    /**
     * Change l'état du composant en fonction du résultat de l'analyse (appelé à chaque tick)
     * @param result le result de l'analyse/simulation
     */
    public void interpretResult(NodeMap nodeMap, SimulationResult result) {}
}
