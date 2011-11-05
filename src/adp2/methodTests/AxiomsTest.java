package adp2.methodTests;

import static adp2.implementations.BinaryImages.*;
import static adp2.application.EsserParser.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import adp2.implementations.BinaryImages;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public class AxiomsTest {
	BinaryImage Bi = BinaryImages.binaryImage(parse("test/fixtures/32x32.esser"));
	Point p1 = point(3,1);
	Blob b1 = blob(Arrays.asList(p1,point(4,1),point(3,2)),Bi);
	Set<Point> es = new TreeSet<Point>();
	Blob nab1 = NaB();
	
	@Test
	public void testBlobAxioms() {
		//points(NaB) = {}
		assertEquals(nab1.iterator().hasNext(),es.iterator().hasNext());
		//width(NaB) = height(NaB) = 0
		assertTrue(nab1.width() == nab1.height() && nab1.height() == 0);
		//Blob != NaB => (width(Blob) < 0) ^ (height(Blob) < 0) ^ (points(Blob) != {})
		assertEquals(!nab1.equals(b1),((b1.width() > 0) && (b1.height() > 0) && (b1.iterator().hasNext())));
	}

}