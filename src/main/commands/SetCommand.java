package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.VariableSpace;

public class SetCommand extends Command {
	
	VariableSpace tempSpace;
	private int setVal;
	
	public SetCommand(GameManager gameManager, ArrayList<int[]> startingState, VariableSpace temp) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startingState);
		this.tempSpace = temp;
		this.computeGoalState();
	}
	
	@Override
	public void setup() {
		tempSpace.setValue(setVal);
	}

	@Override
	protected void computeGoalState() {
		Random random = new Random();
		setVal = random.nextInt(100);
		int destArrIdx = random.nextInt(goalState.size());
		int destElemIdx = random.nextInt(goalState.get(destArrIdx).length);
		
		goalState.get(destArrIdx)[destElemIdx] = setVal;
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		str.append("temp = " + setVal + "; ");
		str.append(gameManager.getNameFromArr(destArrIdx));
		str.append("[" + destElemIdx + "]");
		str.append(" = ");
		str.append("temp");
		str.append(';');
		this.commandText = str.toString();
	}
}
