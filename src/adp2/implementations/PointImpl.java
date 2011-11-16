package adp2.implementations;

import adp2.interfaces.Point;

public class PointImpl extends AbstractPoint implements Point {

    protected PointImpl(int x, int y) {
        super(x, y);
    }

    public static PointImpl valueOf(int x, int y) {
        return new PointImpl(x, y);
    }
}
