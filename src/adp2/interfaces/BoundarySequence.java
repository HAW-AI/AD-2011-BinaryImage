package adp2.interfaces;

import java.util.List;

public interface BoundarySequence {
    final int RIGHT = 0;
    final int TOPRIGHT = 1;
    final int TOP = 2;
    final int TOPLEFT = 3;
    final int LEFT = 4;
    final int BOTTOMLEFT = 5;
    final int BOTTOM = 6;
    final int BOTTOMRIGHT = 7;

	Point getStartPoint();
	List<Integer> getSequence();
	Blob createBlob();
	
	//info/question: boundary2() klappt nur fuer 4er blobs - soll doch auch fuer 8er gehen, oder?
	
	//getUmfang()
	//setBytes(byte[] fromFileOrWhatever)
	//getBytes() - toWriteToFile
	//toBlob(Point pointInBinaryImage, BinaryImage bi)
	//toBlob(BinaryImage bi)
	
	//optional:
	//binaryImage.removeBlob(int nr)
	//binaryImage.addBlob(Blob blob)
}
