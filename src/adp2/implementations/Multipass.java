package adp2.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import adp2.interfaces.Blob;
import adp2.interfaces.Point;
import static adp2.implementations.BinaryImages.point;

public final class Multipass {
	private Multipass() {

	}

	static List<Blob> blobs4(int width, int height,
			boolean[][] pixelMatrix) {
		return blobs(width, height, pixelMatrix, false);
	}

	static List<Blob> blobs8(int width, int height,
			boolean[][] pixelMatrix) {
		return blobs(width, height, pixelMatrix, true);
	}

	private static List<Blob> blobs(int width, int height,
			boolean[][] pixelMatrix, boolean diagonalNeighbours) {
		// if (pixels.length == 0 || pixels[0].length == 0) {
		// return null;
		// }
		Map<Point, Integer> labels = new HashMap<Point, Integer>();

		// boolean diagonalNeighbours = true;

		int label = 1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!pixelMatrix[y][x]) {
					labels.put(point(x, y), 0);
//					System.out.println("x=" + x + " y=" + y + " label=" + 0);
				} else {
					boolean noForegroundNeighbours = !pixel(pixelMatrix, x, y - 1)
							&& !pixel(pixelMatrix, x - 1, y);
					if (diagonalNeighbours) {
						noForegroundNeighbours = noForegroundNeighbours
								&& !pixel(pixelMatrix, x - 1, y - 1)
								&& !pixel(pixelMatrix, x + 1, y - 1);
					}
					if (noForegroundNeighbours) {
//						 System.out.println("x=" + x + " y=" + y + " label=" +
//						 label);
						labels.put(point(x, y), label++);
					} else {
						int min = min(labels.get(point(x, y - 1)),
								labels.get(point(x - 1, y)));
						if (diagonalNeighbours) {
							min = min(min, labels.get(point(x - 1, y - 1)),
									labels.get(point(x + 1, y - 1)));
						}
						labels.put(point(x, y), min);
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
						int min = min(labels.get(point(x, y - 1)),
								labels.get(point(x - 1, y)),
								labels.get(point(x, y)),
								labels.get(point(x + 1, y)),
								labels.get(point(x, y + 1)));
						if (diagonalNeighbours) {
							min = min(min, labels.get(point(x - 1, y - 1)),
									labels.get(point(x + 1, y - 1)),
									labels.get(point(x - 1, y + 1)),
									labels.get(point(x + 1, y + 1)));
						}
						if (min != labels.get(point(x, y))) {
							labels.put(point(x, y), min);
							labelChanged = true;
//							 System.out.println("ch: x=" + x + " y=" + y +
//							 " label=" +min);
						}
					}
				}
			}
		} while (labelChanged);

//		for (int y=0; y<height; y++) {
//			for (int x=0; x<width; x++) {
//				System.out.print(labels.get(point(x,y)));
//			}
//			System.out.println();
//		}
//		System.out.println("end");
		
		
		List<Blob> blobs = new ArrayList<Blob>();
		Set<Integer> remainingLabels = new HashSet<Integer>(labels.values());
		remainingLabels.remove(0);
		for (int currentLabel : remainingLabels) {
			SortedSet<Point> blobPoints = new TreeSet<Point>();
			for (Point point : blobPoints) {
				if (labels.get(point) == currentLabel) {
					blobPoints.add(point);
				}
			}
			blobs.add(adp2.implementations.BinaryImages.blob(blobPoints));
		}
		
		return blobs;

		// return labels;
	}

	private static boolean pixel(boolean[][] pixelMatrix, int x, int y) {
		boolean pixel = false;
		try {
			pixel = pixelMatrix[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return pixel;
	}

//	private static int label(int[][] labelMatrix, int x, int y) {
//		int label = 0;
//		try {
//			label = labelMatrix[y][x];
//		} catch (ArrayIndexOutOfBoundsException e) {
//		}
//		return label;
//	}

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

//	public static Collection<Blob> blob4(int width, int height,
//			boolean[][] pixelMatrix) {
//		int[][] labelledMatrix = labelledMatrix4(width, height, pixelMatrix);
//		return null;
//	}
//
//	private Collection<Blob> blobs(int[][] labelledMatrix) {
//		List<Blob> blobs = new ArrayList<Blob>();
//		Set<Integer> remainingLabels = new HashSet(labels.values());
//		remainingLabels.remove(0);
//		for (int currentLabel : remainingLabels) {
//			SortedSet<Pixel> blobPixels = new TreeSet<Pixel>();
//			for (Pixel pixel : pixels) {
//				if (labels.get(pixel) == currentLabel) {
//					blobPixels.add(pixel);
//				}
//			}
//			blobs.add(adp2.adt.BinaryBlobz.blob(8, blobPixels));
//		}
//
//		return blobs;
//
//	}
//	 final static boolean[][] testImage = {
//	 { false, false, false, true, true, false, false, false, true,
//	 false, true, false },
//	 { true, true, true, false, true, true, true, true, false, false,
//	 false, true },
//	 { true, true, true, false, true, false, false, true, false, false,
//	 true, false },
//	 { true, false, false, false, false, false, false, false, false,
//	 true, false, false } };
//	 final static int testHeight = 4;
//	 final static int testWidth = 12;

	// final static boolean[][] testImage={{}};
	// final static int testHeight=0;
	// final static int testWidth=0;

//	 public static void main(String[] args) {
//	 blobs8(testWidth, testHeight, testImage);
	// for (int y = 0; y < testHeight; y++) {
	// for (int x = 0; x < testWidth; x++) {
	// System.out.print(labelMatrix[y][x]);
	// }
	// System.out.println();
	// }
//	}
}
