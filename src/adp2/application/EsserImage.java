package adp2.application;

/**
 * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 */
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

	public Integer width() {
		return width;
	}

	public Integer height() {
		return height;
	}

	public boolean[][] pixels() {
		return pixels;
	}
	
	public boolean isValid() {
		for (boolean[] e : pixels()) {
			if (e.length != width()) return false; 
		}
		return true;
	}
	
	public String toString() {
		return String.format("EsserImage[%sx%s,%s,%s]",width(), height(), isValid()?"valid":"invalid", pixels_as_string());
	}
	
	private String pixels_as_string() {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for (boolean[] e : pixels()) {
			result.append("[");
			for (boolean b : e) {
				result.append(b?"1":"0");
			}
			result.append("]");
		}
		return result.toString();
	}
}
