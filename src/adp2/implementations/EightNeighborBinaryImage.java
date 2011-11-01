package adp2.implementations;

import java.util.List;

import adp2.interfaces.BinaryImage;

public class EightNeighborBinaryImage extends AbstractBinaryImage {

    public static BinaryImage valueOf(List<List<Boolean>> shape) {
        return new EightNeighborBinaryImage(shape);
    }
    
    private EightNeighborBinaryImage(List<List<Boolean>> shape) {
        super(shape);
    }

    
}
