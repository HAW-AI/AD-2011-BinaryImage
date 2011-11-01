package adp2.implementations;

import java.util.List;

import adp2.interfaces.BinaryImage;

public class FourNeighborBinaryImage extends AbstractBinaryImage {
    
    public static BinaryImage valueOf(List<List<Boolean>> shape) {
        return new FourNeighborBinaryImage(shape);
    }
    
    private FourNeighborBinaryImage(List<List<Boolean>> shape) {
        super(shape);
    }

}
