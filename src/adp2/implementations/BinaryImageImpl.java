package adp2.implementations;

import java.util.*;

import adp2.interfaces.*;

public class BinaryImageImpl extends AbstractBinaryImage {

    public static BinaryImage valueOf(List<List<Boolean>> shape, boolean isEightNbr) {
        int height = shape.size();
        int width = shape.size() > 0 ? shape.get(0).size() : 0;
        List<Integer> l = new ArrayList<Integer>();
        for (List<Boolean> list : shape) {
            for (Boolean b : list) {
                l.add(b ? 1 : 0);
            }
        }
        if (l.size() != width * height) {
            return NaBI.valueOf();
        }
        return new BinaryImageImpl(width, height, l, isEightNbr);
    }

    protected BinaryImageImpl(int width, int height, List<Integer> values, boolean isEightNbr) {
        super(width, height, values, isEightNbr);
    }

    static BinaryImage valueOf(int width, int height, List<Integer> values, boolean isEightNbr) {
        for (Integer i : values) {
            if (i != 1 && i != 0) {
                return BinaryImages.NaBI();
            }
        }
        return new BinaryImageImpl(width, height, values, isEightNbr);
    }

    static BinaryImage valueOf(List<Point> points, int width, int height, boolean isEightNbr) {
        if (width < 0 || height < 0 || !properPoints(points, width, height)) {
            return BinaryImages.NaBI();
        }
        return new BinaryImageImpl(width, height, pointSetToMatrixList(points), isEightNbr);
    }

    @Override
    public BinaryImage toFourNeighborBinaryImage() {
        if (isEightNbr()) {
            return BinaryImageImpl.valueOf(width(), height(), values(), false);
        } else {
            return this;
        }

    }

    @Override
    public BinaryImage toEigthNeighborBinaryImage() {
        if (isEightNbr()) {
            return this;
        } else {
            return BinaryImageImpl.valueOf(width(), height(), values(), true);
        }
    }

    @Override
    public BinaryImage inverse() {
        return valueOf(inversePoints(), this.width(), this.height(), isEightNbr());

    }

    /**
     * Delegates the calculation of Blobs to the preferred algorithm
     * 
     * @return Ordered list of Blobs
     */
    @Override
    protected List<Blob> calcBlobs(Set<Point> points) {
        return multiPass(points);
    }

    /**
     * @author Alekasndr Nosov, Kathrin Kahlh√∂fer
     * @param matrix
     * @return
     */
    protected static List<Integer> pointSetToMatrixList(List<Point> points) {
        List<Integer> result = new ArrayList<Integer>();
        for (Point p : points) {
            if (p instanceof PointImpl) {
                result.add(1);
            } else {
                result.add(0);
            }
        }
        return result;
    }

