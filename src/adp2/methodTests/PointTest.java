package adp2.methodTests;


import static adp2.implementations.BinaryImages.point;
import adp2.interfaces.Point;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest{
	Point p1 = point(3,1); 
	Point p2 = point(3,1);
	Point p3 = point(4,1);
	Point p4 = point(3,2);
	
	@Test
	public void testHashCode(){
		assertTrue(p1.hashCode() == p1.hashCode());
		assertTrue(p1.hashCode() == p2.hashCode());
		assertFalse(p1.hashCode() == p3.hashCode());
		
	}
	@Test
	public void testGetter(){
		assertEquals(3,p1.x());
		assertEquals(1,p1.y());
	}
	@Test
	public void testCompareTo(){
		assertEquals(0,p1.compareTo(p1));
		assertEquals(0,p1.compareTo(p2));
		assertEquals(-1,p1.compareTo(p3));
		assertEquals(-1,p1.compareTo(p4));
		
	}
	@Test
	public void testEquals(){
		assertTrue(p1.equals(p2));
		assertFalse(p1.equals(p3));
		assertFalse(p1.equals(null));
		assertTrue(p1.equals(p1));
		
	}
}
