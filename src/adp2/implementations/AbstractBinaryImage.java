package adp2.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.List;

import adp2.interfaces.BinaryImage;
import adp2.interfaces.Blob;
import adp2.interfaces.Point;

public abstract class AbstractBinaryImage implements BinaryImage {
    private final List<Blob> blobs;
    
    protected AbstractBinaryImage(List<List<Boolean>> shape) {
        // pre-conditions are checked in the BinaryImages factory
        blobs = calcBlobs(shape);
    }
    
    private List<Blob> calcBlobs(List<List<Boolean>> shape) {
        return null;
    }

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
        return null;
    }

}
