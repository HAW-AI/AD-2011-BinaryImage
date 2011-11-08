package adp2.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public abstract class AbstractBinaryImage implements BinaryImage {
	private final List<Blob> blobs;
	protected final SortedSet<Point> points;
	private final int width;
	private final int height;

	protected AbstractBinaryImage(List<List<Boolean>> shape) {
		// pre-conditions are checked in the BinaryImages factory
		height = heightFromMatrix(shape);
		width = widthFromMatrix(shape);
		points = matrixToPointSet(shape);
		blobs = calcBlobs(points);
	}

	protected AbstractBinaryImage(Set<Point> points, int width, int height) {
		// pre-conditions are checked in the BinaryImages factory
		this.height = height;
		this.width = width;
		this.points = new TreeSet<Point>(points);
		this.blobs = calcBlobs(points);
	}

	@Override
	public BinaryImage toFourNeighborBinaryImage() {
		return FourNeighborBinaryImage.valueOf(points, width, height);
	}

	@Override
	public BinaryImage toEigthNeighborBinaryImage() {
		return EightNeighborBinaryImage.valueOf(points, width, height);
	}

	@Override
	public Iterator<Blob> iterator() {
		return blobs().iterator();
	}

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

	@Override
	public int width() {
		return this.width;
	}

	@Override
	public int height() {
		return this.height;
	}

	@Override
	public Set<Point> neighbours(Point point) {
		return neighbours(point, this.points);
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

	@Override
	public abstract BinaryImage inverse();

	/**
	 * Delegates the calculation of Blobs to the preferred algorithm
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Set
	 *            of points as representation of the image
	 * @return Ordered list of Blobs
	 */
	private List<Blob> calcBlobs(Set<Point> points) {
		// return multiPass(points);
		return deepSearch(points);
	}

	/**
	 * DeepSearch algorithm to find blobs. This method delegates to a recursive
	 * method in order to find all blobs for the points not already visited
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Set
	 *            of points as representation of the image
	 * @return Ordered list of blobs
	 */
	private List<Blob> deepSearch(Set<Point> points) {
		List<Blob> result = new ArrayList<Blob>();
		Set<Point> visited = new TreeSet<Point>();
		for (Point p : points) {
			if (!visited.contains(p)) {
				Set<Point> blobAsSet = deepSearch_(p, visited, points);
				result.add(BinaryImages.blob(blobAsSet, this));
			}
		}
		return result;
	}

	/**
	 * Add the neighbours of all neighbours of a point, which haven't been
	 * visited before.
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param the
	 *            point, at which the DeepSearch is currently at
	 * @param a
	 *            Set of already visited points in order to avoid an infinite
	 *            regress
	 * @param Set
	 *            of points as representation of the image
	 * @return a Set of points as representation of a blob
	 */
	private Set<Point> deepSearch_(Point p, Set<Point> visited,
			Set<Point> points) {
		Set<Point> result = new TreeSet<Point>();
		result.add(p);
		visited.add(p);
		Set<Point> neighbours = this.neighbours(p, points);
		neighbours.removeAll(visited);
		for (Point neighbour : neighbours) {
			result.addAll(deepSearch_(neighbour, visited, points));
		}
		return result;
	}

	/*
	 * berechnet die Punkte, die nicht zu Blobs gehoeren ("die False-Punkte")
	 * 
	 * @author Sebastian Krome, Andreas Wimmer
	 */
	protected Set<Point> inversePoints() {
		Set<Point> blobPoints = allPoints();
		for (Blob elem : blobs) {
			for (Point p : elem) {
				blobPoints.remove(p);
			}
		}
		return blobPoints;
	}

	/*
	 * @author Sebastian Krome, Andreas Wimmer
	 * 
	 * @param Matrix, aus der das BinaryImage generiert wird
	 * 
	 * @return ein Set aller mit true markierten Punkte
	 * 
	 * Parameter werden nicht geprueft; geschieht bereits in der Factory
	 */
	private SortedSet<Point> matrixToPointSet(List<List<Boolean>> shape) {
		SortedSet<Point> result = new TreeSet<Point>();

		int out = 0;
		for (List<Boolean> elem : shape) {
			int in = 0;
			for (Boolean e : elem) {
				if (e.equals(true)) {
					result.add(BinaryImages.point(in, out));
				}
				in++;
			}
			out++;
		}
		return result;

	}

	/**
	 * Calculates width of image from matrix representation
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Binary
	 *            Image as matrix
	 * @return width of the matrix
	 */
	private int widthFromMatrix(List<List<Boolean>> shape) {
		if (shape.size() == 0)
			return 0;
		else
			return shape.get(0).size();
	}

	/**
	 * Calculates height of image from matrix representation
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Binary
	 *            Image as matrix
	 * @return height of the matrix
	 */
	private int heightFromMatrix(List<List<Boolean>> shape) {
		return shape.size();
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
	protected static boolean properPoints(Set<Point> points, int width,
			int height) {
		if (points == null)
			return false;
		for (Point p : points) {
			if (p.x() >= width || p.y() >= height || p.x() < 0 || p.y() < 0)
				return false;
		}
		return true;
	}

	/**
	 * Get neighbours for a specific point. Results depend on how the
	 * areNeighbours method is implemented in concrete class
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Point
	 * @param Set
	 *            of potential neighbour points
	 * @return
	 */
	protected Set<Point> neighbours(Point point, Set<Point> points) {
		Set<Point> result = new TreeSet<Point>();
		for (Point other : points) {
			if (areNeighbours(point, other)) {
				result.add(other);
			}
		}
		return result;
	}

	protected abstract boolean areNeighbours(Point p1, Point p2);

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
	 * Helper method to find out, if two points are neighbours (8 neighbourhood)
	 * 
	 * @author Oliver Behncke
	 * 
	 * @param Point
	 * @param Other
	 *            Point
	 * @return
	 */
	protected boolean areNeighbours8n(Point p1, Point p2) {
		return areNeighbours4n(p1, p2)
				|| ((Math.abs(p1.x() - p2.x()) == 1) && (Math.abs(p1.y()
						- p2.y()) == 1))
				|| ((Math.abs(p1.x() - p2.x()) == 1) && (Math.abs(p1.y()
						- p2.y()) == 1));
	}

	/**
	 * Multipass algorithm to extract blobs of binary image. Takes a set of
	 * points as representation of the binary image.
	 * 
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
		int labelCount = 1;
		for (Point p : points) {
			boolean neighborLabeled = false;
			for (Point other : neighbours(p, points)) {
				if (labelMap.containsKey(other)) {
					labelMap.put(p, labelMap.get(other));
					neighborLabeled = true;
				}

			}
			if (!neighborLabeled)
				labelMap.put(p, labelCount++);
		}

		// Relabel as long as no relabeling is possible
		// Relabeling consists of agregating neighboured labeled points to one
		// label
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
		for (int i = 1; i <= labelCount; i++) {
			Set<Point> blobSet = new TreeSet<Point>();
			for (Point p : points) {
				if (labelMap.get(p) == i)
					blobSet.add(p);
			}
			if (blobSet.size() != 0)
				result.add(BinaryImages.blob(blobSet, this));
		}
		return result;
	}

	/*
	 * berechnet alle Punkte eines Bildes aus width und height
	 * 
	 * @ author Sebastian Krome, Andreas Wimmer
	 */
	private Set<Point> allPoints() {
		Set<Point> result = new TreeSet<Point>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.add(BinaryImages.point(j, i));
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "BinaryImage: [ ";
		for (Blob b : blobs) {
			result += b.toString();
		}
		result += " ]";
		return result;
	}

	public String circularities() {
		int i = 1;
		StringBuilder sB = new StringBuilder();

		for (Blob blob : blobs) {

			sB.append(i);

			sB.append(".)  ");
			sB.append(blob.circularity());
			sB.append("\n");
			i++;
		}
		System.out.println(sB);
		return sB.toString();
	}
}
