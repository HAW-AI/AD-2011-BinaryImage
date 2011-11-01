package adp2.implementations;

import java.util.List;

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
	
	public static BinaryImage fourNeighborBinaryImage(List<List<Boolean>> shape) {
	    BinaryImage img;
	    
	    if (isValidBinaryImageShape(shape)) {
	        img = FourNeighborBinaryImage.valueOf(shape);
	    } else {
	        img = NaBI();
	    }
	    
	    return img;
	}
	
	public static BinaryImage eightNeighborBinaryImage(List<List<Boolean>> shape) {
        BinaryImage img;
        
        if (isValidBinaryImageShape(shape)) {
            img = EightNeighborBinaryImage.valueOf(shape);
        } else {
            img = NaBI();
        }
        
        return img;
	}
	
	private static boolean isValidBinaryImageShape(List<List<Boolean>> shape) {
	    if (shape == null) return false;
	    
	    if (shape.size() == 0) return true;
	    if (shape.get(0) == null) return false;
	    
	    int width = shape.get(0).size();
	    
	    for (List<Boolean> row : shape) {
	        if (row == null || row.size() != width) return false;
	    }
	    
	    return true;
	}
}
