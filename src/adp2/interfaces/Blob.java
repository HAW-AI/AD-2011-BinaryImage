package adp2.interfaces;

public interface Blob extends Iterable<Point> {
	int pointCount();

	int width();

	int height();
	
	boolean contains(Point p);
	
	Set<Point> points();
}
