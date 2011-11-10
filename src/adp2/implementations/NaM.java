package adp2.implementations;

import java.util.ArrayList;

import adp2.interfaces.*;

public abstract class NaM extends AbstractMatrix {

	protected NaM() {
		super(0, 0, new ArrayList<Integer>());
	}
	@Override
	public boolean isValid() {
		return false;
	}
}
