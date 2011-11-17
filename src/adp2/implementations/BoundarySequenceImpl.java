package adp2.implementations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
     * @return String that looks like "startPoint.x|startPoint.y(direction,direction,...)"
     * ex.: a square could be 4|4(0,0,0,0,0,0,0,6,6,6,6,6,4,4,4,4,4,4,4,2,2,2,2)
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
//      Set<Point> blobPoints = new TreeSet<Point>();
        List<Point> blobPoints = new ArrayList<Point>();

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
        
        BufferedImage bi = new BufferedImage(128,128,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 128, 128);
        
        GeneralPath star = new GeneralPath();
        star.moveTo(blobPoints.get(0).x(), blobPoints.get(0).y());
        for (int k=1; k < blobPoints.size(); k++){
            star.lineTo(blobPoints.get(k).x(), blobPoints.get(k).y());
        }
        star.lineTo(blobPoints.get(0).x(), blobPoints.get(0).y());
        star.closePath();
        
        g.setColor(Color.BLACK);
        g.fill(star);
        
        List<Point> blobPoints2 = new ArrayList<Point>(blobPoints);
        
        int black = Color.BLACK.getRGB();
        for(int y=0; y<128; ++y){
        	for(int x=0; x<128; ++x){
        		if(bi.getRGB(x, y) == black) blobPoints2.add(PointImpl.valueOf(x, y));
        	}
        }

        return BlobImpl.valueOf(blobPoints2, BinaryImages.NaBI());
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
