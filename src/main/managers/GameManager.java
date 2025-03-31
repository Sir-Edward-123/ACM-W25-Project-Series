package main.managers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import main.commands.*;
import main.Array;
import main.Game;
import main.spaces.*;

public class GameManager {
	private Game game;

	private SoundManager soundManager;
	private VisualManager visualManager;

	Random random;

	// Game Vars

	private ArrayList<Array> arrays;
	private ArrayList<int[]> arraysState;

	private PointerSpace[] ptrs;
	private ArraySpace[] ptrsState;

	private int heldValue;
	private IntSpace currMouseIntSpace; // What space is the mouse hovering over

	private VariableSpace scoreVar;
	private VariableSpace levelVar;
	private VariableSpace commandCountVar;
	private VariableSpace tempVar;
	private VariableSpace countdownVar;

	private Stack<Space> editedSpaces;
	private Stack<Integer> overwrittenVals; // Of the edited spaces
	private Stack<ArraySpace> overwrittenPtrVals;

	private Queue<Command> commands;
	private Command mostRecentlyAddedCommand;
	private CommandType[] commandTypeValues;
	private Timer commandAddTimer;
	int nextLevelScore;
	
	private Timer countdownTimer;
	int countdownStartSeconds = 20;
	
	// DEBUG VARS
	int numStartingCommands = 5;
	int startingScore = 0;
	int startingLevel = 0;
	int nextLevelScoreIncrement = 20;
	int startingSpawnDelay = 11000 - (startingLevel * 100);

	public GameManager(Game game) {
		this.game = game;

		soundManager = new SoundManager(this);
		visualManager = new VisualManager(this);

		arrays = new ArrayList<Array>();
		arraysState = new ArrayList<int[]>();
		// arraysStartCommandState = new ArrayList<int[]>();

		random = new Random();
		commandTypeValues = CommandType.values();

		editedSpaces = new Stack<Space>();
		overwrittenVals = new Stack<Integer>();
		overwrittenPtrVals = new Stack<ArraySpace>();

		commands = new LinkedList<Command>();
		commandAddTimer = new Timer(startingSpawnDelay, e -> addCommand(null));
		nextLevelScore = nextLevelScoreIncrement;
		
		countdownTimer = new Timer(1000, e -> decreaseCountdown());
	}

	public void start() {
		// OTHER STUFF HERE
		visualManager.initGameScreen();
		addArray(5);
		addArray(4);
		scoreVar = addVariable("score", startingScore, true);
		levelVar = addVariable("level", startingLevel, true);
		commandCountVar = addVariable("commandCnt", 0, true);
		countdownVar = addVariable("countdown", countdownStartSeconds, true);
		tempVar = addVariable("temp", 0, false);
		ptrs = new PointerSpace[3];
		ptrsState = new ArraySpace[3];
		for (int i = 0; i < ptrs.length; i++) {
			ptrs[i] = addVariablePointer("ptr" + (i + 1), arrays.get(0).getSpaces()[i], false, i);
			ptrsState[i] = ptrs[i].getPointingTo();
		}
		for(int i = 0; i < numStartingCommands; i++) {
			addCommand(null);
		}
		commandAddTimer.start();
	}

	public Array addArray(Array arr) {
		arrays.add(arr);
		arraysState.add(arr.getValues()); // Shallow copy is ok, we want the arraysState values to be tied to the arr
											// values
		visualManager.drawArr(arr);
		// arraysStartCommandState.add(Arrays.copyOfRange(arr.getValues(), 0,
		// arr.length()));
		addArrPointer(arr.getName(), arr.getSpaces()[0]);
		return arr;
	}

	private Array addArray(int size) {
		Array arr = new Array(this, size, arrays.size());
		arrays.add(arr);
		arraysState.add(arr.getValues()); // Shallow copy is ok, we want the arraysState values to be tied to the arr
											// values
		visualManager.drawArr(arr);
		// arraysStartCommandState.add(Arrays.copyOfRange(arr.getValues(), 0,
		// arr.length()));
		addArrPointer(arr.getName(), arr.getSpaces()[0]);
		return arr;
	}

	private VariableSpace addVariable(String name, int value, boolean readOnly) {
		VariableSpace var = new VariableSpace(this, name, value, readOnly);
		visualManager.drawVariable(var);
		return var;
	}

