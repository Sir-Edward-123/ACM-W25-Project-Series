package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;
import main.spaces.VariableSpace;

public class SetPointerCommand extends Command {

	public SetPointerCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
		this.pointValue = 1;
		this.computeGoalState(level);
	}
	
	@Override
	protected void computeGoalState(int level) {
		Random random = new Random();
		
		// Hotfix - Find better solution later
		int maxSize;
		if(goalArrState.size() > gameManager.getNumArrs()) {
			maxSize = gameManager.getNumArrs();
		}
		else {
			maxSize = goalArrState.size();
		}
		
		int destArrIdx = random.nextInt(maxSize);
		int destElemIdx = random.nextInt(goalArrState.get(destArrIdx).length);
		
		int ptrIdx = random.nextInt(goalPtrState.length);
		
		goalPtrState[ptrIdx] = gameManager.getArrSpace(destArrIdx, destElemIdx);
		
		StringBuilder str = new StringBuilder();
		str.append(gameManager.getNameFromPtr(ptrIdx));
		str.append(" = ");
		this.appendFormatedArrNameAndIdx(str, destArrIdx, destElemIdx, usePtrArithm(random, level), false);
		str.append(';');
		this.commandText = str.toString();
	}

	@Override
	public void setup() {
		// Nothing
	}

}
