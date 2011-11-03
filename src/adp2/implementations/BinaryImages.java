package adp2.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import adp2.application.EsserImage;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

// Factory class
public final class BinaryImages {
	private BinaryImages() {
	}

	/**
	 * create a Point
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @return point at (x, y)
	 */
	public static Point point(int x, int y) {
		return PointImpl.valueOf(x, y);
	}
	
	public static Blob blob(Collection<Point> points) {
		return BlobImpl.valueOf(points);
	}

	/**
	 * create an invalid BinaryImage
	 * 
	 * @return Not a BinaryImage
	 */
	public static BinaryImage NaBI() {
		return NaBI.valueOf();
	}

	/**
	 * create an invalid Blob
	 * 
	 * @return Not a Blob
	 */
	public static Blob NaB() {
		return NaB.valueOf();
	}

    /**
     * Create a BinaryImage whose blobs are created from a neighborhood of
     * four neighbors.
     *
     * @param shape n x n matrix with true values for the foreground and false
     *              for the background
     * @return a valid BinaryImage for correct input shape or NaBI if shape
     *         contains null values or has rows of varying size
     */
	public static BinaryImage fourNeighborBinaryImage(List<List<Boolean>> shape) {
		BinaryImage img;

		if (isValidBinaryImageShape(shape)) {
			img = FourNeighborBinaryImage.valueOf(shape);
		} else {
			img = NaBI();
		}

		return img;
	}

    /**
     * Create a BinaryImage whose blobs are created from a neighborhood of
     * eight neighbors.
     *
     * @param shape n x m matrix with true values for the foreground and false
     *              for the background
     * @return a valid BinaryImage for correct input shape or NaBI if shape
     *         contains null values or has rows of varying size
     */
	public static BinaryImage eightNeighborBinaryImage(List<List<Boolean>> shape) {
		BinaryImage img;

		if (isValidBinaryImageShape(shape)) {
			img = EightNeighborBinaryImage.valueOf(shape);
		} else {
			img = NaBI();
		}

		return img;
	}

    /**
     * Check pre-conditions for BinaryImage shape.
     *
     * @param shape the BinaryImage shape
     * @return true if a valid BinaryImage can be created from the shape -
     *         else false
     * @see BinaryImages#fourNeighborBinaryImage(List<List<Boolean>>)
     */
	private static boolean isValidBinaryImageShape(List<List<Boolean>> shape) {
		if (shape == null)
			return false;

		if (shape.size() == 0)
			return true;
		if (shape.get(0) == null)
			return false;

		int width = shape.get(0).size();

		for (List<Boolean> row : shape) {
			if (row == null || row.size() != width)
				return false;
		}

		return true;
	}

	/**
	 * factory methods getting EsserImage and retuns BinaryImage
	 * 
	 * @param image
	 * @return
	 */
	public static BinaryImage binaryImage(EsserImage image) {
		boolean[][] array=image.pixels();
		List<List<Boolean>> list=new ArrayList<List<Boolean>>();
		for(int i=0; i<array.length; i++){
			List<Boolean> innerList=new ArrayList<Boolean>();
			for(int n=0; n<array[i].length; n++){
				innerList.add(array[i][n]);
			}
			list.add(innerList);
		}
		return fourNeighborBinaryImage(list);
	}
	
	/**
	 * Create a binary image from width, height and foreground points
	 * (4 Neighborhood)
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param width
	 * @param height
	 * @param points
	 * @return Binary Image
	 */
	public static BinaryImage binaryImage4n(int width, int height, Point...points){
		Set<Point> set=new TreeSet<Point>(Arrays.asList(points));
		return FourNeighborBinaryImage.valueOf(set,width, height);
	}

	
	/**
	 * Create a binary image from width, height and foreground points
	 * (8 Neighborhood)
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param width
	 * @param height
	 * @param points
	 * @return Binary Image
	 */
	public static BinaryImage binaryImage8n(int width, int height, Point...points){
		Set<Point> set=new TreeSet<Point>(Arrays.asList(points));
		return EightNeighborBinaryImage.valueOf(set,width, height);
	}

	/**
	 * Create a binary image from width, height and foreground points
	 * (Deafault: 4 Neighborhood)
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param width
	 * @param height
	 * @param points
	 * @return Binary Image
	 */
	public static BinaryImage binaryImage(int width, int height, Point...points){
		return binaryImage4n(width, height, points);
	}
}
