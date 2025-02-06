package main;

public class ArraySpace {
	private int x;
	private int y;
	private int value;
	
	static final int SPACE_WIDTH = 100;
	static final int SPACE_HEIGHT = 80;
	
	public ArraySpace(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getValue() {
		return value;
	}
	
	static int getWidth() {
		return SPACE_WIDTH;
	}
	
	static int getHeight() {
		return SPACE_HEIGHT;
	}
	
	void setValue(int value) {
		this.value = value;
	}
}
