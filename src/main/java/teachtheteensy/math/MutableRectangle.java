package teachtheteensy.math;

/**
 * Représentes un rectangle mutable, ie on peut changer ses dimensions et sa position
 */
public class MutableRectangle implements IRectangle {

    private double x;
    private double y;
    private double width;
    private double height;

    public MutableRectangle(double x, double y, double width, double height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
