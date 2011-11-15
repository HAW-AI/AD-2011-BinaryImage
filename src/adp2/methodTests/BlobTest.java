package adp2.methodTests;

import static adp2.implementations.BinaryImages.*;
import static adp2.application.EsserParser.parse;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import adp2.implementations.BlobImpl;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public class BlobTest {
	Point p1 = point(3,1);
	Point p2 = point(5,6);
	List<Point> l1 = Arrays.asList(p1,point(4,1),point(3,2));
	List<Point> l2 = Arrays.asList(p2,point(6,5),point(6,6));
	List<Point> l3 = Arrays.asList(p2,point(6,5),point(6,6));
	BinaryImage Bi = (parse("test/fixtures/32x32.esser"));
	Blob b1 = blob(l1, Bi);
	Blob b2 = blob(l2, Bi);
	Blob b3 = blob(l3, Bi);

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
	
	@Test
	public void testBoundary() {
		BinaryImage Bi = (parse("test/fixtures/32x32.esser"));
		for(int i = 0; i< 1000; i++)
		assertEquals(24,Bi.blob(0).boundary().size());
		//Problemblob
		assertEquals(5, Bi.blob(8).boundary().size());
		
		System.out.println(Bi.blob(0).boundary2());
		
		for(int i=1; Bi.blob(i) instanceof BlobImpl && i<1000; ++i){
			System.out.println(Bi.blob(i).boundary2());
		}
		
	}

         @Test
	public void testCircularity() {
		BinaryImage Bi = (parse("test/fixtures/32x32.esser"));
		assertEquals(4 * Math.PI / 16,Bi.blob(1).circularity(), 0.001);
		assertEquals(4 * Math.PI * 4 / 100,Bi.blob(2).circularity(), 0.001);
	}
}
