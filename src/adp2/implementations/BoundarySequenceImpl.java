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

    final private List<Integer> sequence;
    final private Point point;

    private BoundarySequenceImpl(Point point, List<Integer> list) {
        this.sequence = list;
        this.point = point;
    }

    @Override
    public Point getStartPoint() {
        return point;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    /**
     * @return String that looks like "startPoint.x|startPoint.y(direction,direction,...)"
     * ex.: a square could be 4|4(0,0,0,0,0,0,0,6,6,6,6,6,4,4,4,4,4,4,4,2,2,2,2)
     * @author Benjamin Kahlau
     * @author Philipp Gille
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
        List<Point> blobPoints = new ArrayList<Point>();

        blobPoints.add(point);
        Point prevPoint = point;
        int xMin=Integer.MAX_VALUE, xMax=0, yMin=Integer.MAX_VALUE ,yMax=0;
        int x = point.x();
        int y = point.y();
        
        for (int e : sequence) {
            switch (e) {
                case RIGHT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y());
                    xMax = Math.max(xMax, ++x);
                    break;
                case TOPRIGHT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y() - 1);
                    xMax = Math.max(xMax, ++x);
                    yMin = Math.min(yMin, --y);
                    break;
                case TOP:
                    prevPoint = PointImpl.valueOf(prevPoint.x(), prevPoint.y() - 1);
                    yMin = Math.min(yMin, --y);
                    break;
                case TOPLEFT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y() - 1);
                    yMin = Math.min(yMin, --y);
                    xMin = Math.min(xMin, --x);
                    break;
                case LEFT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y());
                    xMin = Math.min(xMin, --x);
                    break;
                case BOTTOMLEFT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() - 1, prevPoint.y() + 1);
                    xMin = Math.min(xMin, --x);
                    yMax = Math.max(yMax, ++y);
                    break;
                case BOTTOM:
                    prevPoint = PointImpl.valueOf(prevPoint.x(), prevPoint.y() + 1);
                    yMax = Math.max(yMax, ++y);
                    break;
                case BOTTOMRIGHT:
                    prevPoint = PointImpl.valueOf(prevPoint.x() + 1, prevPoint.y() + 1);
                    yMax = Math.max(yMax, ++y);
                    xMax = Math.max(xMax, ++x);
                    break;
            }
            blobPoints.add(prevPoint);
        }

        /*
         * Missing method to fill a blobs boundary
         */
        
        int w = xMax-xMin;
        int h = yMax-yMin;
        
        System.out.println("BlobCreate-BufferedImage-Size(w,h): "+w+", "+h);
        
        BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        
        GeneralPath path = new GeneralPath();
        path.moveTo(blobPoints.get(0).x()-xMin, blobPoints.get(0).y()-yMin);
        for (int k=1; k < blobPoints.size(); k++){
            path.lineTo(blobPoints.get(k).x()-xMin, blobPoints.get(k).y()-yMin);
        }
        path.lineTo(blobPoints.get(0).x()-xMin, blobPoints.get(0).y()-yMin);
        path.closePath();
        
        g.setColor(Color.BLACK);
        g.fill(path);
        
        List<Point> blobPoints2 = new ArrayList<Point>(blobPoints);
        
        
        
        int black = Color.BLACK.getRGB();
        for(int y1=0; y1<h; ++y1){
        	for(int x1=0; x1<w; ++x1){
        		if(bi.getRGB(x1, y1) == black) blobPoints2.add(PointImpl.valueOf(x1+xMin, y1+yMin));
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
    @Override
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
        } else if (!point.equals(other.getStartPoint())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        hash = 11 * hash + (this.point != null ? this.point.hashCode() : 0);
        return hash;
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
