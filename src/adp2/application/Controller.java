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
		if (args.length >= 1 && new File(args[0]).canRead()) {
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
	}
	
	/**
	 * Handles basic object init.
	 */
	private Controller() {
		new View(this);
	}
	
	/**
	 * accessor method for retrieving binaryImage 
	 * @return BinaryImage
	 */
	public BinaryImage binaryImage() {
		if (this.binaryImage == null) setBinaryImage(BinaryImages.NaBI());
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
	public void setBinaryImage(BinaryImage image) {
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
