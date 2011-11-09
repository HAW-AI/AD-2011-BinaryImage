package adp2.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import adp2.interfaces.*;

public class BlobImpl implements Blob {

	private final TreeSet<Point> pointsOfBlob;
	private final BinaryImage binaryImage;
	private final double circularity;
	private final int perimeter;


	/**
	 * Factory Methode von Blob. Erstellt ein Blob Objekt und gibt ihn zur√ºck.
	 * 
	 * @param s
	 *            : Collection, die alle Points des Blobs enth√§lt.
	 * @return
	 */
	public static Blob valueOf(Collection<Point> s, BinaryImage image) {
		return new BlobImpl(s, image);
	}

	/**
	 * Blob-Konstruktor.
	 * 
	 * @param pointsOfBlob
	 *            : Collection, die alle Points des Blobs enth‰lt.
	 */
	private BlobImpl(Collection<Point> pointsOfBlob, BinaryImage image) {
		this.pointsOfBlob = new TreeSet<Point>(pointsOfBlob);
		this.binaryImage = image;

		this.perimeter = initializePerimeter();
		// Berechnung der Circularity des Blobs, festgehalten in der private
		// final double circularity;
		this.circularity = initializeCircularity();
	}
	

	/**
	 * Gibt einen Iterator √ºber die Menge der Points des Blobs zur√ºck.
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
	 * Gibt die Gr√∂√üe des Blobs als int zur√ºck.
	 */
	public int pointCount() {
		return this.pointsOfBlob.size();
	}

	/**
	 * Gibt die Breite des Blobs als int zur√ºck.
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
	 * Gibt die Hoehe des Blobs als int zur√ºck.
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
	 * Gibt den Hashcode des Blobs als int zur√ºck.
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
	 * Pr√ºft die Wertgleichheit des Blobs mit einem anderen Objekt.
	 * 
	 * param obj: Zu vergleichendes Objekt. return: Gibt true bei Wertgleichheit
	 * zur√ºck, ansonsten false.
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
		return String.format(" A=%3d, P=%3d ", pointCount(), perimeter()).concat(this.pointsOfBlob.toString());
		//return this.pointsOfBlob.toString();
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
	 *         Gibt die Points zur¸ck die zum Rand des Blobs gehˆren
	 * 
	 * 
	 * @return Set<Point> mit Punkten des Blobrandes
	 */
	@Override
	public Set<Point> boundary() {
		Set<Point> boundary = new TreeSet<Point>();
		int count = 8;
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

	// FUNKTIONIERT NUR KORREKT F‹R 8er IMAGES
//	@Override
//	public Set<Point> boundary() {
//		Set<Point> boundary = new TreeSet<Point>();
//		Queue<Point> queue = new ConcurrentLinkedQueue<Point>();
//		
//		boolean stop = false;
//		// Anzahl Nachbarn bei 8er Nachbarschaft
//		int maxNeighbourCount = 8;
//		Point start;
//
//		// Startpunkt suchen (erster Punkt mit weniger Nachbarn)
//		Iterator<Point> it = pointsOfBlob.iterator();
//		start = it.next();
//		while (stop == false && it.hasNext()) {
//
//			if (binaryImage.neighbours(start).size() < maxNeighbourCount) {
//				stop = true;
//			}
//			start = it.next();
//		}
//		// Startpunkt in die Queue pushen
//		queue.add(start);
//
//		// Nachbarn der Punkte ¸berpr¸fen ob diese weniger als Count Nachbarn
//		// haben.
//		// Wenn ja, werden diese zur Queue hinzugef¸gt, falls sie nicht schon in
//		// Boundary stehen.
//		while (!queue.isEmpty()) {
//
//			for (Point p : binaryImage.neighbours(queue.element())) {
//
//				if ((binaryImage.neighbours(p).size() < maxNeighbourCount)
//						&& (!(boundary.contains(p)))) {
//					queue.add(p);
//				}
//			}
//			boundary.add(queue.poll());
//		}
//
//		// Entfernen der Inneren Ecken bei 4rer Nachbarschaft?!?!
////		if (binaryImage instanceof FourNeighborBinaryImage) {
////			maxNeighbourCount = 4;
////			for (Point p : boundary) {
////				if (binaryImage.neighbours(p).size() == 4)
////					boundary.remove(p);
////			}
////
////		}
//
//		return boundary;
//	}



	/**
	 * berechnet Perimeter, wird im Konstruktor zur initialisierung der perimeter konstante verwendet
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl Auﬂenkannten
	 * 
	 */
	private int initializePerimeter() {
		int counter = 0;

		for (Point p : boundary()) {
			int noOfNeighbours = binaryImage.neighbours(p).size();

			if ((noOfNeighbours < 4)) {
				counter += 4 - noOfNeighbours;
			}
		}

		return counter;
	}
	
	/**
	 * berechnet circularity, wird im Konstruktor zur initialisierung der circularity konstante verwendet
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl Auﬂenkannten
	 * 
	 */
	private double initializeCircularity(){
		return 4 * Math.PI * pointCount() / Math.pow(perimeter(), 2);
	}

	/**
	 * gibt die Anzahl der (freistehenden) Auﬂenkanten der Points der Umrandung
	 * zur¸ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl Auﬂenkannten
	 * 
	 */
	private int perimeter() {
		return perimeter;
	}

	/**
	 * gibt die Circularity des blobs zur¸ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return circularity (liegt zwischen 0 und 1)
	 */
	@Override
	public double circularity() {
		return circularity;
	}

}
