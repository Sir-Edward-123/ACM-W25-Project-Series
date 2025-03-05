package IntSpaces;

import main.Array;
import main.managers.GameManager;

public class ArraySpace extends IntSpace{
	private static final long serialVersionUID = -6319335322812156499L;
	
	private Array parentArr;
	private int idx;

	// Array Space Constructor
	public ArraySpace(GameManager manager, Array parent, int idx, int value) {
		this.gameManager = manager;
		this.parentArr = parent;
		this.idx = idx;
		this.value = value;
		this.readOnly = false;
		setup();
	}
	
	public int getIdx() {
		return idx;
	}
	
	@Override
	public void setValue(int value) {
		this.value = value;
		this.getParentArr().getValues()[idx] = value;
		this.updateDispValue();
	}
	
	public Array getParentArr() {
		return parentArr;
	}
}
