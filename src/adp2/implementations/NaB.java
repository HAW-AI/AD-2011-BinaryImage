package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;

import adp2.interfaces.Blob;
import adp2.interfaces.Point;

final class NaB implements Blob {
	
	private final static NaB instance = new NaB();
	
	private NaB() {}
	
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

}
