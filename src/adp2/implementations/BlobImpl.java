package adp2.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Point;

public class BlobImpl implements Blob {

    private final TreeSet<Point> pointsOfBlob;
    private final BinaryImage binaryImage;
    private final double circularity;
    private final double perimeter;
    private final Set<Point> boundary;
    private final List<Integer> boundary2;

    /**
     * Factory Methode von Blob. Erstellt ein Blob Objekt und gibt ihn zurueck.
     * 
     * @param s
     *            : Collection, die alle Points des Blobs enthaelt.
     * @return
     */
    public static Blob valueOf(Collection<Point> s, BinaryImage image) {
        return new BlobImpl(s, image);
    }

    /**
     * Blob-Konstruktor.
     * 
     * @param pointsOfBlob
     *            : Collection, die alle Points des Blobs enthaelt.
     */
    private BlobImpl(Collection<Point> pointsOfBlob, BinaryImage image) {
        this.pointsOfBlob = new TreeSet<Point>(pointsOfBlob);
        this.binaryImage = image;

        // Da die Circularity direkt berechnet wird, macht es sinn auch die Boundary sofort zu speichern.
        this.boundary = this.calcBoundary();
        this.boundary2 = this.calcBoundary2();
        // Berechnung der Circularity des Blobs, festgehalten in der private
        // final double circularity;
        //this.perimeter = calcPerimeter();
        this.perimeter = calcPerimeterGruppe3();
        this.circularity = calcCircularity();
    }

    /**
     * Gibt einen Iterator ueber die Menge der Points des Blobs zurueck.
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
     * Gibt die Groesse des Blobs als int zurueck.
     */
    public int pointCount() {
        return this.pointsOfBlob.size();
    }

    /**
     * Gibt die Breite des Blobs als int zurueck.
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
     * Gibt die Hoehe des Blobs als int zurueck.
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
     * Gibt den Hashcode des Blobs als int zurueck.
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
     * Prueft die Wertgleichheit des Blobs mit einem anderen Objekt.
     * 
     * param obj: Zu vergleichendes Objekt. return: Gibt true bei Wertgleichheit
     * zurueck, ansonsten false.
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
        BlobImpl other = (BlobImpl) obj;
        if (pointsOfBlob == null) {
            if (other.pointsOfBlob != null) {
                return false;
            }
        } else if (!pointsOfBlob.equals(other.pointsOfBlob)) {
            return false;
        }
        return true;
    }

    public boolean contains(Point p) {
        if (p == null) {
            return false;
        }
        return pointsOfBlob.contains(p);
    }

    @Override
    public String toString() {
        //return this.pointsOfBlob.toString();
        return "Area: " + pointCount() + " Perimeter: " + perimeter() + " Points:" + this.pointsOfBlob.toString();
    }

    @Override
    public BinaryImage binaryImage() {
        return this.getBinaryImage();
    }
    //TODO redudant
    @Deprecated
    public BinaryImage getBinaryImage() {
        return binaryImage;
    }

    /**
     * @author Kai Bielenberg
     * @author Tobias Mainusch
     * 
     *         Gibt die Points zurueck die zum Rand des Blobs gehoeren
     * 
     * @return Set<Point> mit Punkten des Blobrandes
     */
    @Override
    public Set<Point> boundary() {
        return this.boundary;
    }

    @Override
    public List<Integer> boundary2() {
        return this.boundary2;
    }

