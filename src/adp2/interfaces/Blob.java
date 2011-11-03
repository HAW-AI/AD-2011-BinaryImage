package adp2.interfaces;

import java.util.Set;

public interface Blob extends Iterable<Point> {
	int pointCount();

	int width();

	int height();
	
	boolean contains(Point p);
	
	Set<Point> points();
}
