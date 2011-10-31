package adp2.label;

public final class Multipass {
	private Multipass() {

	}

	public static int[][] labelledMatrix4(int width, int height,
			boolean[][] pixelMatrix) {
		return labelledMatrix(width, height, pixelMatrix, false);
	}

	public static int[][] labelledMatrix8(int width, int height,
			boolean[][] pixelMatrix) {
		return labelledMatrix(width, height, pixelMatrix, true);
	}

	private static int[][] labelledMatrix(int width, int height,
			boolean[][] pixelMatrix, boolean diagonalNeighbours) {
		if (pixelMatrix.length==0) return null;
		// if (pixels.length == 0 || pixels[0].length == 0) {
		// return null;
		// }
		int[][] labels = new int[height][width];

		// boolean diagonalNeighbours = true;

		int label = 1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!pixelMatrix[y][x]) {
					labels[y][x] = 0;
//					 System.out.println("x=" + x + " y=" + y + " label=" + 0);
				} else {
					boolean noLabelledNeighbours = !pixel(pixelMatrix, x, y - 1)
							&& !pixel(pixelMatrix, x - 1, y);
					if (diagonalNeighbours) {
						noLabelledNeighbours = noLabelledNeighbours
								&& !pixel(pixelMatrix, x - 1, y - 1)
								&& !pixel(pixelMatrix, x + 1, y - 1);
					}
					if (noLabelledNeighbours) {
						// System.out.println("x=" + x + " y=" + y + " label=" +
						// label);
						labels[y][x] = label++;
					} else {
						int min = min(label(labels, x, y - 1),
								label(labels, x - 1, y));
						if (diagonalNeighbours) {
							min = min(min, label(labels, x - 1, y - 1),
									label(labels, x + 1, y - 1));
						}
						labels[y][x] = min;
						// System.out.println("x=" + x + " y=" + y + " label=" +
						// labels.get(pixel(x, y)));
					}
				}
			}
		}

		boolean labelChanged = false;
		do {
			labelChanged = false;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (pixelMatrix[y][x]) {
						int min = min(label(labels, x, y - 1),
								label(labels, x - 1, y),
								label(labels, x, y),
								label(labels, x + 1, y),
								label(labels, x, y + 1));
						if (diagonalNeighbours) {
							min = min(min, label(labels, x - 1, y - 1),
									label(labels, x + 1, y - 1),
									label(labels, x - 1, y + 1),
									label(labels, x + 1, y + 1));
						}

						if (min != label(labels,x,y)) {
							labels[y][x] = min;
							labelChanged = true;
							// System.out.println("ch: x=" + x + " y=" + y +
							// " label=" +min);
						}
					}
				}
			}
		} while (labelChanged);

		// List<Blob> blobs = new ArrayList<Blob>();
		// Set<Integer> remainingLabels = new HashSet(labels.values());
		// remainingLabels.remove(0);
		// for (int currentLabel : remainingLabels) {
		// SortedSet<Pixel> blobPixels = new TreeSet<Pixel>();
		// for (Pixel pixel : pixels) {
		// if (labels.get(pixel) == currentLabel) {
		// blobPixels.add(pixel);
		// }
		// }
		// blobs.add(adp2.adt.BinaryBlobz.blob(8, blobPixels));
		// }
		//
		// return blobs;

		return labels;
	}

	private static boolean pixel(boolean[][] pixelMatrix, int x, int y) {
		boolean pixel = false;
		try {
			pixel = pixelMatrix[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return pixel;
	}

	private static int label(int[][] labelMatrix, int x, int y) {
		int label = 0;
		try {
			label = labelMatrix[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return label;
	}

	private static int min(int... values) {
		int min = 0;
		for (int value : values) {
			if (value != 0) {
				if (min == 0) {
					min = value;
				} else {
					min = Math.min(min, value);
				}
			}
		}
		return min;
	}

//	final static boolean[][] testImage = {
//			{ false, false, false, true, true, false, false, false, true,
//					false, true, false },
//			{ true, true, true, false, true, true, true, true, false, false,
//					false, true },
//			{ true, true, true, false, true, false, false, true, false, false,
//					true, false },
//			{ true, false, false, false, false, false, false, false, false,
//					true, false, false } };
//	final static int testHeight = 4;
//	final static int testWidth = 12;

//	final static boolean[][] testImage={{}};
//	final static int testHeight=0;
//	final static int testWidth=0;
	
//	public static void main(String[] args) {
//		int[][] labelMatrix = labelledMatrix4(testWidth, testHeight, testImage);
//		for (int y = 0; y < testHeight; y++) {
//			for (int x = 0; x < testWidth; x++) {
//				System.out.print(labelMatrix[y][x]);
//			}
//			System.out.println();
//		}
//	}
}
