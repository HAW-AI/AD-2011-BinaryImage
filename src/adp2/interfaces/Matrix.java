package adp2.interfaces;

public interface Matrix extends Iterable<Integer> {

    int width();

    int height();

    int get(int x, int y);
}
