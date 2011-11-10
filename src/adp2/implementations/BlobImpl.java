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
    private final Set<Point> boundary;

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
	 *            : Collection, die alle Points des Blobs enth�lt.
	 */
	private BlobImpl(Collection<Point> pointsOfBlob, BinaryImage image) {
		this.pointsOfBlob = new TreeSet<Point>(pointsOfBlob);
		this.binaryImage = image;

		
		this.boundary = this.boundary_();
		// Berechnung der Circularity des Blobs, festgehalten in der private
		// final double circularity;
		this.perimeter = calcPerimeter();
		this.circularity = calcCircularity();
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
		//return this.pointsOfBlob.toString();
		return "Area: " +pointCount() + " Perimeter: " + perimeter() + " Points:" +this.pointsOfBlob.toString();
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
	 *         Gibt die Points zurück die zum Rand des Blobs gehören
	 * 
	 * @return Set<Point> mit Punkten des Blobrandes
	 */
	
	@Override
	public Set<Point> boundary() {
		return this.boundary;
	}
	
	
	private Set<Point> boundary_() {
	
		int maxNeighbours = 4;
		if (binaryImage.isEightNbr()) {
			maxNeighbours = 8;
			return boundary_all(maxNeighbours);

		}
		return boundary_esser(maxNeighbours);
//		 return boundary_all(maxNeighbours);

	}

	private Set<Point> boundary_all(int maxNeighbours) {
		Set<Point> boundary = new TreeSet<Point>();
		for (Point p : pointsOfBlob) {

			if ((binaryImage.neighbours(p).size() < maxNeighbours)) {
				boundary.add(p);
			}
		}

		return boundary;
	}

	private Set<Point> boundary_esser(int maxNeighbours) {
		Set<Point> boundary = new TreeSet<Point>();
		Set<Point> result = new TreeSet<Point>();
		Point start = this.pointsOfBlob.first();
		Point aktuell = start;
		Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
		Point temp;

		
		// When Blob Size = 1, dann Boundary = this.points();
		if (this.points().size() == 1)
			result.addAll(this.points());
		// Bei gr��eren Blobs...
		else {
			do {

				if (this.contains(aktuell)) {
					// Bei 4 Neighbours ist der Punkt eine Innenecke und wird
					// nicht hinzugef�gt
					if (binaryImage().neighbours(aktuell).size() < maxNeighbours) {
						boundary.add(aktuell);
					}
					temp = aktuell;
					aktuell = left_turn(vorg, aktuell);
					vorg = temp;
					
				} else {
					temp = aktuell;
					aktuell = right_turn(vorg, aktuell);
					vorg = temp;
				}
			} while (!(start.equals(aktuell)) || (boundary.size() == 1));
			// Suche solange weiter bis Startpunkt = Aktueller Punkt oder
			// Boundary Size == 1
			// Boundary Size == 1 umgeht Probleme bei Blobs die 2 Punkte Direkt
			// untereinander als Startrand haben.
			result.addAll(boundary);
		}

		return result;

	}

	private Point left_turn(Point vorg, Point aktuell) {
		int new_x = 0;
		int new_y = 0;
		
		
		switch (aktuell.x() - vorg.x()) {
		case -1:
			new_x = aktuell.x();
			new_y = aktuell.y() + 1;
			break;
		case 0:
			switch (aktuell.y() - vorg.y()) {
			case -1:
				new_x = aktuell.x() - 1;
				new_y = aktuell.y();
				break;
			case 1:
				new_x = aktuell.x() + 1;
				new_y = aktuell.y();
				break;
			default:
				return NaP.valueOf(new_x, new_y);
			}
			break;
		case 1:
			new_x = aktuell.x();
			new_y = aktuell.y() - 1;
			break;
		default:
			return NaP.valueOf(new_x, new_y);
			}
		return BinaryImages.point(new_x, new_y);
	}

	private Point right_turn(Point vorg, Point aktuell) {
		int new_x = 0;
		int new_y = 0;

		switch (aktuell.x() - vorg.x()) {
		case -1:
			new_x = aktuell.x();
			new_y = aktuell.y() - 1;
			break;
		case 0:
			switch (aktuell.y() - vorg.y()) {
			case -1:
				new_x = aktuell.x() + 1;
				new_y = aktuell.y();
				break;
			case 1:
				new_x = aktuell.x() - 1;
				new_y = aktuell.y();
				break;
			default:
				return NaP.valueOf(new_x, new_y);
			}
			break;
		case 1:
			new_x = aktuell.x();
			new_y = aktuell.y() + 1;
			break;
		default:
			return NaP.valueOf(new_x, new_y);
		}
		return BinaryImages.point(new_x, new_y);
	}
	
	
	
	// FUNKTIONIERT NUR KORREKT F�R 8er IMAGES
//@Override
//	public Set<Point> boundary() {
//		Set<Point> boundary = new TreeSet<Point>();
//		Queue<Point> queue = new ConcurrentLinkedQueue<Point>();
//		boolean stop = false;
//		int count = 8;
//		Point start;
//		
////		if (binaryImage instanceof EightNeighborBinaryImage) {
////			count = 8;
////		}
////		if (binaryImage instanceof FourNeighborBinaryImage) {
////			count = 4;
////		}
//		
//		// Startpunkt suchen (erster Punkt mit weniger Nachbarn)
//		Iterator<Point> it = pointsOfBlob.iterator();
//		start = it.next();
//		while (stop == false && it.hasNext()) {
//			
//			if (binaryImage.neighbours(start).size() < count) {
//				stop = true;
//			}
//			start = it.next();
//		}
//		// Startpunkt in die Queue pushen
//		queue.add(start);
//
//		// Nachbarn der Punkte �berpr�fen ob diese weniger als Count Nachbarn
//		// haben.
//		// Wenn ja, werden diese zur Queue hinzugef�gt, falls sie nicht schon in
//		// Boundary stehen.
//		while (!queue.isEmpty()) {
//			
//			for (Point p : binaryImage.neighbours(queue.element())) {
//				
//				if ((binaryImage.neighbours(p).size() < count)
//						&& (!(boundary.contains(p)))) {
//					queue.add(p);		
//				}	
//			}
//			boundary.add(queue.poll());	
//		}
//		return boundary;
//	}

	
	
	
	
	/**
	 * gibt die Anzahl der (freistehenden) Au�enkanten der Points der Umrandung
	 * zur�ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl Au�enkannten
	 * 
	 */


	private int perimeter() {
		return perimeter;
	}

	/**
	 * gibt die Circularity des blobs zur�ck
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
	
	/**
	 * errechnet Circularity
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return circularity (liegt zwischen 0 und 1)
	 */
	private double calcCircularity(){
		return 4 * Math.PI * pointCount() / Math.pow(perimeter(), 2);
	}

	
	/**
	* berechnet Perimeter, wird im Konstruktor zur initialisierung der perimeter konstante verwendet
	*
	* @author Stephan Berngruber
	* @author Tobias Meurer
	*
	* @return Anzahl Au�enkannten
	*
	*/
	private int calcPerimeter() {
		int counter = 0; //Z�hlt Anzahl der Au�enkanten hoch => Wert des Umfangs

		for (Point p : boundary()) {

		//Anzahl der Rand-Kanten bestimmen
		int noOfInnerEdges = binaryImage.noOfInnerEdges(p);

		System.out.println(noOfInnerEdges);
		if ((noOfInnerEdges < 4)) {
				counter += 4 - noOfInnerEdges; //counter um Anzahl der Randkanten erh�hen
			}
		}
		return counter;
	}
}
