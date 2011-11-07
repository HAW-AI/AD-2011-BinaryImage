package adp2.implementations;

public class Matrix {
	final private int width;
	final private int height;
	final private int[] values;
	
	public Matrix(int width, int height, int[] values) {
		super();
		if ((width * height) != values.length) {
			throw new IllegalArgumentException();
		}
		this.width = width;
		this.height = height;
		this.values = values;
	}
	
	private int getWidth() {
		return width;
	}

	private int getHeight() {
		return height;
	}

	
	public int get(int x, int y) {
		if (x < 0 || x > this.getWidth() || y < 0 || y > this.getHeight()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return values[x + (y*getWidth())];
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				result.append(get(x,y));
			}
			result.append("\n");
		}
		return result.toString();
	}
}
