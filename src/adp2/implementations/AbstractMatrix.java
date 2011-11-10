package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.Matrix;
import adp2.interfaces.Point;

/**
 * 
 * @author Aleksandr Nosov, Kathrin Kaflhöfer
 *
 */
public abstract class AbstractMatrix implements Matrix{
	final private int width;
	final private int height;
	final private List<Integer> values;
	
	public AbstractMatrix(int width, int height, List<Integer> values) {
		this.width = width;
		this.height = height;
		this.values = values;
	}
	public boolean isValid(){
		return (width * height) == values.size();
	}
	public int width() {
		return width;
	}

	public int height() {
		return height;
	}
	@Override
	public Iterator<Integer> iterator() {
		return values.iterator();
	}
	public int get(int x, int y) {
		if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return values.get(x + (y*width()));
	}
	protected List<Integer> inverseValues() {
		List<Integer> l= new ArrayList<Integer>();
		while (values.listIterator().hasPrevious())
			l.add(values.listIterator().previous());
		return l;
	}
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int y = 0; y < height(); y++) {
			result.append("[");
			for (int x = 0; x < width(); x++) {
				result.append(get(x,y));
			}
			result.append("]\n");
		}
		return result.toString();
	}
}