    /**
     * Checks a set of points and returns false if Note: This relies on the
     * implementation of matrixToPointSet, which currently creates points from
     * 0..(widht-1) and 0..(height-1). This check needs to be changed, when
     * coordinates are created differently
     * 
     * @author Oliver Behncke
     * 
     * @param points
     * @param width
     * @param height
     * @return
     */
    protected static boolean properPoints(List<Point> points, int width,
            int height) {
        if (points == null) {
            return false;
        }
        for (Point p : points) {
            if (p.x() >= width || p.y() >= height || p.x() < 0 || p.y() < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Set<Point> neighbours(Point point, Set<Point> points) {
        return isEightNbr() ? neighbours8er(point, points) : neighbours4er(point, points);
    }

    protected Set<Point> neighbours4er(Point point, Set<Point> points) {
        Set<Point> result = new HashSet<Point>();
        if (point instanceof NaP) {
            return result;
        }
        int x = point.x();
        int y = point.y();
        if (width() > x + 1 && get(x + 1, y) == 1) {
            result.add(getPoint(x + 1, y));
        }
        if (height() > y + 1 && get(x, y + 1) == 1) {
            result.add(getPoint(x, y + 1));
        }
        if (x > 0 && get(x - 1, y) == 1) {
            result.add(getPoint(x - 1, y));
        }
        if (y > 0 && get(x, y - 1) == 1) {
            result.add(getPoint(x, y - 1));
        }
        return result;
    }

    protected Set<Point> neighbours8er(Point point, Set<Point> points) {
        Set<Point> result = neighbours4er(point, points);
        if (point instanceof NaP) {
            return result;
        }
        int x = point.x();
        int y = point.y();
        if (width() > x + 1 && height() > y + 1 && get(x + 1, y + 1) == 1) {
            result.add(getPoint(x + 1, y + 1));
        }
        if (x > 0 && y > 0 && get(x - 1, y - 1) == 1) {
            result.add(getPoint(x - 1, y - 1));
        }
        if (x > 0 && height() > y + 1 && get(x - 1, y + 1) == 1) {
            result.add(getPoint(x - 1, y + 1));
        }
        if (width() > x + 1 && y > 0 && get(x + 1, y - 1) == 1) {
            result.add(getPoint(x + 1, y - 1));
        }
        return result;
    }

    private Point getPoint(int x, int y) {
        return allPoints.get(x + (y * width()));
    }

    /**
     * Multipass algorithm to extract blobs of binary image. Takes a set of
     * points as representation of the binary image.
     * (Mehrere Durchgänge über die Vordergrundpixel um Blobs zu finden)
     * @author Panos
     * @author Oliver Behncke
     * 
     * @param Set
     *            of points
     * @return list of blobs
     */
    protected List<Blob> multiPass(Set<Point> points) {

        List<Blob> result = new ArrayList<Blob>();
        Map<Point, Integer> labelMap = new HashMap<Point, Integer>();

        // Initial labeling
        // Erster Durchgang: Gehe über jeden (vordergrund) Pixel, wenn ein Nachbar in der labelMap
        // gib aktuellem Pixel die gleiche Nummer, die auch der Nachbar hat,
        // sonst gib ihm eine nummer - labelcount - und zähle labelcount++
        // wenn fertig hat man eine Nummerierung die eventuell noch unrichtige 
        // nummerierungen enthält 
        int labelCount = 1;
        for (Point p : points) {
            boolean neighborLabeled = false;
            for (Point other : neighbours(p, points)) {
                if (labelMap.containsKey(other)) {
                    labelMap.put(p, labelMap.get(other));
                    neighborLabeled = true;
                }

            }
            if (!neighborLabeled) {
                labelMap.put(p, labelCount++);
            }
        }

        // Relabel as long as no relabeling is possible
        // Relabeling consists of agregating neighboured labeled points to one
        // label
        // Gehe erneut �ber alle Vordergrundpixel r�ber und pr�fe ob aktueller Pixel 
        // einen nachbar mit kleinerer Nummerrierung hat
        // wenn ja gib akt. Pixel nummer von Nachbar
        // Mach das ganze so oft bis es einen Durchgang gab ohne �nderungen in den Nummerierungen
        // Nun hat man eine Map in der alle Pixel eines Blobs die gleiche nummer als Value haben
        boolean labelChanged = true;
        while (labelChanged) {
            labelChanged = false;
            for (Point p : points) {
                for (Point other : neighbours(p)) {
                    if (labelMap.get(other) < labelMap.get(p)) {
                        labelMap.put(p, labelMap.get(other));
                        labelChanged = true;
                    }
                }
            }
        }

        // Extract blobs from labeled map
        // Blobs aus map erzeugen anhand gleicher nummern

        for (int i = 1; i <= labelCount; i++) {
            Set<Point> blobSet = new TreeSet<Point>();
            for (Point p : points) {
                if (labelMap.get(p) == i) {
                    blobSet.add(p);
                }
            }
            if (blobSet.size() != 0) {
                result.add(BinaryImages.blob(blobSet, this));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "BinaryImage: [ \n";
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                result += getPoint(x, y).toString();
            }
            result += "\n";
        }
        result += " ]";
        return result;
    }

    /**
     * gibt String mit Circularity-Werten aller Blobs zurÔøΩck
     * 
     * @author Stephan Berngruber
     * @author Tobias Meurer
     * 
     * @return String mit Circularity-Werten
     */
    @Override
    public String circularities() {
        int i = 1;
        StringBuilder sB = new StringBuilder();
        String nl = "\n";
        String zwischenraum = ".)  ";
//		 String area = "    Area: ";
//		 String perimeter = "    Perimeter: ";

        for (Blob blob : blobs) {
            sB.append(String.format("%5d", i));

            sB.append(zwischenraum);
            sB.append(blob.circularity());
            //	 sB.append(blob);
            sB.append(nl);
            i++;
        }
        // System.out.println(sB);
        return sB.toString();
    }
}
