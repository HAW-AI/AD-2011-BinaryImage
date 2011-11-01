package adp2.implementations;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;

// Factory class
public final class BinaryImages {
	private BinaryImages() {		
	}
	
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x,y);
	}
	
	public static BinaryImage NaBI() {
	    return NaBI.valueOf();
	}
	
	public static Blob NaB() {
	    return NaB.valueOf();
	}
}
