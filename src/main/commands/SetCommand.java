package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;
import main.spaces.VariableSpace;

public class SetCommand extends Command {
	
	VariableSpace tempSpace;
	private int setVal;
	
	public SetCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level, VariableSpace temp) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
		this.tempSpace = temp;
		this.pointValue = 1;
		this.computeGoalState(level);
	}
	
	@Override
	public void setup() {
		tempSpace.setValue(setVal);
	}

	@Override
	protected void computeGoalState(int level) {
		Random random = new Random();
		setVal = random.nextInt(100);
		int destArrIdx = random.nextInt(goalArrState.size());
		int destElemIdx = random.nextInt(goalArrState.get(destArrIdx).length);
		
		goalArrState.get(destArrIdx)[destElemIdx] = setVal;
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		str.append("temp = " + setVal + "; ");
		this.appendFormatedArrNameAndIdx(str, destArrIdx, destElemIdx, usePtrArithm(random, level));
		str.append(" = ");
		str.append("temp");
		str.append(';');
		this.commandText = str.toString();
	}
}
