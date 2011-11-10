package adp2.application;

import adp2.implementations.BinaryImages;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Matrix;

public class TestTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] files = {
				"test/fixtures/2x2-einsen.esser",
				"test/fixtures/4x4-einsen.esser",
				"test/fixtures/8x8-einsen.esser",
				"test/fixtures/16x16-einsen.esser",
				"test/fixtures/32x32-einsen.esser",
				"test/fixtures/64x64-einsen.esser",

				};
		
		long start = 0;
		
		for (int i = 0; i < 1; i++) {
			for (String file : files) {
				start = System.currentTimeMillis();
				BinaryImage image = EsserParser.parse(file);
				System.out.println(image.blobCount());
				System.out.println(String.format("Runtime in MS: %s (file: %s)", System.currentTimeMillis() - start, file));
			}
		}
	}
}
