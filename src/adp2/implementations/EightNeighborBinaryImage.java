package adp2.implementations;

import java.util.List;
import java.util.Set;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;

public class EightNeighborBinaryImage extends AbstractBinaryImage {

    public static BinaryImage valueOf(List<List<Boolean>> shape) {
        return new EightNeighborBinaryImage(shape);
    }
    
    private EightNeighborBinaryImage(List<List<Boolean>> shape) {
        super(shape);
    }

	@Override
	protected Set<Point> neighbours(Point point, Set<Point> points) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
