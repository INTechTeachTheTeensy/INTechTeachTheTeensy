package teachtheteensy.math;

public class OffsetRectangle implements IRectangle {

    private final IRectangle base;
    private final double xOffset;
    private final double yOffset;
    private final double width;
    private final double height;

    public OffsetRectangle(IRectangle base, double xOffset, double yOffset, double width, double height) {
        this.base = base;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getX() {
        return base.getX()+xOffset;
    }

    @Override
    public double getY() {
        return base.getY()+yOffset;
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
