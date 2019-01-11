package teachtheteensy.math;

/**
 * Représentes un rectangle mutable, ie on peut changer ses dimensions et sa position
 */
public class MutableRectangle {

    public double x;
    public double y;
    public double width;
    public double height;

    public MutableRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Est-ce que le point est dans ce rectangle?
     * @param px position X du point
     * @param py position Y du point
     * @return 'true' si le point est dans le rectangle
     */
    public boolean isPointIn(double px, double py) {
        if(px < x) return false; // trop à gauche
        if(px >= x+width) return false; // trop à droite
        if(py < y) return false; // trop en haut
        return !(py >= y + height);// trop en bas?
    }

    /**
     * Test d'intersection de deux rectangles
     * @param other l'autre rectangle
     * @return 'true' si les deux rectangles ont une intersection non nulle
     */
    public boolean intersects(MutableRectangle other) {
        if(x+width < other.x) return false; // trop à gauche
        if(x >= other.x+other.width) return false; // trop à droite
        if(y+height < other.y) return false; // trop en haut
        return !(y >= other.y + other.height); // trop en bas?
    }
}
