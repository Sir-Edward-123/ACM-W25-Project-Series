package main;

import java.util.Random;

import javax.swing.JPanel;

import IntSpaces.ArraySpace;
import IntSpaces.IntSpace;
import main.managers.GameManager;

public class Array extends JPanel {
	private static final long serialVersionUID = 4981441440807683209L;

	private GameManager gameManager;
	
	private String arrName;
	private int arrListIdx;
	
	private final int ARRAY_SIZE;
	private IntSpace[] spaces;
	private int[] values;
	
	public Array(GameManager manager, int size, int arrListIdx) {
		gameManager = manager;
		
		this.arrListIdx = arrListIdx;
		this.arrName = "arr_" + Character.toString((char)(arrListIdx + 97)); // Name starts at arr_a, then letters increase 
		
		this.ARRAY_SIZE = size;
		this.spaces = new IntSpace[ARRAY_SIZE];
		this.values = new int[ARRAY_SIZE];
		
		Random random = new Random();
		for(int i = 0; i < ARRAY_SIZE; i++) {
			int nextVal = random.nextInt(100);
			spaces[i] = new ArraySpace(gameManager, this, i, nextVal);
			values[i] = nextVal;
		}
		manager.visualManager().drawArr(this);
	}
	
	public int getListIdx() {
		return arrListIdx;
	}
	
	public String getName() {
		return arrName;
	}
	
	public int length() {
		return ARRAY_SIZE;
	}
	
	public IntSpace[] getSpaces() {
		return spaces;
	}
	
	public int[] getValues() {
		return values;
	}
	
}
