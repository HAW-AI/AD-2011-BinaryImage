package adp2.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

final class NaBI implements BinaryImage {
	
	private final static NaBI instance = new NaBI();
	
	private NaBI() {}
	
	/**
	 * 
	 * @return the instance of NaBI 
	 */
	public static BinaryImage valueOf() {
		return instance;
	}
	
	/**
	 * 
	 * @return an iterator of an empty collection of Blob
	 */
	@Override
	public Iterator<Blob> iterator() {
		return (new ArrayList<Blob>()).iterator();
	}

	/**
	 * 
	 * @return number of Blobs
	 */
	@Override
	public int blobCount() {
		return 0;
	}

	/**
	 * 
	 * @return NaB
	 */
	@Override
	public Blob blob(int i) {
		return NaB.valueOf();
	}

	/**
	 * 
	 * @return an empty collection of Blobs
	 */
	@Override
	public List<Blob> blobs() {
		return new ArrayList<Blob>();
	}

	/**
	 * 
	 * @return width of BinaryImage 
	 */
	@Override
	public int width() {
		return 0;
	}

	/**
	 * 
	 * @return height of BinaryImage
	 */
	@Override
	public int height() {
		return 0;
	}

	/**
	 * 
	 * @return a empty set of neighbours 
	 */
	@Override
	public Set<Point> neighbours(Point point) {
		return new HashSet<Point>();
	}

	/**
	 * 
	 * @return false 
	 */
	@Override
	public boolean valueAt(Point point) {
		return false;
	}

	/**
	 * 
	 * @return false 
	 */
	@Override
	public boolean connected(Point point1, Point point2) {
		return false;
	}

	/**
	 * 
	 * @return NaBI
	 */
	@Override
	public BinaryImage inverse() {
		return this;
	}

	@Override
	public BinaryImage toFourNeighborBinaryImage() {
		return this;
	}

	@Override
	public BinaryImage toEigthNeighborBinaryImage() {
		return this;
	}
	
	public String toString() {
		return "NaBI";
	}

	
	/**
	 * gibt String "NaBI has no circularity!" für NaBIs zurück
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return String "NaBI has no circularity!"
	 */
	@Override
	public String circularities() {
		return "NaBI has no circularity!";
	}
}
