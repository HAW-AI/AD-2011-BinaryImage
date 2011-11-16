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
 * @author Sebastian Bartels
 */
public class BlobParser {

    private static final String ELEMENT_DELIMITER = ",";
    private static final String SEQUENCE_DELIMITER = ")";

    public static List<BoundarySequence> parse(String file) {
        return parse_array(content(file));
    }

    private static List<BoundarySequence> parse_array(String[] content) {
        List<BoundarySequence> result = new ArrayList<BoundarySequence>();

        for (int i = 0; i < Array.getLength(content); i++) {
            String[] xSplit = content[i].split("|");
            int x = Integer.parseInt(xSplit[0]);

            String[] ySplit = xSplit[1].split("(");
            int y = Integer.parseInt(ySplit[0]);

            String[] elements = ySplit[1].split(ELEMENT_DELIMITER);

            int n = 0;
            List<Integer> elemList = new ArrayList<Integer>();
            boolean end = false;
            while (!end) {
                if (elements[n].endsWith(SEQUENCE_DELIMITER)) {
                    int sequenceDelIndex = elements[n].lastIndexOf(SEQUENCE_DELIMITER);
                    elements[n] = elements[n].copyValueOf(elements[n].toCharArray(), 0, sequenceDelIndex - 1);
                    end = true;
                }
                elemList.add(Integer.parseInt(elements[n++]));
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
