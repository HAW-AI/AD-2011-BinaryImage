package adp2.implementations;

import adp2.interfaces.Point;

public class PointImpl implements Point {

	private final int x;
	private final int y;
	
	private PointImpl(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public int compareTo(Point other) {
		int xCompare=Integer.valueOf(this.x).compareTo(Integer.valueOf(other.x()));
		if(xCompare==0) return Integer.valueOf(this.y).compareTo(Integer.valueOf(other.y()));
		else return xCompare;
	}

	@Override
	public int x() {
		return this.x;
	}

	@Override
	public int y() {
		return this.y;
	}
	
	static PointImpl valueOf(int x, int y){
		return new PointImpl(x,y);
	}

}
