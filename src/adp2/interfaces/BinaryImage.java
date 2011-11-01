package adp2.interfaces;

import java.util.List;
import java.util.Set;

public interface BinaryImage extends Iterable<Blob> {
	int blobCount();

	Blob blob(int i);

	List<Blob> blobs();

	int width();

	int height();

	Set<Point> neighbours(Point point);

	boolean valueAt(Point point);

	boolean connected(Point point1, Point point2);

	BinaryImage inverse();
}
