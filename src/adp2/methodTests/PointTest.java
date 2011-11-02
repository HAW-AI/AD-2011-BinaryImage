package adp2.methodTests;


import static adp2.implementations.BinaryImages.point;
import adp2.interfaces.Point;

public class PointTest {
	public static void main(String args[]){
		Point p1 = point(3,1); 
		Point p2 = point(3,1);
		Point p3 = point(4,1);
		Point p4 = point(3,2);
		
		System.out.println("Equals: \n");
		System.out.println(p1.equals(p2));
		System.out.println(p1.equals(p3));
		System.out.println(p1.equals(null));
		System.out.println(p1.equals(p1));
		
		System.out.println("compare:\n");
		System.out.println(p1.compareTo(p1));
		System.out.println(p1.compareTo(p2));
		System.out.println(p1.compareTo(p3));
		System.out.println(p1.compareTo(p4));
		
		System.out.println("Getter:\n");
		System.out.println(p1.x());
		System.out.println(p1.y());
		
		System.out.println("Hashcode:\n");
		System.out.println(p1.hashCode() == p1.hashCode());
		System.out.println(p1.hashCode() == p2.hashCode());
		System.out.println(p1.hashCode() == p3.hashCode());
		
		
	}
}
