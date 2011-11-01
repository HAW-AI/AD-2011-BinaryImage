package adp2.implementations;

import java.util.List;
import java.util.Set;

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
	protected Set<Point> neighbours(Point point, Set<Point> points) {
		// TODO Auto-generated method stub
		return null;
	}

}
