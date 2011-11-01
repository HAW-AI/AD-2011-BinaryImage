package adp2.implementations;

// Factory class
public final class BinaryImages {
	private BinaryImages() {		
	}
	
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x,y);
	}
}
