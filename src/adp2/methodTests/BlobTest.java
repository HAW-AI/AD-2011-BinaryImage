package adp2.methodTests;

import static adp2.implementations.BinaryImages.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public class BlobTest {
	Point p1 = point(3,1);
	Point p2 = point(5,6);
	List<Point> l1 =  Arrays.asList(p1,point(4,1),point(3,2));
	List<Point> l2 =  Arrays.asList(p2,point(6,5),point(6,6));
	List<Point> l3 =  Arrays.asList(p2,point(6,5),point(6,6));
	
	Blob b1 = blob(l1);
	Blob b2 = blob(l2);
	Blob b3 = blob(l3);

	@Test
	public void testPointCount() {
		assertEquals(b1.pointCount(),b2.pointCount());
		assertEquals(b2.pointCount(),b3.pointCount());
		assertEquals(b1.pointCount(),b3.pointCount());
	}
	@Test
	public void testPoints() {
		assertTrue(b1.points().containsAll(l1));
		assertTrue(b2.points().containsAll(l2));
		assertTrue(b3.points().containsAll(l3));
	}
	@Test
	public void testWidth() {
		assertEquals(b1.width(),b2.width());
		assertEquals(b2.width(),b3.width());
		assertEquals(b1.width(),2);
		assertEquals(b2.width(),2);
	}
	@Test
	public void testHeigth() {
		assertEquals(b1.height(),b2.height());
		assertEquals(b2.height(),b3.height());
		assertEquals(b1.height(),2);
		assertEquals(b2.height(),2);
	}
	@Test
	public void testEquals() {
		assertFalse(b1.equals(b2));
		assertTrue(b1.equals(b1));
		assertTrue(b2.equals(b3));
	}
	@Test
	public void testContains() {
		assertTrue(b1.contains(p1));
		assertTrue(b2.contains(p2));
		assertTrue(b1.contains(point(3,1)));
		assertTrue(b2.contains(point(5,6)));
	}
	@Test
	public void testHashCode() {
		assertTrue(b1.hashCode() == b1.hashCode());
		assertTrue(b2.hashCode() == b2.hashCode());
		assertTrue(b3.hashCode() == b3.hashCode());
	}

}
