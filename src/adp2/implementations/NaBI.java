package adp2.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

final class NaBI extends NaM implements BinaryImage {
	
	private final static NaBI instance = new NaBI();
	
	private NaBI() { super();}
	
	/**
	 * 
	 * @return the instance of NaBI 
	 */
	public static BinaryImage valueOf() {
		return instance;
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

	public String toString() {
		return "NaBI";
	}

	
	/**
	 * gibt String "NaBI has no circularity!" fï¿½r NaBIs zurï¿½ck
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

	@Override
	public boolean isEightNbr() {
		return false;
	}

	@Override
	public BinaryImage toFourNeighborBinaryImage() {
		return this;
	}

	@Override
	public BinaryImage toEigthNeighborBinaryImage() {
		return this;
	}

	/**
	* Berechnet die Anzahl der Nicht-Randkanten eines Pixels in einem Bild und gibt diese zurück
	*
	* @author Stephan Berngruber
	* @author Tobias Meurer
	*
	* @return Anzahl der Nicht-Randkanten eines Pixels in einem Bild. Hier Wert 0, da NaBI
	*/
	@Override
	public int noOfInnerEdges(Point point) {
		return 0;
	}

	@Override
	public BinaryImage deleteBlob(int blobId) {
		return instance;
	}
}
