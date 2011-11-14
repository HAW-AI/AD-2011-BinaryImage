package adp2.implementations;

import java.util.List;

import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Point;

public class BoundarySequenceImpl implements BoundarySequence {

	List<Integer> sequence;
	Point point;
	
	public BoundarySequenceImpl(Point point, List<Integer> list) {
		this.sequence = list;
		this.point = point;
	}
	
	@Override
	public Point getStartPoint() {
		// TODO Auto-generated method stub
		return point;
	}

	@Override
	public List<Integer> getSequence() {
		// TODO Auto-generated method stub
		return sequence;
	}

}
