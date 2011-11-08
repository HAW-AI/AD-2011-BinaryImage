package adp2.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import adp2.interfaces.*;

public class BlobImpl implements Blob {

	private final TreeSet<Point> pointsOfBlob;
	private final BinaryImage binaryImage;
	private final double circularity;

	/**
	 * Factory Methode von Blob. Erstellt ein Blob Objekt und gibt ihn zurück.
	 * 
	 * @param s
	 *            : Collection, die alle Points des Blobs enthält.
	 * @return
	 */
	public static Blob valueOf(Collection<Point> s, BinaryImage image) {
		return new BlobImpl(s, image);
	}

	/**
	 * Blob-Konstruktor.
	 * 
	 * @param pointsOfBlob
	 *            : Collection, die alle Points des Blobs enthält.
	 */
	private BlobImpl(Collection<Point> pointsOfBlob, BinaryImage image) {
		this.pointsOfBlob = new TreeSet<Point>(pointsOfBlob);
		this.binaryImage = image;
		this.circularity = 4 * Math.PI * pointCount()
				/ Math.pow(perimeter(), 2);
	}

	/**
	 * Gibt einen Iterator über die Menge der Points des Blobs zurück.
	 */
	public Iterator<Point> iterator() {
		return this.pointsOfBlob.iterator();
	}

	/**
	 * Gibt die alle Points eines Blobs als Set zurueck.
	 */
	public Set<Point> points() {
		return this.pointsOfBlob;
	}

	/**
	 * Gibt die Größe des Blobs als int zurück.
	 */
	public int pointCount() {
		return this.pointsOfBlob.size();
	}

	/**
	 * Gibt die Breite des Blobs als int zurück.
	 */
	public int width() {
		int max = pointsOfBlob.first().x(), min = pointsOfBlob.first().x();
		for (Point p : pointsOfBlob) {
			if (p.x() < min) {
				min = p.x();
			}
			if (p.x() > max) {
				max = p.x();
			}
		}
		return max - min + 1;
	}

	/**
	 * Gibt die Hoehe des Blobs als int zurück.
	 */
	public int height() {
		int max = pointsOfBlob.first().y(), min = pointsOfBlob.first().y();
		for (Point p : pointsOfBlob) {
			if (p.y() < min) {
				min = p.y();
			}
			if (p.y() > max) {
				max = p.y();
			}
		}
		return max - min + 1;
	}

	/**
	 * Gibt den Hashcode des Blobs als int zurück.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pointsOfBlob == null) ? 0 : pointsOfBlob.hashCode());
		return result;
	}

	/**
	 * Prüft die Wertgleichheit des Blobs mit einem anderen Objekt.
	 * 
	 * param obj: Zu vergleichendes Objekt. return: Gibt true bei Wertgleichheit
	 * zurück, ansonsten false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlobImpl other = (BlobImpl) obj;
		if (pointsOfBlob == null) {
			if (other.pointsOfBlob != null)
				return false;
		} else if (!pointsOfBlob.equals(other.pointsOfBlob))
			return false;
		return true;
	}

	public boolean contains(Point p) {
		if (p == null)
			return false;
		return pointsOfBlob.contains(p);
	}

	@Override
	public String toString() {
		return this.pointsOfBlob.toString();
	}

	@Override
	public BinaryImage binaryImage() {
		return this.getBinaryImage();
	}

	public BinaryImage getBinaryImage() {
		return binaryImage;
	}

	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * 
	 *         Gibt die Points zur�ck die zum Rand des Blobs geh�ren
	 * 
	 * 
	 * @return Set<Point> mit Punkten des Blobrandes
	 */
	@Override
	public Set<Point> boundary() {
		Set<Point> boundary = new TreeSet<Point>();
		int count = 0;
		if (binaryImage instanceof EightNeighborBinaryImage) {
			count = 8;
		}
		if (binaryImage instanceof FourNeighborBinaryImage) {
			count = 4;
		}

		for (Point p : pointsOfBlob) {
			// System.out.println("Neighbours" +binaryImage.neighbours(p) +
			// " p: " + p);

			if ((binaryImage.neighbours(p).size() < count)) {
				boundary.add(p);
			}
		}

		return boundary;
	}

	/**
	 * gibt die Anzahl der (feistehenden) Au�endkanten der Points der Umrandung
	 * zur�ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 */

	private int perimeter() {
		int counter = 0;

		for (Point p : pointsOfBlob) {
			int noOfNeighbours = binaryImage.neighbours(p).size();

			if ((noOfNeighbours < 4)) {
				counter += 4 - noOfNeighbours;
			}
		}

		return counter;
	}

	/**
	 * gibt die Circularity des blobs zur�ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 */
	@Override
	public double circularity() {
		return circularity;
		// return 4*Math.PI * pointCount() / Math.pow(perimeter(), 2);
	}

}
