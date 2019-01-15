package teachtheteensy.math;

public class FixedRectangle implements IRectangle {

    private double x;
    private double y;
    private double width;
    private double height;

    public FixedRectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
