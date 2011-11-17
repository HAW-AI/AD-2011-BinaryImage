package adp2.methodTests;

import static adp2.application.EsserParser.parse;
import static adp2.implementations.BinaryImages.NaB;
import static adp2.implementations.BinaryImages.blob;
import static adp2.implementations.BinaryImages.point;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public class AxiomsTest {
	BinaryImage Bi = (parse("test/fixtures/32x32.esser"));
	Point p1 = point(3, 1);
	Blob b1 = blob(Arrays.asList(p1, point(4, 1), point(3, 2)), Bi);
	Set<Point> es = new TreeSet<Point>();
	Blob nab1 = NaB();
	
	@Test 
	 public void testBlobAxioms() { 
	  // points(NaB) = {} 
	  assertEquals(nab1.iterator().hasNext(), es.iterator().hasNext()); 
	  // width(NaB) = height(NaB) = 0 
	  assertEquals(true, nab1.width() == nab1.height() && nab1.height() == 0); 
	  // Blob != NaB => (width(Blob) < 0) ^ (height(Blob) < 0) ^ (points(Blob) 
	  // != {}) 
	  assertEquals(!nab1.equals(b1), ((b1.width() > 0) && (b1.height() > 0) && (b1.iterator().hasNext()))); 
	  assertEquals(-1, nab1.circularity(), 0.0); 
	  assertEquals(true, b1.circularity() > 0 && b1.circularity() <= 1);       
	 }
}