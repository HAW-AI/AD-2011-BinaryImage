package adp2.interfaces;

import java.util.List;
import java.util.Set;

public interface BinaryImage {

    /**
     * The number of blobs in the image.
     */
    int blobCount();

    /**
     * The nth Blob in the image.
     * 
     * @return the nth Blob if 0 <= n < blobCount() - otherwise NaB (NotABlob)
     */
    Blob blob(int i);

    /**
     * The blobs in the image, depending on the neighbor-interpretation.
     * 
     * @see toFourNeighborBinaryImage()
     * @see toEigthNeighborBinaryImage()
     */
    List<Blob> blobs();

    /**
     * The width of the image.
     */
    int width();

    /**
     * The height of the image.
     */
    int height();

    /** 
     * Check if it's an eightNeighborBinaryImage
     */
    boolean isEightNbr();

    /**
     * The neighbours with the same color as a given point. The number of
     * neighbours won't exceed four or eight (depending on the interpretation).
     * 
     * @see toFourNeighborBinaryImage()
     * @see toEigthNeighborBinaryImage()
     */
    Set<Point> neighbours(Point point);

    /**
     * Color for a given point in the image.
     * 
     * @return true if the point is in the foreground - otherwise false
     */
    boolean valueAt(Point point);

    /**
     * Check if two points belong to the same blob.
     */
    boolean connected(Point point1, Point point2);

    /**
     * Swap foreground and background.
     * 
     * @return BinaryImage with foreground and background swapped.
     */
    BinaryImage inverse();

    /**
     * @return BinaryImage with blobs from four-neighbor representation
     */
    BinaryImage toFourNeighborBinaryImage();

    /**
     * @return BinaryImage with blobs from eight-neighbor representation
     */
    BinaryImage toEigthNeighborBinaryImage();

    String circularities();

    int noOfInnerEdges(Point p);
}
