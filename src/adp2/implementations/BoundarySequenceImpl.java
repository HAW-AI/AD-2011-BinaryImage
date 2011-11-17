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
     * @author Philipp Gill�
     */
    @Override
    public String toString() {
    	String result = "";
        result += getStartPoint().x();
        result += "|";
        result += getStartPoint().y();
        result += "(";
        if (!getSequence().isEmpty()) {
            for (int i : getSequence()) {
                result += i + ",";
            }
            result = result.substring(0, result.length() -1 );
        }
        result += ")";
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
    
    /**
     * Prueft die Wertgleichheit der BoundarySequence mit einem anderen Objekt.
     * 
     * param obj: Zu vergleichendes Objekt. return: Gibt true bei Wertgleichheit
     * zurueck, ansonsten false.
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BoundarySequenceImpl other = (BoundarySequenceImpl) obj;
        if (sequence == null) {
            if (other.getSequence() != null) {
                return false;
            }
        } else if (!sequence.equals(other.getSequence())) {
            return false;
        } else if (!point.equals(other.getStartPoint())){
        	return false;
        }
        return true;
    }

    /**
     * Factory Methode von BoundarySequence. Erstellt ein BoundarySequence Objekt und gibt ihn zurueck.
     * 
     * @param startpoint
     *            : Point mit x und y Wert,
     *        elementliste
     *            : Liste mit der Sequence
     *            
     * @return BoundarySequence
     */
    public static BoundarySequence valueOf(Point start, List<Integer> elemList) {
        return new BoundarySequenceImpl(start, elemList);
    }
    
}
