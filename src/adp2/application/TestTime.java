package adp2.application;

import adp2.implementations.BinaryImages;

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
		
		for (int i = 0; i < 5; i++) {
			for (String file : files) {
				EsserImage image = EsserParser.parse(file);
				start = System.currentTimeMillis();
				System.out.println(BinaryImages.binaryImage(image).blobCount());
				System.out.println(String.format("Runtime in MS: %s (file: %s)", System.currentTimeMillis() - start, file));
			}
		}
	}

}
