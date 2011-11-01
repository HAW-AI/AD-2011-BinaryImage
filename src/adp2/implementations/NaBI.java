package adp2.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

final class NaBI implements BinaryImage {

	@Override
	public Iterator<Blob> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int blobCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Blob blob(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Blob> blobs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Point> neighbours(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean valueAt(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connected(Point point1, Point point2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BinaryImage inverse() {
		// TODO Auto-generated method stub
		return this;
	}

}
