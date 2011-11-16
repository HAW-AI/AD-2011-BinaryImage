package adp2.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adp2.implementations.BinaryImages;
import adp2.implementations.BlobImpl;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import adp2.interfaces.Matrix;

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
	private View view;

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
		this.view = new View(this);
		// for testing, just comment this line in:
		//setBinaryImage(openImage("test/fixtures/32x32.esser"));
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
	public BinaryImage openImage(String filename) {
		return EsserParser.parse(filename);
	}

	/**
	 * set binaryImage field to given BinaryImage
	 * @param image
	 */
	public void setBinaryImage(BinaryImage image) {
		this.binaryImage = image;
		if (this.view != null) this.view.repaint();
	}
	/**
	 * predicate method indicating BinaryImage presents
	 * @return
	 */
	public boolean isBinaryImageAvailable() {
		return this.binaryImage == null;
	}

	/**
	 * 
	 * @return long string with a text representation of all blobs with 1 blob in each line
	 * 
	 * @author Harald Kirschenmann
	 * @author Philipp Gillé
	 */
	public String getBlobAsSequenceString() {
		String result = "";
		BoundarySequence sequence;
		int maxNeighbours = binaryImage.isEightNbr()?8:4;
		for(Blob b : binaryImage.blobs()){
			sequence = b.boundary_esser2(maxNeighbours);
			result += sequence.getStartPoint().x();
			result += "|";
			result += sequence.getStartPoint().y();
			result += "(";
			for (int i : sequence.getSequence()) {
				result += i;
			}
			result += ")\n";
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 * 
	 * @author Sebastian Bartels
	 */
	public List<Blob> openBlob(String filename) {
		List<BoundarySequence> boundaries = BlobParser.parse(filename);
		List<Blob> result = new ArrayList<Blob>();
		for(BoundarySequence b : boundaries)
			result.add(b.createBlob());
		return result;
	}
}