    /**
     * @author Kai Bielenberg
     * @author Tobias Mainusch
     * 
     *         Berechnet den Rand des Blobs, fuer 4 und 8 Nachbarschaft koennen unterschiedliche Algorithmen verwendet werden.
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

    private List<Integer> calcBoundary2() {
<<<<<<< HEAD
=======

>>>>>>> 8bb2a940a692a567ebc6d1e6b4b7d4e9a461ff0c
        int maxNeighbours = 4;
//		if (binaryImage.isEightNbr()) {
//			maxNeighbours = 8;
//			return boundary_all(maxNeighbours);
//
//		}
        return boundary_esser2(maxNeighbours).getSequence();
//		 return boundary_all(maxNeighbours);

    }

    /**
     * @author Kai Bielenberg
     * @author Tobias Mainusch
     * 
     * Berechnet alle Punkte die zum Rand des Blobs gehoeren indem alle Punkte des 
     * Blobs auf die Anzahl ihrer Nachbarn im Blob getestet werden. 
     * ISt diese Anzahl gleich der Anzahl der MaxNeighbours, gehoert der Punkt nicht zum Rand.
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
     * Algorithmus nur fuer 4rer Nachbarschaft, da sonst ungenau
     * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
     * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
     * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
     * da dieses Sortiert ist.
     * Der Vorige Punkt wird immer als Vorgaenger zwischengespeichert, damit die Richtung fuer das Links 
     * und Rechts Abbiegen ermittelt werden kann.
     * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
     * 
     * Die "problematischen" Innenecken werden direkt aussortiert indem geprueft wird ob die Nachbaranzahl 4 ist.
     * 
     * 
     * @param maxNeighbours
     * @return
     */
    private Set<Point> boundary_esser(int maxNeighbours) {
        Set<Point> result = new TreeSet<Point>();
        Point start = this.pointsOfBlob.first();
        Point aktuell = start;
        Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
        Point temp;


        // When Blob Size = 1, dann Boundary = this.points();
        if (this.points().size() == 1) {
            result.addAll(this.points());
        } // Bei groesseren Blobs...
        else {
            do {

                if (this.contains(aktuell)) {
                    // Bei 4 Neighbours ist der Punkt eine Innenecke und wird
                    // nicht hinzugefuegt
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
     * Algorithmus nur fuer 4rer Nachbarschaft, da sonst ungenau
     * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
     * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
     * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
     * da dieses Sortiert ist.
     * Der Vorige Punkt wird immer als Vorgaenger zwischengespeichert, damit die Richtung fuer das Links 
     * und Rechts Abbiegen ermittelt werden kann.
     * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
     * 
     * Die "problematischen" Innenecken werden direkt aussortiert indem geprueft wird ob die Nachbaranzahl 4 ist.
     * 
     * 
     * @param maxNeighbours
     * @return
     */
    public BoundarySequence boundary_esser2(int maxNeighbours) {
        Point start = this.pointsOfBlob.first();
        Set<Point> result = new TreeSet<Point>();
        List<Integer> sequence = new ArrayList<Integer>();
        BoundarySequence res = new BoundarySequenceImpl(start, sequence);
        Point aktuell = start;
        Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
        Point temp;
        Point previous = null;

        // When Blob Size = 1, dann Boundary = this.points();
        if (this.points().size() == 1) {
            result.addAll(this.points());
        } // Bei groesseren Blobs...
        else {
            do {
                if (this.contains(aktuell)) {
                    // Bei 4 Neighbours ist der Punkt eine Innenecke und wird
                    // nicht hinzugefuegt
                    if (binaryImage().neighbours(aktuell).size() < maxNeighbours) {
                        result.add(aktuell);
                    }
                    temp = aktuell;

                    if (previous != null) {
                        // nach rechts
                        if (aktuell.x() > previous.x()) {
                            // rechts oben 7
                            if (aktuell.y() > previous.y()) {
                                sequence.add(7);
                            // rechts
                            } else if (aktuell.y() == previous.y()) {
                                sequence.add(0);
                            // rechts unten 1
                            } else {
                                sequence.add(1);
                            }
                        // nach oben oder unten
                        } else if (aktuell.x() == previous.x()) {
                            // nach unten
                            if (aktuell.y() > previous.y()) {
                                sequence.add(6);
                            // 
                            // } else if (aktuell.y() == previous.y()) { gleichheit
                            // nach oben
                            } else {
                                sequence.add(2);
                            }
                        } else {
                            if (aktuell.y() > previous.y()) {
                                sequence.add(5);
                            } else if (aktuell.y() == previous.y()) {
                                sequence.add(4);
                            } else {
                                sequence.add(3);
                            }
                        }

                    }

                    previous = aktuell;

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

        return res;

    }

    /**
     * @author Kai Bielenberg
     * @author Tobias Mainusch
     * 
     * Regelt das "links" Abbiegen fuer den Esser Algorithmus zur Kantensuche.
     * Es wird der Vorgaengerpunkt und der Aktuell zu pruefende Punkt verwendet.
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
     * Regelt das "rechts" Abbiegen fuer den Esser Algorithmus zur Kantensuche.
     * Es wird der Vorgaengerpunkt und der Aktuell zu pruefende Punkt verwendet.
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

    // FUNKTIONIERT NUR KORREKT FUER 8er IMAGES
<<<<<<< HEAD
	private List<Integer> calcBoundary2() {
		int maxNeighbours = 4;
		if (binaryImage.isEightNbr()) {
			maxNeighbours = 8;
		}
		return boundary_esser2(maxNeighbours).getSequence();
	}
	
	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Berechnet alle Punkte die zum Rand des Blobs gehoeren indem alle Punkte des 
	 * Blobs auf die Anzahl ihrer Nachbarn im Blob getestet werden. 
	 * ISt diese Anzahl gleich der Anzahl der MaxNeighbours, gehoert der Punkt nicht zum Rand.
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
	 * Algorithmus nur fuer 4rer Nachbarschaft, da sonst ungenau
	 * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
	 * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
	 * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
	 * da dieses Sortiert ist.
	 * Der Vorige Punkt wird immer als Vorgaenger zwischengespeichert, damit die Richtung fuer das Links 
	 * und Rechts Abbiegen ermittelt werden kann.
	 * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
	 * 
	 * Die "problematischen" Innenecken werden direkt aussortiert indem geprueft wird ob die Nachbaranzahl 4 ist.
	 * 
	 * 
	 * @param maxNeighbours
	 * @return
	 */
	private Set<Point> boundary_esser(int maxNeighbours) {
		Set<Point> result = new TreeSet<Point>();
		Point start = this.pointsOfBlob.first();
		Point aktuell = start;
		Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
		Point temp;

		
		// When Blob Size = 1, dann Boundary = this.points();
		if (this.points().size() == 1)
			result.addAll(this.points());
		// Bei groesseren Blobs...
		else {
			do {

				if (this.contains(aktuell)) {
					// Bei 4 Neighbours ist der Punkt eine Innenecke und wird
					// nicht hinzugefuegt
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
	 * Algorithmus nur fuer 4rer Nachbarschaft, da sonst ungenau
	 * Berechnet die Kanten des Blobs anhand eines Sprungverfahrens.
	 * Ist der Aktuelle Punkt im Blob, biegt man auf seinem Weg links ab, ansonsten rechts.
	 * Der Start ist der Erste Punkt des Blobs im Koordinatensystem. Dieser ist der erste im TreeSet,
	 * da dieses Sortiert ist.
	 * Der Vorige Punkt wird immer als Vorgaenger zwischengespeichert, damit die Richtung fuer das Links 
	 * und Rechts Abbiegen ermittelt werden kann.
	 * Der Algorithmus hat Probleme mit Blobs.size() = 1, daher fangen wir das Ergebis direkt ab.
	 * 
	 * Die "problematischen" Innenecken werden direkt aussortiert indem geprueft wird ob die Nachbaranzahl 4 ist.
	 * 
	 * 
	 * @param maxNeighbours
	 * @return
	 */
	public BoundarySequence boundary_esser2(int maxNeighbours) {
		Point start = this.pointsOfBlob.first();
		Set<Point> result = new TreeSet<Point>();
		List<Integer> sequence = new ArrayList<Integer>();
		BoundarySequence res = new BoundarySequenceImpl(start, sequence);
		Point aktuell = start;
		Point vorg = BinaryImages.point(aktuell.x() - 1, aktuell.y());
		Point temp;
		Point previous = null;
		
		// When Blob Size = 1, dann Boundary = this.points();
		if (this.points().size() == 1)
//			result.addAll(this.points());
			return res;
		// Bei groesseren Blobs...
		else {
			do {
				if (this.contains(aktuell)) {
					// Bei 4 Neighbours ist der Punkt eine Innenecke und wird
					// nicht hinzugefuegt
					if (binaryImage().neighbours(aktuell).size() < maxNeighbours) {
						result.add(aktuell);
					}
					temp = aktuell;
					
					if(previous != null){
						if(aktuell.x() > previous.x()){
							if(aktuell.y() > previous.y()){
								sequence.add(7);
							} else if (aktuell.y() == previous.y()){
								sequence.add(0);
							} else {
								sequence.add(1);
							}
						}
						
						else if(aktuell.x() == previous.x()){
							if(aktuell.y() > previous.y()){
								sequence.add(6);
							} else if (aktuell.y() == previous.y()){
								
							} else {
								sequence.add(2);
							}
						}
						
						else {
							if(aktuell.y() > previous.y()){
								sequence.add(5);
							} else if (aktuell.y() == previous.y()){
								sequence.add(4);
							} else {
								sequence.add(3);
							}
						}
						
					}
					
					previous = aktuell;
					
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
		
		return res;

	}

	/**
	 * @author Kai Bielenberg
	 * @author Tobias Mainusch
	 * 
	 * Regelt das "links" Abbiegen fuer den Esser Algorithmus zur Kantensuche.
	 * Es wird der Vorgaengerpunkt und der Aktuell zu pruefende Punkt verwendet.
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
	 * Regelt das "rechts" Abbiegen fuer den Esser Algorithmus zur Kantensuche.
	 * Es wird der Vorgaengerpunkt und der Aktuell zu pruefende Punkt verwendet.
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
	
	
	
	// FUNKTIONIERT NUR KORREKT FUER 8er IMAGES
>>>>>>> 3552d988445d389400876bb8ba690d4c44c21032
=======

>>>>>>> 8bb2a940a692a567ebc6d1e6b4b7d4e9a461ff0c
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
//		// Nachbarn der Punkte ueberpruefen ob diese weniger als Count Nachbarn
//		// haben.
//		// Wenn ja, werden diese zur Queue hinzugefuegt, falls sie nicht schon in
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
     * Gibt den Umfang des Blobs zurueck.
     * 
     * @author Stephan Berngruber
     * @author Tobias Meurer
     * @author aav511
     * 
     * @return Anzahl Aussenkannten
     * 
     */
    public double perimeter() {
        return perimeter;
    }

    /**
     * gibt die Circularity des blobs zurueck
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
    private double calcCircularity() {
        return 4 * Math.PI * pointCount() / Math.pow(perimeter(), 2);
    }

    /**
     * berechnet Perimeter, wird im Konstruktor zur initialisierung der perimeter konstante verwendet
     * @deprecated stattdessen calcPerimeterGruppe3() benutzen. 
     * 
     * @author Stephan Berngruber
     * @author Tobias Meurer
     *
     * @return Anzahl Aussenkannten
     *
     */
    @SuppressWarnings("unused")
    private int calcPerimeter() {
        int counter = 0; //Zaehlt Anzahl der Aussenkanten hoch => Wert des Umfangs

        for (Point p : boundary()) {

            //Anzahl der Rand-Kanten bestimmen
            int noOfInnerEdges = binaryImage.noOfInnerEdges(p);

            counter += 4 - noOfInnerEdges; //counter um Anzahl der Randkanten erhoehen

        }
        return counter;
    }

    /**
     * Berechnet den Umfang nach Methode der Gruppe 3
     * Schraege Kanten zaehlen sqrt(2), Gerade 1 Schritt.
     * 
     * @author aav511
     * 
     * @return Umfang als double
     */
    private double calcPerimeterGruppe3() {

        double res = 0;
        List<Integer> seq = boundary_esser2(4).getSequence();
        if (!seq.isEmpty()) {
            for (int i : boundary_esser2(4).getSequence()) {
                if (i % 2 == 0) {
                    res += 1;
                } else {
                    res += Math.sqrt(2);
                }
            }
        } else {
            res = 1;
        }
        return res;
    }
}
