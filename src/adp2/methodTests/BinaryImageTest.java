package adp2.methodTests;
import java.util.*;

import adp2.implementations.BinaryImages;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Point;
import static java.util.Arrays.asList;
public class AllgemeinTest {
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
		BinaryImage b4 = BinaryImages.fourNeighborBinaryImage(list);
		BinaryImage b8 = BinaryImages.eightNeighborBinaryImage(list);
		
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
		
		Point p = BinaryImages.point(0,3);
		Point p1 = BinaryImages.point(0,4);
		Point p2 = BinaryImages.point(0,0);
		System.out.println("Connected:\n");
		System.out.println(b4.connected(p, p1));
		System.out.println(b4.connected(p, p2));
		System.out.println(b8.connected(p1, p2));
		
	}
	
}
