package adp2.implementations;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Point;

public class BoundarySequenceImpl implements BoundarySequence {

    List<Integer> sequence;
    Point point;

    private BoundarySequenceImpl(Point point, List<Integer> list) {
        this.sequence = list;
        this.point = point;
    }

    @Override
    public Point getStartPoint() {
        return point;
    }

    @Override
    public List<Integer> getSequence() {
        return sequence;
    }

    /**
     * @author Benjamin Kahlau
     */
    @Override
    public String toString() {
        String result = this.point.toString();
        for (Integer elem : this.sequence) {
            result = result + elem.toString();
        }
        return result;
    }

    /**
     * Creates a new (outline of a) blob, 
     * whose boundary matches the boundary sequence
     * 
     * @return Blob
     * 
     * @author Sebastian Bartels
     */
    @Override
    public Blob createBlob() {
//    	System.out.println("BS: "+this);
    	
        Set<Point> blobPoints = new TreeSet<Point>();
        blobPoints.add(point);
        Point prevPoint = point;

        for (int e : sequence) {
            switch (e) {
                case 0:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y());
                    blobPoints.add(prevPoint);
                    break;
                case 1:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y() - 1);
                    blobPoints.add(prevPoint);
                    break;
                case 2:
                    prevPoint = PointImpl.valueOf(prevPoint.x(), prevPoint.y() - 1);
                    blobPoints.add(prevPoint);
                    break;
                case 3:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y() - 1);
                    blobPoints.add(prevPoint);
                    break;
                case 4:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y());
                    blobPoints.add(prevPoint);
                    break;
                case 5:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y() + 1);
                    blobPoints.add(prevPoint);
                    break;
                case 6:
                    prevPoint = PointImpl.valueOf(prevPoint.x(), prevPoint.y() + 1);
                    blobPoints.add(prevPoint);
                    break;
                case 7:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y() + 1);
                    blobPoints.add(prevPoint);
                    break;
            }
        }

        /*
         * TODO Missing method to fill a blobs boundary
         */

        return BlobImpl.valueOf(blobPoints, BinaryImages.NaBI());
    }

    public static BoundarySequence valueOf(Point start, List<Integer> elemList) {
        return new BoundarySequenceImpl(start, elemList);
    }
    
}
