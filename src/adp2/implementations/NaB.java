package adp2.implementations;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Point;

final class NaB implements Blob {

    private final static NaB instance = new NaB();

    private NaB() {
    }

    /**
     * 
     * @return the instance of NaB
     */
    public static Blob valueOf() {
        return instance;
    }

    /**
     * 
     * @return an iterator of an empty collection of Point
     */
    @Override
    public Iterator<Point> iterator() {
        return (new ArrayList<Point>()).iterator();
    }

    /**
     * 
     * @return number of Points
     */
    @Override
    public int pointCount() {
        return 0;
    }

    /**
     * 
     * @return width of the Blob
     */
    @Override
    public int width() {
        return 0;
    }

    /**
     * 
     * @return height of Blob
     */
    @Override
    public int height() {
        return 0;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    @Override
    public Set<Point> points() {
        return new HashSet<Point>();
    }

    @Override
    public BinaryImage binaryImage() {
        return BinaryImages.NaBI();
    }

    @Override
    public String toString() {
        return "NaB";
    }

    @Override
    public Set<Point> boundary() {
        return new TreeSet<Point>();
    }

    @Override
    public BoundarySequence boundary2() {
        return new NaS();
    }

    /**
     * returnt -1, gueltiger circularity-Wert liegt zwischen 0 und
     * 1, NaB hat keine gueltige circularity.
     * 
     * @author Tobias Meurer
     * @author Stephan Berngruber
     * 
     * @return -1
     * 
     */
    @Override
    public double circularity() {
        return -1;
    }

    /**
     * Perimeter von NaB ungueltig, daher -1.
     * 
     * @author aav511
     * @return -1
     */
    @Override
    public double perimeter() {
        return -1;
    }

	@Override
	public Rectangle boundingBox() {
		return new Rectangle();
	}
}
