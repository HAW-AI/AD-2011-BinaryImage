package adp2.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import adp2.implementations.BinaryImages;
import adp2.implementations.BlobImpl;
import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.BoundarySequence;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben Rexin <benjamin.rexin@haw-hamburg.de>
 * @author Patrick Detlefsen <patrick.detlefsen@haw-hamburg.de>
 *
 */
public class Controller {

    public static void main(String[] args) {
        if (args.length >= 1 && new File(args[0]).canRead()) {
            new Controller(args[0]);
        } else {
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
        if (this.binaryImage == null) {
            setBinaryImage(BinaryImages.NaBI());
        }
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
        if (this.view != null) {
            this.view.repaint();
        }
    }

    /**
     * predicate method indicating BinaryImage presents
     * @return
     */
    public boolean isBinaryImageAvailable() {
        return this.binaryImage == null;
    }

    /**
     * Returns a list of blobs saved as boundary-sequence in the given file
     * 
     * @param filename
     * @return List<Blob>
     * 
     * @author Sebastian Bartels
     */
    public List<Blob> openBlob(String filename) {
        List<BoundarySequence> boundaries = BlobParser.parse(filename);
        List<Blob> result = new ArrayList<Blob>();
        for (BoundarySequence b : boundaries) {
            result.add(b.createBlob());
        }
        return result;
    }

    /*
     * Adds the selected blob to the currently used binary image
     * 
     * @param Blob
     * 
     * @author Sebastian Bartels
     */
    protected void addBlob(Blob b) {
        this.binaryImage = this.binaryImage.addBlob(b);
        this.view.repaint();
    }
    
    /**
     * @author Harald Kirschenmann
     * @author Philipp Gillé
     */
    public void writeAllBlobs(String path) {
    	List<Blob> blobs = binaryImage().blobs();
    	String resultBlobs = "";
    	for (Blob b : blobs) {
    		resultBlobs += b.toString();
    		resultBlobs += "\n";
    	}
    	System.out.println("writeAllBlobs -> " + resultBlobs);//test
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(resultBlobs);
            out.close();
        } catch (IOException e) {
            System.out.println("Error while trying to write the file");
        }
    }
    
    /**
     * @author Philipp Gillé
     * @param path to the file
     * @param blobId - id of the blob to be written
     */
    public void writeOneBlob(String path, int blobId) {
// jörgs code auskommentiert wegen änderung der blobsequenzmethode - philipp
//        String binaryImageAsSequenceString = getBlobAsSequenceString();
//        String[] blobies = binaryImageAsSequenceString.split("\n");
//
//        if (blobId < 0 || blobId >= blobies.length) {
//            return; //ignore No save
//        }
//        String blob = blobies[blobId];
//
//        System.out.println(binaryImageAsSequenceString);//test
//        System.out.println(">> " + blob);//test
    	
    	List<Blob> blobs = binaryImage().blobs();
    	String resultBlob = blobs.get(blobId).toString();
		System.out.println("writeOneBlob mit id=" + blobId + " -> " + resultBlob);//test
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(resultBlob);
            out.close();
        } catch (IOException ex) {
            System.out.println("Error while trying to write the file");
        }
    }
}
