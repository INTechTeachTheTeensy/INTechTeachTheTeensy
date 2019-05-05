package teachtheteensy.math;

/**
 * Représentes un rectangle.
 */
public interface IRectangle {

    /**
     * Est-ce que le point est dans ce rectangle?
     * @param px position X du point
     * @param py position Y du point
     * @return 'true' si le point est dans le rectangle
     */
    default boolean isPointIn(double px, double py) {
        if(px < getX()) return false; // trop à gauche
        if(px >= getX() + getWidth()) return false; // trop à droite
        if(py < getY()) return false; // trop en haut
        return !(py >= getY() + getHeight());// trop en bas?
    }

    /**
     * Test d'intersection de deux rectangles
     * @param other l'autre rectangle
     * @return 'true' si les deux rectangles ont une intersection non nulle
     */
    default boolean intersects(IRectangle other) {
        if(getX() + getWidth() < other.getX()) return false; // trop à gauche
        if(getX() >= other.getX() + other.getWidth()) return false; // trop à droite
        if(getY() + getHeight() < other.getY()) return false; // trop en haut
        return !(getY() >= other.getY() + other.getHeight()); // trop en bas?
    }

    double getX();
    double getY();
    double getWidth();
    double getHeight();

}
