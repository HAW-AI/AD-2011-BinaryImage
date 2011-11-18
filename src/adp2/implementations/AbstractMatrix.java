package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.Matrix;

/**
 * 
 * @author Aleksandr Nosov, Kathrin Kaflhöfer
 *
 */
public abstract class AbstractMatrix implements Matrix {

    final private int width;
    final private int height;
    final private List<Integer> values;

    public AbstractMatrix(int width, int height, List<Integer> values) {
        this.width = width;
        this.height = height;
        this.values = values;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    public List<Integer> values() {
        return values;
    }

    @Override
    public Iterator<Integer> iterator() {
        return values.iterator();
    }

    @Override
    public int get(int x, int y) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return values.get(x + (y * width()));
    }

    @Deprecated
    protected List<Integer> inverseValues() {
        List<Integer> l = new ArrayList<Integer>();
        while (values.listIterator().hasPrevious()) {
            l.add(values.listIterator().previous());
        }
        return l;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < height(); y++) {
            result.append("[");
            for (int x = 0; x < width(); x++) {
                result.append(get(x, y));
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
