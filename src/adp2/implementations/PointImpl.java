package adp2.implementations;

import adp2.interfaces.Point;

public class PointImpl implements Point {

    private final int x;
    private final int y;

    private PointImpl(int x, int y){
	this.x=x;
	this.y=y;
    }
	
    /**
     * compares two Points
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @param the point, to which the caller is compared to
     * @return a Integer to represent whether the Caller-Point is Greater, even or Smaller than the Param-Point 
     */
    @Override
    public int compareTo(Point other) {
	int xCompare=Integer.valueOf(this.x).compareTo(Integer.valueOf(other.x()));
	if(xCompare==0) return Integer.valueOf(this.y).compareTo(Integer.valueOf(other.y()));
	else return xCompare;
    }

    /**
     * getter for the X-Value of a Point
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @return a Integer representing the X-Value of a Point 
     */
    @Override
    public int x() {
	return this.x;
    }

    /**
     * getter for the Y-Value of a Point
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @return a Integer representing the Y-Value of a Point 
     */
    @Override
    public int y() {
	return this.y;
    }

    /**
     * creates a Point
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @param Integer x: X-Coordinate of the Point
     * @param Integer y: Y-Coordinate of the Point
     * 
     * @return a Point with given x/y-Coordinates
     */
    static PointImpl valueOf(int x, int y){
	return new PointImpl(x,y);
    }

    /**
     * creates the Hashcode
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @return a Integer representing the Hashcode 
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + x;
	result = prime * result + y;
	return result;
    }

    /**
     * checks two Points for equality
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @param the point, to which the caller is checked for equalitty to
     * @return a Boolean to represent whether the Caller-Point Equal to the param-Point or not 
     */
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

    /**
     * creates a String-Representation of a Point
     * 
     * @author Sebastian Krome
     * @author Andreas Wimmer
     * 
     * @return a String with the X- and Y-Coordinates of a given Point 
     */
    public String toString(){
//	return "Point: x=" + x + " " + "y= " + y;
    	return "P("+x+","+y+")";
    }

}
