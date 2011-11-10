package adp2.implementations;

import adp2.interfaces.Point;

public abstract class AbstractPoint implements Point{
	private final int x;
    private final int y;

    protected AbstractPoint(int x, int y){
		this.x=x;
		this.y=y;
    }
    @Override
    public int x() {
	return this.x;
    }

    @Override
    public int y() {
	return this.y;
    }

    @Override
    public int compareTo(Point other) {
	int xCompare=Integer.valueOf(this.x()).compareTo(Integer.valueOf(other.x()));
	if(xCompare==0) return Integer.valueOf(this.y()).compareTo(Integer.valueOf(other.y()));
	else return xCompare;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + x;
	result = prime * result + y;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
            return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Point))
	    return false;
	Point other = (Point) obj;
	    return compareTo(other) == 0;
    }

    public String toString(){
    	return "P("+x+","+y+")";
    }
}
