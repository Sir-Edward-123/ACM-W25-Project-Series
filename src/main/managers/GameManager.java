package main.managers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import IntSpaces.*;
import commands.*;
import main.Array;
import main.Game;

public class GameManager {
	private Game game;
	
	private SoundManager soundManager;
	private VisualManager visualManager;
	
	private ArrayList<Array> arrays;
	private ArrayList<int[]> arraysState;
	
	Random random;
	
	// Game Vars
	
	private int heldValue;
	private IntSpace currMouseSpace; // What space is the mouse hovering over
	
	private Stack<IntSpace> editedSpaces;
	private Stack<Integer> overwrittenVals; // Of the edited spaces
	
	private Queue<Command> commands;
	private Command mostRecentlyAddedCommand;
	private CommandType[] commandTypeValues;
	
	public GameManager(Game game) {
		this.game = game;
		
		soundManager = new SoundManager(this);
		visualManager = new VisualManager(this);
		
		arrays = new ArrayList<Array>();
		arraysState = new ArrayList<int[]>();
		//arraysStartCommandState = new ArrayList<int[]>();
		
		random = new Random();
		commandTypeValues = CommandType.values();
		
		editedSpaces = new Stack<IntSpace>();
		overwrittenVals = new Stack<Integer>();
		
		commands = new LinkedList<Command>();
	}
	
	public void start() {
		// OTHER STUFF HERE
		visualManager.initGameScreen();
		addArray(5);
		addArray(4);
		addVariable("score", 0, true);
		addVariable("level", 0, true);
		addVariable("temp", 0, false);
		addCommand();
	}
	
	private void addArray(int size) {
		Array arr = new Array(this, size, arrays.size());
		arrays.add(arr);
		arraysState.add(arr.getValues()); // Shallow copy is ok, we want the arraysState values to be tied to the arr values
		//arraysStartCommandState.add(Arrays.copyOfRange(arr.getValues(), 0, arr.length()));
	}
	
	private void addVariable(String name, int value, boolean readOnly) {
		VariableSpace var = new VariableSpace(this, name, value, readOnly);
		visualManager.drawVariable(var);
	}
	
	private void addCommand() {
		CommandType type = commandTypeValues[random.nextInt(commandTypeValues.length)];
		Command command = null;
		
		ArrayList<int[]> state = null;
		if(commands.isEmpty()) {
			state = arraysState;
		}
		else {
			state = mostRecentlyAddedCommand.getGoalState();
		}
		
		switch(type) {
		case SWAP:
			command = new SwapCommand(this, state);
		}
		commands.add(command);
		mostRecentlyAddedCommand = command;
		visualManager.drawCommand(command);
		
		// If the command was added when the commands queue was empty
		if(commands.size() == 1) {
			if(checkCommandComplete()) {
				completeCommand();
			}
		}
	}
	
	public boolean checkCommandComplete() {
		if(commands.peek() == null) {
			return false;
		}
		if(commands.peek().checkGoalReached(arraysState)) {
			return true;
		}
		return false;
	}
	
	public void completeCommand() {
		commands.remove();
		editedSpaces.clear();
		overwrittenVals.clear();
		visualManager.setUndoButtonEnabled(false);
		visualManager.setResetButtonEnabled(false);
		visualManager.removeCommand();
		System.out.println("Complete");
		// Check if subsequent commands are complete
		if(checkCommandComplete()) {
			completeCommand();
		}
	}
	
	public void reportMouseEvent(IntSpace reportedSpace, MouseEventType e) {
		if(e == MouseEventType.MOUSE_ENTERED) {
			currMouseSpace = reportedSpace;
		}
		else if(e == MouseEventType.MOUSE_EXITED) {
			currMouseSpace = null;
		}
		else if(e == MouseEventType.MOUSE_PRESSED) {
			heldValue = reportedSpace.getValue();
		}
		else if(e == MouseEventType.MOUSE_RELEASED) {
			if(currMouseSpace != null && currMouseSpace.getValue() != heldValue && !currMouseSpace.readOnly()) {
				editedSpaces.push(currMouseSpace);
				overwrittenVals.push(currMouseSpace.getValue());
				currMouseSpace.setValue(heldValue);
				visualManager.setUndoButtonEnabled(true);
				visualManager.setResetButtonEnabled(true);
				if(checkCommandComplete()) {
					completeCommand();
				}
			}
		}
		visualManager.reportVisualMouseEvent(reportedSpace, e);
	}
	
	/*
	// ONLY USE WHEN A COMMAND IS COMPLETED
	private void saveStartCommandState() {
		int[] saveLastStateArrRef = arraysStartCommandState.get(currMouseSpace.getParentArr().getListIdx());
		saveLastStateArrRef[currMouseSpace.getIdx()] = heldValue;
		editedSpaces.clear();
		overwrittenVals.clear();
		visualManager.setUndoButtonEnabled(false);
		visualManager.setResetButtonEnabled(false);
	}
	*/
	
	public void undoMove() {
		if(editedSpaces.empty()) {
			return;
		}
		editedSpaces.pop().setValue(overwrittenVals.pop());
		if(editedSpaces.empty()) {
			visualManager.setUndoButtonEnabled(false);
			visualManager.setResetButtonEnabled(false);
		}
	}
	
	public void resetMoves() {
		while(!editedSpaces.empty()) {
			undoMove();
		}
	}
	
	public String getNameFromArr(int arrIdx) {
		return arrays.get(arrIdx).getName();
	}
	
	public Game game() {
		return game;
	}
	
	public SoundManager soundManager() {
		return soundManager;
	}
	
	public VisualManager visualManager() {
		return visualManager;
	}
}
