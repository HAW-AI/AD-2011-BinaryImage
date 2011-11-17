package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Point;

public final class NaS implements BoundarySequence{
	
	private final static NaS instance = new NaS();

	public BoundarySequence valueOf(){
		return instance;
	}
	
	@Override
	public Point getStartPoint() {
		return PointImpl.valueOf(-1, -1);
	}

	@Override
	public List<Integer> getSequence() {
		return new ArrayList<Integer>();
	}

	@Override
	public Blob createBlob() {
		return ;
	}

	
}
