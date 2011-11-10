package adp2.methodTests;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static adp2.implementations.BinaryImages.*;
import adp2.application.EsserParser;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;
import static java.util.Arrays.asList;
public class BinaryImageTest {
	List<Boolean> l1, l2, l3, l4, l5;
	List<List<Boolean>> list;
	BinaryImage b4, b8, b;
	Point p, p1, p2, p3;
	
	@Before
	public void setUp(){
		l1 = new ArrayList<Boolean>(asList(true, false, false,true, false));
		l2 = new ArrayList<Boolean>(asList(true, false, false,true, false));
		l3 = new ArrayList<Boolean>(asList(false, true, false,true, true));
		l4 = new ArrayList<Boolean>(asList(true, false, false,false, false));
		l5 = new ArrayList<Boolean>(asList(true, false, false,true, true));
		list = new ArrayList<List<Boolean>>();
		list.add(l1);
		list.add(l2);
		list.add(l3);
		list.add(l4);
		list.add(l5);
		b4 = fourNeighborBinaryImage(list);
		b8 = eightNeighborBinaryImage(list);
		b = (EsserParser.parse("test/fixtures/32x32.esser"));
		
		p = point(0,0);
		p1 = point(0,1);
		p2 = point(1,2);
		p3 = point(0,3);	
	}
	
	@Test 
	public void testInverse(){
		assertEquals(1,b4.inverse().blob(0).points().size());
		assertEquals(11,b4.inverse().blob(1).points().size());
		assertEquals(2,b4.inverse().blob(2).points().size());
		assertEquals(12,b8.inverse().blob(0).points().size());
		assertEquals(2,b8.inverse().blob(1).points().size());
	}
	@Test 
	public void testNeighbours(){
		assertEquals(1,b4.neighbours(p1).size());
		assertEquals(0,b4.neighbours(p2).size());
		assertEquals(2,b8.neighbours(p2).size());
	}
	
	@Test
	public void testConnected(){
		assertTrue(b4.connected(p, p1));
		assertFalse(b4.connected(p1, p2));
		assertTrue(b8.connected(p1, p2));		
	}@Test
	public void testValueAt(){
		assertTrue(b4.valueAt(p));
		assertTrue(b4.valueAt(p1));
		assertTrue(b4.valueAt(p3));
	}
	@Test
	public void testBlobCount(){
		assertEquals(12, b.blobCount());
		assertEquals(4, b.toEigthNeighborBinaryImage().blobCount());
		assertEquals(2, b4.blob(0).points().size());
		assertEquals(2, b4.blob(1).points().size());
		assertEquals(1, b4.blob(2).points().size());
		assertEquals(0, b4.blob(9).points().size());
		assertEquals(5, b8.blob(0).points().size());
		assertEquals(4, b8.blob(1).points().size());
		assertEquals(2, b8.blob(2).points().size());
		assertEquals(0, b8.blob(9).points().size());
		
		assertEquals(5,b4.blobCount());
		assertEquals(3,b8.blobCount());
	}
	
}
