package adp2.implementations;

import java.util.List;
import java.util.Set;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;

public class EightNeighborBinaryImage extends AbstractBinaryImage {

    /**
     * Factory method to create Binary Image from point set
     * 
     * @author Oliver Behncke
     * 
     * @param Set of points (representing foreground)
     * @param width of image
     * @param height of image
     * @return Binary Image
     */	
    static BinaryImage valueOf(Set<Point> points, int width, int height) {
    	if(!properPoints(points, width, height)) return BinaryImages.NaBI();
        return new EightNeighborBinaryImage(points, width, height);
    }
    
	
    static BinaryImage valueOf(List<List<Boolean>> shape) {
        return new EightNeighborBinaryImage(shape);
    }
    
    private EightNeighborBinaryImage(List<List<Boolean>> shape) {
        super(shape);
    }
    
    private EightNeighborBinaryImage(Set<Point> points, int width, int height) {
        super(points, width, height);
    }
    

	@Override
	protected boolean areNeighbours(Point p1, Point p2) {
		return areNeighbours8n(p1,p2);
	}


	@Override
	public BinaryImage toFourNeighborBinaryImage() {
		return FourNeighborBinaryImage.valueOf(points, this.width(), this.height());
	}

	@Override
	public BinaryImage toEigthNeighborBinaryImage() {
		return this;
	}


    
}
