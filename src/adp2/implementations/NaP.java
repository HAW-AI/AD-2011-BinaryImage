package adp2.implementations;

public class NaP extends AbstractPoint{

	protected NaP(int x, int y) {
		super(x, y);
	}
	public static NaP valueOf(int x, int y){
		return new NaP(x,y);
	}
}
