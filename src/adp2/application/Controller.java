package adp2.application;

import java.io.File;
import adp2.implementations.BinaryImages;
import adp2.interfaces.BinaryImage;

/**
 * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 *
 */
public class Controller {
	public static void main(String[] args) {
		if (new File(args[0]).canRead()) {
			new Controller(args[0]);
		}
		else {
			new Controller();
		}
	}
	
	/**
	 * Mutable, else we would need to restart for opening a image
	 */
	private BinaryImage binaryImage;
	
	/**
	 * Constructor builds BinaryImage for given File
	 * @param filename
	 */
	private Controller(String filename) {
		this();
		setBinaryImage(openImage(filename));
		new View(binaryImage());
	}
	
	/**
	 * Handles basic object init.
	 */
	private Controller() {
		// no file given, what now?
		// - create view
		// - keep running
		/*List<Boolean> array= new ArrayList<Boolean>();
		array.add(new Boolean(true));
		array.add(new Boolean(false));
		array.add(new Boolean(true));
		array.add(new Boolean(true));

		List<Boolean> array2= new ArrayList<Boolean>();
		array2.add(new Boolean(false));
		array2.add(new Boolean(true));
		array2.add(new Boolean(false));
		array2.add(new Boolean(false));
		
		List<Boolean> array3= new ArrayList<Boolean>();
		array3.add(new Boolean(true));
		array3.add(new Boolean(false));
		array3.add(new Boolean(true));
		array3.add(new Boolean(true));
		
		List<Boolean> array4= new ArrayList<Boolean>();
		array4.add(new Boolean(false));
		array4.add(new Boolean(true));
		array4.add(new Boolean(false));
		array4.add(new Boolean(false));
		
		List<Boolean> array5= new ArrayList<Boolean>();
		array5.add(new Boolean(true));
		array5.add(new Boolean(false));
		array5.add(new Boolean(false));
		array5.add(new Boolean(true));
		
		List<Boolean> array6= new ArrayList<Boolean>();
		array6.add(new Boolean(false));
		array6.add(new Boolean(true));
		array6.add(new Boolean(false));
		array6.add(new Boolean(false));
		
		List<List<Boolean>> testData = new ArrayList<List<Boolean>>();
		
		testData.add(array);
		testData.add(array2);
		testData.add(array3);
		testData.add(array4);
		testData.add(array5);
		testData.add(array6);
		
		BinaryImage testImage = BinaryImages.fourNeighborBinaryImage(testData);
		
		new View(binaryImage());
		*/	
	}
	
	/**
	 * accessor method for retrieving binaryImage 
	 * @return BinaryImage
	 */
	public BinaryImage binaryImage() {
		return this.binaryImage;
	}
	
	/**
	 * open file and build EsserImage
	 * @param filename
	 * @return
	 */
	public EsserImage openImage(String filename) {
		return EsserParser.parse(filename);
	}
	
	/**
	 * set binaryImage field to given BinaryImage, after converting from EsserImage
	 * @param image
	 */
	private void setBinaryImage(EsserImage image) {
		if (!image.isValid()) {
			setBinaryImage(BinaryImages.NaBI());
		}
		else {
			setBinaryImage(BinaryImages.binaryImage(image));
		}
	}
	
	/**
	 * set binaryImage field to given BinaryImage
	 * @param image
	 */
	private void setBinaryImage(BinaryImage image) {
		this.binaryImage = image;
	}
	/**
	 * predicate method indicating BinaryImage presents
	 * @return
	 */
	public boolean isBinaryImageAvailable() {
		return this.binaryImage == null;
	}
}
