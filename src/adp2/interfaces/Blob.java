package adp2.interfaces;

import java.util.List;
import java.util.Set;

public interface Blob extends Iterable<Point> {
	int pointCount();

	int width();

	int height();
	
	boolean contains(Point p);
	
	Set<Point> points();
	
	BinaryImage binaryImage();
	
	Set<Point> boundary();

	List<Integer> boundary2();
	
	double circularity();
	
	double perimeter();
}
