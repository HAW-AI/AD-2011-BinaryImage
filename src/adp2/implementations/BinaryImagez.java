package adp2.implementations;

// Factory class
public final class BinaryImagez {
	private BinaryImagez() {		
	}
	
	public static Pixel pixel(int x, int y) {
		return PixelImpl.valueOf(x,y);
	}
}
