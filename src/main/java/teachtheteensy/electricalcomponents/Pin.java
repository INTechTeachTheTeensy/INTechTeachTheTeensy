package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import teachtheteensy.Game;
import teachtheteensy.Renderable;

import java.util.LinkedList;
import java.util.List;

/**
 * Représente une pin d'un composant (position, nom et connections)
 */
public class Pin implements Renderable {

    public final static int PIN_RADIUS = 10;

    /**
     * Nom de la pin
     */
    private final String name;

    /**
     * Indice de la pin (utilisé pour la programmation des composants)
     */
    private final int index;

    /**
     * Position relative sur l'axe X de la pin par rapport au coin gauche du composant
     */
    private final double relativeX;

    /**
     * Position relative sur l'axe Y de la pin par rapport au coin gauche du composant
     */
    private final double relativeY;

    /**
     * Liste de toutes les pins connectées à celle-ci
     */
    private final List<Pin> connections = new LinkedList<>();

    /**
     * Le composant de cette pin
     */
    private final ElectricalComponent owner;

    /**
     * Crées une nouvelle pin
     * @param owner
     * @param name
     * @param index
     * @param relativeX
     * @param relativeY
     */
    public Pin(ElectricalComponent owner, String name, int index, double relativeX, double relativeY) {
        this.owner = owner;
        this.name = name;
        this.index = index;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    @Override
    public void render(GraphicsContext ctx) {
        // on dessine une ligne par connection
        // comme chaque pin fait le dessin, les lignes sont dessinées deux fois...
        ctx.setLineWidth(5);
        ctx.setStroke(Color.GREENYELLOW);
        for(Pin connection : connections) {
            ctx.strokeLine(getAbsoluteX(), getAbsoluteY(), connection.getAbsoluteX(), connection.getAbsoluteY());
        }
        ctx.setStroke(Color.BLACK);
    }

    /**
     * Création d'une connection entre deux pins.
     * Vérifie que les deux pins ne proviennent pas du même composant et qu'elles ne sont pas les mêmes
     * @param other l'autre pin
     */
    public void connectTo(Pin other) {
        if(other.owner == owner) {
            throw new IllegalArgumentException("Impossible de connecter deux pins d'un même composant!");
        }

        if(other == this) {
            throw new IllegalArgumentException("Impossible de connecter un pin à lui même!");
        }

        connections.add(other);
        other.connections.add(this);
    }

    public List<Pin> getConnections() {
        return connections;
    }

    public ElectricalComponent getOwner() {
        return owner;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getRelativeX() {
        return relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public double getAbsoluteX() {
        return getRelativeX() + owner.box.getX();
    }

    public double getAbsoluteY() {
        return getRelativeY() + owner.box.getY();
    }

    /**
     * La souris est-elle sur cette pin?
     * @see #PIN_RADIUS
     * @return 'true' si la souris est présente sur la pin
     */
    public boolean isMouseOn() {
        double dx = getAbsoluteX() - Game.getInstance().getMouseX();
        double dy = getAbsoluteY() - Game.getInstance().getMouseY();
        return dx*dx+dy*dy <= PIN_RADIUS*PIN_RADIUS;
    }

    /**
     * Vérifies que cette pin a au moins une connection
     * @return 'true' si cette pin a au moins une connection
     */
    public boolean hasAtLeastOneConnection() {
        return ! connections.isEmpty();
    }

    /**
     * Renvoies un String qui va identifier cette pin de manière unique pour JSpice.
     * Si plusieurs pins ont le même nom dans un même composant, elles seront considérées comme connectées (ce qui est voulu, notamment pour GND)
     * @return un String qui identifie cette pin en tant que noeud pour JSpice
     */
    public String toNodeName() {
        return owner.toString()+"("+owner.hashCode()+")."+name;
    }

    /**
     * Force la {@link teachtheteensy.electricalcomponents.simulation.NodeMap NodeMap} à utiliser le nom de noeud de cette pin (quitte à remplacer ceux déjà utilisés)
     * Permets d'avoir des noms de noeuds qui ne dépendent pas de l'ordre d'insertion des composants sur la zone de jeu
     * @return 'true' si cette pin force le nom du noeud qui lui est associé
     */
    public boolean forcesNodeName() {
        return false;
    }
}
