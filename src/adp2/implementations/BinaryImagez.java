package adp2.implementations;

import java.util.Collection;

import adp2.interfaces.Point;
import adp2.interfaces.Blob;

// Factory class
public final class BinaryImagez {
	private BinaryImagez() {		
	}
	
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x,y);
	}

	static Blob blob(Collection<Point> points) {
		return BlobImpl.valueOf(points);
	}
}
