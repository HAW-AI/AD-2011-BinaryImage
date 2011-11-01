package adp2.implementations;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public abstract class AbstractBinaryImage implements BinaryImage {
    private final List<Blob> blobs;
    private final Set<Point> points;
    
    protected AbstractBinaryImage(List<List<Boolean>> shape) {
        // pre-conditions are checked in the BinaryImages factory
    	points = matrixToPointSet(shape);
        blobs = calcBlobs(points);
    }
    /**
     * Delegates the calculation of Blobs to the preferred algorithm
     * 
     * @author Oliver Behncke
     * 
     * @param Set of points as representation of the image
     * @return Ordered list of Blobs
     */
    private List<Blob> calcBlobs(Set<Point> points) {
        return deepSearch(points);
    }

    /**
     * DeepSearch algorithm to find blobs. This method delegates to a recursive method in order to find all blobs for the points not already visited
     * 
     * @author Oliver Behncke
     * 
     * @param Set of points as representation of the image
     * @return Ordered list of blobs 
     */
    private List<Blob> deepSearch(Set<Point> points) {
    	List<Blob> result=new ArrayList<Blob>();
    	Set<Point> visited=new TreeSet<Point>();
		for(Point p : points){
			if(!visited.contains(p)){
				Set<Point> blobAsSet=deepSearch_(p, visited, points);
				result.add(BinaryImages.blob(blobAsSet));
			}
		}
		return result;
	}
    
    /**
     * Add the neighbours of all neighbours of a point, which haven't been visited before.
     * 
     * @author Oliver Behncke
     * 
     * @param the point, at which the DeepSearch is currently at
     * @param a Set of already visited points in order to avoid an infinite regress
     * @param Set of points as representation of the image
     * @return a Set of points as representation of a blob
     */
    private Set<Point> deepSearch_(Point p, Set<Point> visited, Set<Point> points){
		Set<Point> result=new TreeSet<Point>();
		result.add(p);
		visited.add(p);
		Set<Point> neighbours=this.neighbours(p, points);
		neighbours.removeAll(visited);
		for(Point neighbour: neighbours){
			result.addAll(deepSearch_(neighbour, visited, points));
		}
		return result;
    }

	private SortedSet<Point> matrixToPointSet(List<List<Boolean>> shape) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public Iterator<Blob> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int blobCount() {
        return this.blobs.size();
    }

    @Override
    public Blob blob(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Blob> blobs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int width() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int height() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Set<Point> neighbours(Point point) {
        return neighbours(point, this.points);
    }

    /**
     * Get neighbours for a specific point. Results depend on how the areNeighbours method is implemented in concrete class
     * 
     * @author Oliver Behncke
     * 
     * @param Point
     * @param Set of potential neighbour points
     * @return
     */
	protected Set<Point> neighbours(Point point, Set<Point> points) {
		Set<Point> result=new TreeSet<Point>();
		for(Point other: points){
			if(areNeighbours(point, other)){
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
     * @param Other Point
     * @return
     */
    protected boolean areNeighbours4n(Point p1, Point p2){
    	return (Math.abs(p1.x()-p2.x())==1) && (Math.abs(p1.y()-p2.y())==0) || (Math.abs(p1.x()-p2.x())==0) && (Math.abs(p1.y()-p2.y())==1);
    }
    
    /**
     * Helper method to find out, if two points are neighbours (8 neighbourhood)
     * 
     * @author Oliver Behncke
     * 
     * @param Point
     * @param Other Point
     * @return
     */
    protected boolean areNeighbours8n(Point p1, Point p2){
    	return areNeighbours4n(p1,p2) || ((Math.abs(p1.x()-p2.x())==1) && (Math.abs(p1.y()-p2.y())==1)) || ((Math.abs(p1.x()-p2.x())==1) && (Math.abs(p1.y()-p2.y())==1));
    }
    
	@Override
    public boolean valueAt(Point point) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean connected(Point point1, Point point2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BinaryImage inverse() {
        // TODO Auto-generated method stub
        return null;
    }

}
