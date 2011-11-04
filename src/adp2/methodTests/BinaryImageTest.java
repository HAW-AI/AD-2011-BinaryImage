package adp2.methodTests;
import java.util.*;

import static adp2.implementations.BinaryImages.*;
import adp2.application.EsserParser;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;
import static java.util.Arrays.asList;
public class BinaryImageTest {
	
	public static void main(String args[]){
		
		List<Boolean> l1 = new ArrayList<Boolean>(asList(true, false, false,true, false));
		List<Boolean> l2 = new ArrayList<Boolean>(asList(true, false, false,true, false));
		List<Boolean> l3 = new ArrayList<Boolean>(asList(false, true, false,true, true));
		List<Boolean> l4 = new ArrayList<Boolean>(asList(true, false, false,false, false));
		List<Boolean> l5 = new ArrayList<Boolean>(asList(true, false, false,true, true));
		List<List<Boolean>> list = new ArrayList<List<Boolean>>();
		list.add(l1);
		list.add(l2);
		list.add(l3);
		list.add(l4);
		list.add(l5);
		BinaryImage b4 = fourNeighborBinaryImage(list);
		BinaryImage b8 = eightNeighborBinaryImage(list);
		BinaryImage b = binaryImage(EsserParser.parse("test/fixtures/32x32.esser"));

		System.out.println("Blobcount b:\n");
		System.out.println(b.blobCount());
		
		System.out.println("b4- blob(i):\n");
		System.out.println(b4.blob(0).points());
		System.out.println(b4.blob(1).points());
		System.out.println(b4.blob(2).points());
		System.out.println(b4.blob(9).points());
		
		System.out.println("b8- blob(i):\n");
		System.out.println(b8.blob(0).points());
		System.out.println(b8.blob(1).points());
		System.out.println(b8.blob(2).points());
		System.out.println(b8.blob(9).points());
		
		System.out.println("Blobcount:\n");
		System.out.println(b4.blobCount());
		System.out.println(b8.blobCount());
		
		Point p = point(0,3);
		Point p1 = point(0,4);
		Point p2 = point(0,0);
		Point p3 = point(1,0);
		
		System.out.println("Connected:\n");
		System.out.println(b4.connected(p, p1));
		System.out.println(b4.connected(p, p2));
		System.out.println(b8.connected(p1, p2));
		
		System.out.println("valueAt:\n");
		System.out.println(b4.valueAt(p));
		System.out.println(b4.valueAt(p1));
		System.out.println(b4.valueAt(p3));
		
		System.out.println("neighbours:\n");
		System.out.println(b4.neighbours(p1));
		System.out.println(b4.neighbours(p));
		
		System.out.println("inverse 4n:\n");
		System.out.println(b4.inverse().blob(0).points());
		System.out.println(b4.inverse().blob(1).points());
		System.out.println(b4.inverse().blob(2).points());
		System.out.println(b4.inverse().blob(3).points());
		System.out.println(b4.inverse().blob(4).points());
		
		System.out.println("inverse 8n:\n");
		System.out.println(b8.inverse().blob(0).points());
		System.out.println(b8.inverse().blob(1).points());
		System.out.println(b8.inverse().blob(2).points());
		System.out.println(b8.inverse().blob(3).points());
		System.out.println(b8.inverse().blob(4).points());
		
		System.out.println(b8.blobCount());
		System.out.println(b8.blob(0).binaryImage());
	}
	
	
}
