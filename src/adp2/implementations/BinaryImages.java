package adp2.implementations;

import java.util.Collection;
import java.util.List;

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

    public static Point NaP(int x, int y) {
        return NaP.valueOf(x, y);
    }

    public static Blob blob(Collection<Point> points, BinaryImage image) {
        return BlobImpl.valueOf(points, image);
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
        return BinaryImageImpl.valueOf(shape, false);
    }

    public static BinaryImage fourNeighborBinaryImage(int width, int height, List<Integer> values) {
        return BinaryImageImpl.valueOf(width, height, values, false);
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
        return BinaryImageImpl.valueOf(shape, true);
    }

    public static BinaryImage eightNeighborBinaryImage(int width, int height, List<Integer> values) {
        return BinaryImageImpl.valueOf(width, height, values, true);
    }

    public static BinaryImage binaryImage(int width, int height, List<Integer> values) {
        return fourNeighborBinaryImage(width, height, values);
    }

    /**
     * Create a binary image from width, height and foreground points
     * (Deafault: 4 Neighborhood)
     * 
     * @param points
     * @param width
     * @param height
     * @return Binary Image
     */
    public static BinaryImage binaryImage(List<Point> points, int width, int height) {
        return BinaryImageImpl.valueOf(points, width, height, false);
    }
}
