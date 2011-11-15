package adp2.interfaces;

import java.util.List;

public interface BoundarySequence {

	Point getStartPoint();
	List<Integer> getSequence();
	
	
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
