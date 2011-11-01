package adp2.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 */
public class EsserParser {
	private static final String LINE_DELIMITER = "\n";
	private static final String ELEMENT_DELIMITER = " ";
	
	public static EsserImage parse(String file) {
		return EsserImage.valueOf(parse_array(body(content(file)))); 
	}

	/**
	 * parse a line into boolean array
	 * @param line
	 * @return boolean array
	 */
	private static boolean[] elements(String line) {
		final String[] elements = line.split(ELEMENT_DELIMITER);
		boolean[] result = new boolean[elements.length];
		int counter = 0;
		for (String element : elements) {
			result[counter] = Boolean.parseBoolean(element);
			counter++; // never ever touch this line, else this shit will break
		}
		return result;
	}
	
	/**
	 * split string into string array by line breaks
	 * @param body
	 * @return string array
	 */
	private static String[] lines(String body) {
		return body.split(LINE_DELIMITER);
	}

	/**
	 * build "physical" image out of an String 2d array
	 * @param body String representation of an 2d array
	 */
	private static boolean[][] parse_array(String body) {
		boolean[][] result = new boolean[lines(body).length][];
		int counter = 0;
		for (String line : lines(body)) {
			result[counter] = elements(line);
			counter++; // never ever touch this line, else this shit will break
		}
		return result;
	}

	/**
	 * return the acsii image string, without header
	 * @param file
	 */
	private static String body(String[] content) {
		StringBuilder result = new StringBuilder();
		for (String line : Arrays.copyOfRange(content, 1, content.length)) {
			result.append(line);
		}
		return result.toString();
	}
	
	/**
	 * returns the file content as String
	 * @param file
	 * @return
	 */
	private static String[] content(String filename) {
		final BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(filename)));
		} catch (FileNotFoundException e) {
			return new String[]{""};
		}
		List<String> lines = new ArrayList<String>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			// too bad, leve lines as it is
		}
		
		return lines.toArray(new String[0]);
	}

}

