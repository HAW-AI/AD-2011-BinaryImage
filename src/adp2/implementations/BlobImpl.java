package adp2.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import adp2.interfaces.*;

public class BlobImpl implements Blob {
	
	private final TreeSet<Point> s;

	/**
	 * Factory Methode von Blob. Erstellt ein Blob Objekt und gibt ihn zurück.
	 * 
	 * @param s: Collection, die alle Points des Blobs enthält.
	 * @return
	 */
	public static Blob valueOf(Collection<Point> s){
		return new BlobImpl(s);
	}
	/**
	 * Blob-Konstruktor.
	 * 
	 * @param s: Collection, die alle Points des Blobs enthält.
	 */
	private BlobImpl(Collection<Point> s){
		this.s = new TreeSet<Point>(s);
	}

	/**
	 * Gibt einen Iterator über die Menge der Points des Blobs zurück.
	 */
	public Iterator<Point> iterator() {
		return this.s.iterator();
	}
	
	/**
	 * Gibt die alle Points eines Blobs als Set zurueck.
	 */
	public Set<Point> points(){
		return this.s;
	}
	
	/**
	 * Gibt die Größe des Blobs als int zurück.
	 */
	public int pointCount() {
		return this.s.size();
	}

	/**
	 * Gibt die Breite des Blobs als int zurück.
	 */
	public int width() {
		int max = s.first().x(),min = s.first().x();
		for(Point p : s){
			if(p.x() < min){min = p.x();}
			if(p.x() > max){max = p.x();}
		}
		return max-min;
	}

	/**
	 * Gibt die Hoehe des Blobs als int zurück.
	 */
	public int height() {
		int max = s.first().y(),min = s.first().y();
		for(Point p : s){
			if(p.y() < min){min = p.y();}
			if(p.y() > max){max = p.y();}
		}
		return max-min;
	}

	/**
	 * Gibt den Hashcode des Blobs als int zurück.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	/**
	 * Prüft die Wertgleichheit des Blobs mit einem anderen Objekt.
	 * 
	 * param obj: Zu vergleichendes Objekt.
	 * return: Gibt true bei Wertgleichheit zurück, ansonsten false.
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
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
	
	public boolean contains(Point p){
		if(p == null) return false;
		return s.contains(p);
	}

}
