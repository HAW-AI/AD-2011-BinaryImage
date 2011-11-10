package adp2.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Integer;
import java.lang.reflect.Array;
import adp2.implementations.*;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Matrix;

/**
 * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 */
public class EsserParser {
	private static final String ELEMENT_DELIMITER = " ";
	public static BinaryImage parse(String file) {
		return parse_array(content(file)); 
	}	
	private static BinaryImage parse_array(String[] content) {	
		final String[] header = content[0].split(ELEMENT_DELIMITER);
		int width = Integer.parseInt(header[0]);
		int height = Integer.parseInt(header[1]);
		String[] body = body(content);
		List<Integer> values = new ArrayList<Integer>();
		for (int line = 0; line < Array.getLength(body); line++) {
			String[] elements = body[line].split(ELEMENT_DELIMITER);
			for (int e = 0; e < Array.getLength(elements); e++) {
				values.add(Integer.parseInt(elements[e]));
			}
		}
		return BinaryImages.fourNeighborBinaryImage(width, height, values);
	}

	/**
	 * return the acsii image string, without header
	 * @param file
	 */
	private static String[] body(String[] content) {
		return Arrays.copyOfRange(content, 1, content.length);
	}
	
	private static List<String> body(List<String> content) {
		List<String> result = content;
		result.remove(0);
		return result;
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
			return new String[] {""};
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