	private PointerSpace addArrPointer(String name, ArraySpace pointTo) {
		PointerSpace pointer = new PointerSpace(this, name, pointTo, true, -1);
		visualManager.drawArrPointer(pointer);
		return pointer;
	}

	private PointerSpace addVariablePointer(String name, ArraySpace pointTo, boolean readOnly, int pointerListIdx) {
		PointerSpace pointer = new PointerSpace(this, name, pointTo, readOnly, pointerListIdx);
		visualManager.drawVariablePointer(pointer);
		return pointer;
	}

	// null CommandType means random
	private void addCommand(CommandType selectedType) {

		Command command = null;
		CommandType type;

		if (selectedType == null) {
			type = commandTypeValues[random.nextInt(commandTypeValues.length)];
			// Do not add arr randomly
			while (type == CommandType.ADD_ARR) {
				type = commandTypeValues[random.nextInt(commandTypeValues.length)];
			}
		}
		else {
			type = selectedType;
		}

		ArrayList<int[]> arrState = null;
		ArraySpace[] ptrsState;
		if (commands.isEmpty()) {
			arrState = this.arraysState;
			ptrsState = this.ptrsState;
		} else {
			arrState = mostRecentlyAddedCommand.getGoalArrState();
			ptrsState = mostRecentlyAddedCommand.getGoalPtrState();
		}

		switch (type) {
		case ADD_ARR: 
			command = new AddArrCommand(this, arrState, ptrsState); break;
		case SET:
			command = new SetCommand(this, arrState, ptrsState, levelVar.getValue(), tempVar);
			break;
		case SET_PTR:
			command = new SetPointerCommand(this, arrState, ptrsState, levelVar.getValue());
			break;
		case COPY:
			command = new CopyCommand(this, arrState, ptrsState, levelVar.getValue());
			break;
		case SWAP:
			command = new SwapCommand(this, arrState, ptrsState, levelVar.getValue());
			break;
		case SORT:
			command = new SortCommand(this, arrState, ptrsState, levelVar.getValue());
			break;
		default:
			break;
		}
		commands.add(command);
		mostRecentlyAddedCommand=command;
		commandCountVar.gameSetValue(commandCountVar.getValue()+1);
		visualManager.drawCommand(command);

		// If the command was added when the commands queue was empty
		if(commands.size() == 1) {
			clearUndoAndReset();
			mostRecentlyAddedCommand.setup();
			if (checkCommandComplete()) {
				completeCommand();
			}
		}
		
		if(commands.size() > 20 && !countdownTimer.isRunning()) {
			countdownTimer.restart();
		}
	}

	private boolean checkCommandComplete() {
		if (commands.peek() == null) {
			return false;
		}
		if (commands.peek().checkGoalReached(arraysState, ptrsState)) {
			return true;
		}
		return false;
	}

	private void completeCommand() {
		scoreVar.gameSetValue(scoreVar.getValue() + commands.peek().getPoints());
		commands.remove();
		commandCountVar.gameSetValue(commandCountVar.getValue() - 1);
		clearUndoAndReset();
		visualManager.removeCommand();

		if (scoreVar.getValue() >= nextLevelScore && levelVar.getValue() < 10) {
			increaseLevel();
		}
		
		if(countdownTimer.isRunning() && commands.size() <= 20) {
			countdownTimer.stop();
			countdownVar.gameSetValue(countdownStartSeconds);
		}

		if (commands.peek() != null) {
			commands.peek().setup();
			// Check if subsequent commands are complete
			if (checkCommandComplete()) {
				completeCommand();
			}
		}
	}

	private void increaseLevel() {
		commandAddTimer.setDelay(commandAddTimer.getDelay() - 1000);
		levelVar.gameSetValue(levelVar.getValue() + 1);
		nextLevelScore += nextLevelScoreIncrement;
		if (levelVar.getValue() % 2 == 0) {
			addCommand(CommandType.ADD_ARR);
		}
	}
	
	private void decreaseCountdown() {
		countdownVar.gameSetValue(countdownVar.getValue() - 1);
		visualManager.flashCommandList();
		if(countdownVar.getValue() == 0) {
			game.lose(scoreVar.getValue());
		}
	}

