package adp2.application;

public class EsserImage {
	private final Integer width;
	private final Integer height;
	private final boolean[][] pixels;
	
	private EsserImage(Integer width, Integer height, boolean[][] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public static EsserImage valueOf(boolean[][] array) {
		return new EsserImage(array[0].length, array.length, array);
	}
}
