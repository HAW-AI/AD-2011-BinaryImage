package adp2.implementations;

import java.util.List;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;

public class FourNeighborBinaryImage extends AbstractBinaryImage {
    
    public static BinaryImage valueOf(List<List<Boolean>> shape) {
        return new FourNeighborBinaryImage(shape);
    }
    
    private FourNeighborBinaryImage(List<List<Boolean>> shape) {
        super(shape);
    }

	@Override
	protected boolean areNeighbours(Point p1, Point p2) {
		return areNeighbours4n(p1,p2);
	}


}
