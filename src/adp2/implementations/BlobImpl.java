package adp2.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import adp2.interfaces.*;

public class BlobImpl implements Blob {

	private final TreeSet<Point> s;
	
	public static Blob valueOf(Collection<Point> s){
		return new BlobImpl(s);
	}
	
	private BlobImpl(Collection<Point> s){
		this.s = new TreeSet<Point>(s);
	}
	
	public Iterator<Point> iterator() {
		return this.s.iterator();
	}

	public int pointCount() {
		return this.s.size();
	}

	public int width() {
		int max = s.first().x(),min = s.first().x();
		for(Point p : s){
			if(p.x() < min){min = p.x();}
			if(p.x() > max){max = p.x();}
		}
		return max-min;
	}

	public int height() {
		int max = s.first().y(),min = s.first().y();
		for(Point p : s){
			if(p.y() < min){min = p.y();}
			if(p.y() > max){max = p.y();}
		}
		return max-min;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

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

}
