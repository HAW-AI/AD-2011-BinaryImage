package adp2.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.reflect.Array;
import adp2.implementations.*;
import adp2.interfaces.BoundarySequence;

/**
 * Creates a list of blob-boundaries read from the given file.
 * Blobs are saved as:
 *   x|y(a,b,c)
 * Where x&y are the starting points coordinates, 
 * while a, b&c are the sequence (direction from the previous point)
 * as determined by the method to save the boundaries.
 * 
 * @author Sebastian Bartels
 */
public class BlobParser {

    private static final String ELEMENT_DELIMITER = "\\,";

    public static List<BoundarySequence> parse(String file) {
        return parse_array(content(file));
    }

    private static List<BoundarySequence> parse_array(String[] content) {
        List<BoundarySequence> result = new ArrayList<BoundarySequence>();
        
        for (int i = 0; i < content.length; i++) {
            String[] xSplit = content[i].split("\\|");
            int x = Integer.parseInt(xSplit[0]);

            String[] ySplit = xSplit[1].split("\\(");
            int y = Integer.parseInt(ySplit[0]);

            if(!ySplit[1].endsWith(")")) return result; //error
            
            char[] elements = ySplit[1].toCharArray();

            int n = 0;
            List<Integer> elemList = new ArrayList<Integer>();
            
            while (elements[n] != ')') {
                if(n%2 == 1){
                    if(elements[n] != ',') return result; //element higher than 10 -> error
                }
                else {
                    elemList.add(Integer.parseInt(""+elements[n]));
                }
                n++;
            };

            if ((x > -1 && y > -1)) {
                result.add(BoundarySequenceImpl.valueOf(PointImpl.valueOf(x, y), elemList));
            }
        }
        
        return result;
    }

    /**
     * returns the file content as String-Array
     * @param file
     * @return String[]
     * 
     * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
     * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
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
            // too bad, leave lines as is
        }

        return lines.toArray(new String[0]);
    }
}