package adp2.label;

public final class Multipass {
	private Multipass() {

	}

	public static int[][] labelledMatrix4(int width, int height,
			boolean[][] pixelMatrix) {
		return labelledMatrix(width, height,
				pixelMatrix, false);
	}
	
	public static int[][] labelledMatrix8(int width, int height,
			boolean[][] pixelMatrix) {
		return labelledMatrix(width, height,
				pixelMatrix, true);
	}

	private static int[][] labelledMatrix(int width, int height,
			boolean[][] pixelMatrix, boolean diagonalNeighbours) {
		// if (pixels.length == 0 || pixels[0].length == 0) {
		// return null;
		// }


		int[][] labels = new int[height][width];

		//boolean diagonalNeighbours = true;

		int label = 1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!pixelMatrix[y][x]) {
					labels[y][x] = 0;
					// System.out.println("x=" + x + " y=" + y + " label=" + 0);
				} else {
					boolean noLabelledNeighbours = !pixelMatrix[y - 1][x]
							&& !pixelMatrix[y][x - 1];
					if (diagonalNeighbours) {
						noLabelledNeighbours = noLabelledNeighbours
								&& !pixelMatrix[y - 1][x - 1]
								&& !pixelMatrix[y - 1][x + 1];
					}
					if (noLabelledNeighbours) {
						// System.out.println("x=" + x + " y=" + y + " label=" +
						// label);
						labels[y][x] = label++;
					} else {
						int min = min(labels[y - 1][x], labels[y][x - 1]);
						if (diagonalNeighbours) {
							min = min(min, labels[y - 1][x - 1],
									labels[y - 1][x + 1]);
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
						int min = min(labels[y - 1][x], labels[y][x - 1],
								labels[y][x + 1], labels[y + 1][x + 1]);
						if (diagonalNeighbours) {
							min = min(min, labels[y - 1][x - 1],
									labels[y - 1][x + 1], labels[y + 1][x - 1],
									labels[y + 1][x + 1]);
						}

						if (min != 0) {
							labels[y][x] = min;
							labelChanged = true;
							// System.out.println("ch: x=" + x + " y=" + y +
							// " label=" +min);
						}
					}
				}
			}
		} while (!labelChanged);

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

	private static int min(Integer... values) {
		int min = 0;
		for (Integer value : values) {
			if (value != null && value != 0) {
				if (min == 0) {
					min = value;
				} else {
					min = Math.min(min, value);
				}
			}
		}
		return min;
	}
}
