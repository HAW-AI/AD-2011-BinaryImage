package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import adp2.interfaces.*;

public abstract class AbstractBinaryImage extends AbstractMatrix implements
		BinaryImage {
	protected final List<Blob> blobs;
	protected final List<Point> points;
	protected final SortedSet<Point> pointsAsSet;
	protected final boolean isEightNbr; // true if eightNeighborBinaryImage,
										// false if fourNeighborBinaryImage

	protected AbstractBinaryImage(int width, int height, List<Integer> values,
			boolean isEightNbr) {
		super(width, height, values);
		this.isEightNbr = isEightNbr;
		this.points = wrapList(values);
		pointsAsSet = new TreeSet<Point>();
		for (Point point : points) {
			if (!(point instanceof NaP))
				pointsAsSet.add(point);
		}
		this.blobs = calcBlobs(pointsAsSet());
	}

	public List<Point> wrapList(List<Integer> values) {
		List<Point> wrapper = new ArrayList<Point>();
		for (int y = 0; y < height(); y++) {
			for (int x = 0; x < width(); x++) {
				if (get(x, y) > 0)
					wrapper.add(BinaryImages.point(x, y));
				else
					wrapper.add(BinaryImages.NaP(x, y));
			}
		}
		return wrapper;
	}

	@Override
	public boolean isEightNbr() {
		return isEightNbr;
	}

	/**
	 * dreht Hintergrund- und Vordergrundpunkte um, aus Punkten werden NaP, aus NaP Punkte
	 * 
	 **/
	protected List<Point> inversePoints() {
		List<Point> l = new ArrayList<Point>();
		for (Point p : points) {
			if (p instanceof NaP) {
				l.add(BinaryImages.point(p.x(), p.y()));
			} else {
				l.add(BinaryImages.NaP(p.x(), p.y()));
			}
		}
		return l;
	}

	/**
	 * Delegates the calculation of Blobs to the preferred algorithm
	 * 
	 * @return Ordered list of Blobs
	 */
	protected abstract List<Blob> calcBlobs(Set<Point> points);

	@Override
	public int blobCount() {
		return this.blobs.size();
	}

	@Override
	public Blob blob(int i) {
		if (i < 0 || i >= blobs.size())
			return BinaryImages.NaB();
		return blobs.get(i);
	}

	@Override
	public List<Blob> blobs() {
		return new ArrayList<Blob>(blobs);
	}

	protected abstract Set<Point> neighbours(Point point, Set<Point> points);

	@Override
	public Set<Point> neighbours(Point point) {
		return neighbours(point, pointsAsSet());
	}

	protected Set<Point> pointsAsSet() {
		return pointsAsSet;
	}

	@Override
	public boolean valueAt(Point point) {
		return points.contains(point);
	}

	@Override
	public boolean connected(Point point1, Point point2) {
		for (Blob elem : blobs) {
			if (elem.contains(point1)) {
				if (elem.contains(point2))
					return true;
				return false;
			}
		}
		return false;
	}

	/**
	 * Helper method to find out, if two points are neighbours (4 neighbourhood)
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Point
	 * @param Other
	 *            Point
	 * @return
	 */
	protected boolean areNeighbours4n(Point p1, Point p2) {
		return (Math.abs(p1.x() - p2.x()) == 1)
				&& (Math.abs(p1.y() - p2.y()) == 0)
				|| (Math.abs(p1.x() - p2.x()) == 0)
				&& (Math.abs(p1.y() - p2.y()) == 1);
	}

	/**
	 * Berechnet die Anzahl der Nicht-Randkanten eines Pixels in einem Bild und
	 * gibt diese zurück
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl der Nicht-Randkanten eines Pixels in einem Bild
	 */
	@Override
	public int noOfInnerEdges(Point point) {
		int counter = 0;
		// Set<Point> result = new TreeSet<Point>();
		for (Point other : pointsAsSet) {
			// Hier wird die Methode areNeighbours4n verwendet, unabhängig davon
			// ob es sich um eine 4er- oder 8er-Nachbarschaft handelt.
			// Das hängt damit zusammen, das es für den Umfang nur entscheidend
			// ist,
			// ob weitere Pixel an den Rand des Pixels grenzen. Pixel, die an
			// die Ecken
			// grenzen beeinflussen den Umfang des einen Pixels NICHT
			if (areNeighbours4n(point, other)) {
				counter++;
			}
		}
		return counter;
	}
}
