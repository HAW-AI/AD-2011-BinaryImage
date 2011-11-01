package adp2.implementations;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;

// Factory class
public final class BinaryImages {
	private BinaryImages() {		
	}
	
	/**
	 * create a Point
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return point at (x, y)
	 */
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x,y);
	}
	
	/**
	 * create an invalid BinaryImage
	 * 
	 * @return Not a BinaryImage
	 */
	public static BinaryImage NaBI() {
	    return NaBI.valueOf();
	}
	
	/**
	 * create an invalid Blob
	 * 
	 * @return Not a Blob
	 */
	public static Blob NaB() {
	    return NaB.valueOf();
	}
}
