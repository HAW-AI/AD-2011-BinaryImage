package adp2.label;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultipassTest {

//	@Test
//	public void testWithEmptyMatrix() {
//		boolean[][] testMatrix={{}};
//		int testHeight=0;
//		int testWidth=0;
//		
//		int[][] expectedMatrix={{}};
//		assertArrayEquals(expectedMatrix, Multipass.labelledMatrix4(testWidth, testHeight, testMatrix));
//		assertArrayEquals(expectedMatrix, Multipass.labelledMatrix8(testWidth, testHeight, testMatrix));
//	}

	@Test
	public void testWithNonemptyMatrix() {
		boolean[][] testMatrix = {
				{ false, false, false, true, true, false, false, false, true,
						false, true, false },
				{ true, true, true, false, true, true, true, true, false,
						false, false, true },
				{ true, true, true, false, true, false, false, true, false,
						false, true, false },
				{ true, false, false, false, false, false, false, false, false,
						true, false, false } };
		int testHeight = 4;
		int testWidth = 12;

		int[][] expectedMatrix4={{0,0,0,1,1,0,0,0,2,0,3,0},{4,4,4,0,1,1,1,1,0,0,0,5},{4,4,4,0,1,0,0,1,0,0,6,0},{4,0,0,0,0,0,0,0,0,7,0,0}};
		int[][] expectedMatrix8={{0,0,0,1,1,0,0,0,1,0,3,0},{1,1,1,0,1,1,1,1,0,0,0,3},{1,1,1,0,1,0,0,1,0,0,3,0},{1,0,0,0,0,0,0,0,0,3,0,0}};
		assertArrayEquals(expectedMatrix4, Multipass.labelledMatrix4(testWidth, testHeight, testMatrix));
		assertArrayEquals(expectedMatrix8, Multipass.labelledMatrix8(testWidth, testHeight, testMatrix));
	}

}
