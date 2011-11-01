package adp2.implementations;

// Factory class
public final class BinaryImagez {
	private BinaryImagez() {		
	}
	
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x,y);
	}
}
