package adp2.interfaces;

public interface Blob extends Iterable<Point> {
	int pointCount();

	int width();

	int height();
}