	public void reportMouseEvent(IntSpace reportedSpace, MouseEventType e) {
		if (e == MouseEventType.MOUSE_ENTERED) {
			currMouseIntSpace = reportedSpace;
		} else if (e == MouseEventType.MOUSE_EXITED) {
			currMouseIntSpace = null;
		} else if (e == MouseEventType.MOUSE_PRESSED) {
			heldValue = reportedSpace.getValue();
		} else if (e == MouseEventType.MOUSE_RELEASED) {
			if (currMouseIntSpace != null && currMouseIntSpace.getValue() != heldValue
					&& !currMouseIntSpace.readOnly()) {
				editedSpaces.push(currMouseIntSpace);
				overwrittenVals.push(currMouseIntSpace.getValue());
				currMouseIntSpace.setValue(heldValue);
				visualManager.setUndoButtonEnabled(true);
				visualManager.setResetButtonEnabled(true);
				if (checkCommandComplete()) {
					completeCommand();
				}
			}
		}
		visualManager.reportVisualMouseEvent(reportedSpace, e);
	}

	public void reportMouseEvent(PointerSpace reportedSpace, MouseEventType e) {
		if (e == MouseEventType.MOUSE_PRESSED) {

		} else if (e == MouseEventType.MOUSE_RELEASED) {
			if (currMouseIntSpace != null && currMouseIntSpace.getClass() == ArraySpace.class
					&& reportedSpace.getPointingTo() != currMouseIntSpace) {
				editedSpaces.push(reportedSpace);
				overwrittenPtrVals.push(reportedSpace.getPointingTo());
				reportedSpace.setValue((ArraySpace) currMouseIntSpace);
				visualManager.setUndoButtonEnabled(true);
				visualManager.setResetButtonEnabled(true);
				if (checkCommandComplete()) {
					completeCommand();
				}
			}
		}
		visualManager.reportVisualMouseEvent(reportedSpace, e);
	}

	/*
	 * // ONLY USE WHEN A COMMAND IS COMPLETED private void saveStartCommandState()
	 * { int[] saveLastStateArrRef =
	 * arraysStartCommandState.get(currMouseIntSpace.getParentArr().getListIdx());
	 * saveLastStateArrRef[currMouseIntSpace.getIdx()] = heldValue;
	 * editedSpaces.clear(); overwrittenVals.clear();
	 * visualManager.setUndoButtonEnabled(false);
	 * visualManager.setResetButtonEnabled(false); }
	 */

	public void undoMove() {
		if (editedSpaces.empty()) {
			return;
		}

		if (editedSpaces.peek().getClass() == ArraySpace.class) {
			((IntSpace) editedSpaces.pop()).setValue(overwrittenVals.pop());
		} else if (editedSpaces.peek().getClass() == VariableSpace.class) {
			((IntSpace) editedSpaces.pop()).setValue(overwrittenVals.pop());
		} else if (editedSpaces.peek().getClass() == PointerSpace.class) {
			((PointerSpace) editedSpaces.pop()).setValue(overwrittenPtrVals.pop());
		}

		if (editedSpaces.empty()) {
			visualManager.setUndoButtonEnabled(false);
			visualManager.setResetButtonEnabled(false);
		}
	}

	public void resetMoves() {
		while (!editedSpaces.empty()) {
			undoMove();
		}
	}

	private void clearUndoAndReset() {
		editedSpaces.clear();
		overwrittenVals.clear();
		visualManager.setUndoButtonEnabled(false);
		visualManager.setResetButtonEnabled(false);
	}

	public String getNameFromArr(int arrIdx) {
		if (arrIdx < arrays.size()) {
			return arrays.get(arrIdx).getName();
		}
		// Hot fix name predictor
		else {
			return "arr_" + Character.toString((char) (arrIdx + 97));
		}

	}

	public int getNumArrs() {
		return arrays.size();
	}
	
	public ArraySpace getArrSpace(int arrIdx, int elemIdx) {
		return arrays.get(arrIdx).getSpaces()[elemIdx];
	}
	
	public String getNameFromPtr(int ptrListIdx) {
		return ptrs[ptrListIdx].getName();
	}
	
	public ArraySpace getPtrPointingTo(int ptrListIdx) {
		return ptrs[ptrListIdx].getPointingTo();
	}
	
	public void updatePtrState(int idx, ArraySpace space) {
		ptrsState[idx] = space;
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
