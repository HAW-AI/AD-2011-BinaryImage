package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import adp2.interfaces.*;

public abstract class AbstractBinaryImage extends AbstractMatrix implements BinaryImage {
	protected final List<Blob> blobs;
	protected final List<Point> points;
	protected final SortedSet<Point> pointsSet;
	protected final boolean isEightNbr;		//true if eightNeighborBinaryImage, false if fourNeighborBinaryImage

	protected AbstractBinaryImage(int width, int height, List<Integer> values,boolean isEightNbr) {
		super(width, height, values);
		this.isEightNbr = isEightNbr;
		this.points = wrapList(values);
		pointsSet=new TreeSet<Point>();
		for (Point point : points) {
			if(!(point instanceof NaP))
				pointsSet.add(point);
		}
		this.blobs = calcBlobs(poitsAsSet());
	}
	public List<Point> wrapList(List<Integer> values){
		List<Point> wrapper=new ArrayList<Point>();
		for (int y=0;y<height();y++) {
			for (int x = 0; x < width(); x++) {
				if(get(x, y)>0)
					wrapper.add(BinaryImages.point(x,y));
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
	 * berechnet die Punkte, die nicht zu Blobs gehoeren ("die False-Punkte")
	 * 
	 **/
	protected List<Point> inversePoints() {
		 List<Point> l= new ArrayList<Point>();
		 for (Point p: points){
		 	if (p instanceof NaP){
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
	protected abstract List<Blob> calcBlobs(Set<Point> points) ;

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
		return neighbours(point, poitsAsSet());
	}
	protected Set<Point> poitsAsSet(){
		return pointsSet;
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
}
