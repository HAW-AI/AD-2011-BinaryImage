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
	 * Factory Methode von Blob. Erstellt ein Blob Objekt und gibt ihn zurÃ¼ck.
	 * 
	 * @param s
	 *            : Collection, die alle Points des Blobs enthÃ¤lt.
	 * @return
	 */
	public static Blob valueOf(Collection<Point> s, BinaryImage image) {
		return new BlobImpl(s, image);
	}

	/**
	 * Blob-Konstruktor.
	 * 
	 * @param pointsOfBlob
	 *            : Collection, die alle Points des Blobs enthï¿½lt.
	 */
	private BlobImpl(Collection<Point> pointsOfBlob, BinaryImage image) {
		this.pointsOfBlob = new TreeSet<Point>(pointsOfBlob);
		this.binaryImage = image;

		// Da die Circularity direkt berechnet wird, macht es sinn auch die Boundary sofort zu speichern.
		this.boundary = this.calcBoundary();
		// Berechnung der Circularity des Blobs, festgehalten in der private
		// final double circularity;
		this.perimeter = calcPerimeter();
		this.circularity = calcCircularity();
	}


	/**
	 * Gibt einen Iterator Ã¼ber die Menge der Points des Blobs zurÃ¼ck.
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
	 * Gibt die GrÃ¶ÃŸe des Blobs als int zurÃ¼ck.
	 */
	public int pointCount() {
		return this.pointsOfBlob.size();
	}

	/**
	 * Gibt die Breite des Blobs als int zurÃ¼ck.
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
	 * Gibt die Hoehe des Blobs als int zurÃ¼ck.
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
	 * Gibt den Hashcode des Blobs als int zurÃ¼ck.
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
	 * PrÃ¼ft die Wertgleichheit des Blobs mit einem anderen Objekt.
	 * 
	 * param obj: Zu vergleichendes Objekt. return: Gibt true bei Wertgleichheit
	 * zurÃ¼ck, ansonsten false.
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
	 *         Gibt die Points zurÃ¼ck die zum Rand des Blobs gehÃ¶ren
	 * 
	 * @return Set<Point> mit Punkten des Blobrandes
	 */
	
	@Override
	public Set<Point> boundary() {
		return this.boundary;
	}
	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 *         Berechnet den Rand des Blobs, für 4 und 8 Nachbarschaft können unterschiedliche Algorithmen verwendet werden.
	 * 
	 * @return Set<Point> mit Punkten des Blobrandes
	 */
	private Set<Point> calcBoundary() {
	
		int maxNeighbours = 4;
		if (binaryImage.isEightNbr()) {
			maxNeighbours = 8;
			return boundary_all(maxNeighbours);

		}
		return boundary_esser(maxNeighbours);
//		 return boundary_all(maxNeighbours);

	}

	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Berechnet alle Punkte die zum Rand des Blobs gehören indem alle Punkte des 
	 * Blobs auf die Anzahl ihrer Nachbarn im Blob getestet werden. 
	 * ISt diese Anzahl gleich der Anzahl der MaxNeighbours, gehört der Punkt nicht zum Rand.
	 * @param maxNeighbours
	 * @return
	 */
	private Set<Point> boundary_all(int maxNeighbours) {
		Set<Point> boundary = new TreeSet<Point>();
		for (Point p : pointsOfBlob) {

			if ((binaryImage.neighbours(p).size() < maxNeighbours)) {
				boundary.add(p);
			}
		}

		return boundary;
	}

	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Algorithmus nur für 4rer Nachbarschaft, da sonst ungenau
	 * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
	 * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
	 * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
	 * da dieses Sortiert ist.
	 * Der Vorige Punkt wird immer als Vorgänger zwischengespeichert, damit die Richtung für das Links 
	 * und Rechts Abbiegen ermittelt werden kann.
	 * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
	 * 
	 * Die "problematischen" Innenecken werden direkt aussortiert indem geprüft wird ob die Nachbaranzahl 4 ist.
	 * 
	 * 
	 * @param maxNeighbours
	 * @return
	 */
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
		// Bei größeren Blobs...
		else {
			do {

				if (this.contains(aktuell)) {
					// Bei 4 Neighbours ist der Punkt eine Innenecke und wird
					// nicht hinzugefügt
					if (binaryImage().neighbours(aktuell).size() < maxNeighbours) {
						result.add(aktuell);
					}
					temp = aktuell;
					aktuell = left_turn(vorg, aktuell);
					vorg = temp;
					
				} else {
					temp = aktuell;
					aktuell = right_turn(vorg, aktuell);
					vorg = temp;
				}
				
			} while (!(start.equals(aktuell)) || (result.size() == 1));
			// Suche solange weiter bis Startpunkt = Aktueller Punkt oder
			// Boundary Size == 1
			// Boundary Size == 1 umgeht Probleme bei Blobs die 2 Punkte Direkt
			// untereinander als Startrand haben.
			
		}
		return result;

	}
	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Algorithmus nur für 4rer Nachbarschaft, da sonst ungenau
	 * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
	 * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
	 * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
	 * da dieses Sortiert ist.
	 * Der Vorige Punkt wird immer als Vorgänger zwischengespeichert, damit die Richtung für das Links 
	 * und Rechts Abbiegen ermittelt werden kann.
	 * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
	 * 
	 * Die "problematischen" Innenecken werden direkt aussortiert indem geprüft wird ob die Nachbaranzahl 4 ist.
	 * 
	 * 
	 * @param maxNeighbours
	 * @return
	 */
	private Set<Point> boundary_esser2(int maxNeighbours) {
		Set<Point> boundary = new TreeSet<Point>();
		Set<Point> result = new TreeSet<Point>();
		Point start = this.pointsOfBlob.first();
		Point aktuell = start;
		Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
		Point temp;

		
		// When Blob Size = 1, dann Boundary = this.points();
		if (this.points().size() == 1)
			result.addAll(this.points());
		// Bei größeren Blobs...
		else {
			do {

				if (this.contains(aktuell)) {
					// Bei 4 Neighbours ist der Punkt eine Innenecke und wird
					// nicht hinzugefügt
					if (binaryImage().neighbours(aktuell).size() < maxNeighbours) {
						result.add(aktuell);
					}
					temp = aktuell;
					aktuell = left_turn(vorg, aktuell);
					vorg = temp;
					
				} else {
					temp = aktuell;
					aktuell = right_turn(vorg, aktuell);
					vorg = temp;
				}
				
			} while (!(start.equals(aktuell)) || (result.size() == 1));
			// Suche solange weiter bis Startpunkt = Aktueller Punkt oder
			// Boundary Size == 1
			// Boundary Size == 1 umgeht Probleme bei Blobs die 2 Punkte Direkt
			// untereinander als Startrand haben.
			
		}
		return result;

	}

	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Regelt das "links" Abbiegen für den Esser Algorithmus zur Kantensuche.
	 * Es wird der Vorgängerpunkt und der Aktuell zu prüfende Punkt verwendet.
	 * 
	 * @param vorg
	 * @param aktuell
	 * @return
	 */
	private Point left_turn(Point vorg, Point aktuell) {
		int new_x = 0;
		int new_y = 0;
		
		// Zuerst wird die X-Koordinate abgeglichen, sind diese gleich,
		// wird die Y-Koordinate verwendet.
	
		switch (aktuell.x() - vorg.x()) {
		case -1:
			//nach unten
			new_x = aktuell.x();
			new_y = aktuell.y() + 1;
			break;
		case 0:
			switch (aktuell.y() - vorg.y()) {
			case -1:
				//nach links
				new_x = aktuell.x() - 1;
				new_y = aktuell.y();
				break;
			case 1:
				//nach rechts
				new_x = aktuell.x() + 1;
				new_y = aktuell.y();
				break;
			default:
				return NaP.valueOf(new_x, new_y);
			}
			break;
		case 1:
			//oben
			new_x = aktuell.x();
			new_y = aktuell.y() - 1;
			break;
		default:
			return NaP.valueOf(new_x, new_y);
			}
		return BinaryImages.point(new_x, new_y);
	}

	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Regelt das "rechts" Abbiegen für den Esser Algorithmus zur Kantensuche.
	 * Es wird der Vorgängerpunkt und der Aktuell zu prüfende Punkt verwendet.
	 * 
	 * @param vorg
	 * @param aktuell
	 * @return
	 */
	private Point right_turn(Point vorg, Point aktuell) {
		int new_x = 0;
		int new_y = 0;
		
		// Zuerst wird die X-Koordinate abgeglichen, sind diese gleich,
		// wird die Y-Koordinate verwendet.

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
	
	
	
	// FUNKTIONIERT NUR KORREKT Fï¿½R 8er IMAGES
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
//		// Nachbarn der Punkte ï¿½berprï¿½fen ob diese weniger als Count Nachbarn
//		// haben.
//		// Wenn ja, werden diese zur Queue hinzugefï¿½gt, falls sie nicht schon in
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
	 * gibt die Anzahl der (freistehenden) Auï¿½enkanten der Points der Umrandung
	 * zurï¿½ck
	 * 
	 * @author Stephan Berngruber
	 * @author Tobias Meurer
	 * 
	 * @return Anzahl Auï¿½enkannten
	 * 
	 */


	private int perimeter() {
		return perimeter;
	}

	/**
	 * gibt die Circularity des blobs zurï¿½ck
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
	* @return Anzahl Außenkannten
	*
	*/
	private int calcPerimeter() {
		int counter = 0; //Zählt Anzahl der Außenkanten hoch => Wert des Umfangs

		for (Point p : boundary()) {

		//Anzahl der Rand-Kanten bestimmen
		int noOfInnerEdges = binaryImage.noOfInnerEdges(p);

		counter += 4 - noOfInnerEdges; //counter um Anzahl der Randkanten erhöhen

		}
		return counter;
	}
}
