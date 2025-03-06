package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;
import main.spaces.PointerSpace;

public class SwapCommand extends Command {

	public SwapCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
		this.pointValue = 2;
		this.computeGoalState(level);
	}
	
	@Override
	public void setup() {
		// Nothing
	}
	

	@Override
	protected void computeGoalState(int level) {
		Random random = new Random();
		int sourceArrIdx = random.nextInt(goalArrState.size());
		int sourceElemIdx = random.nextInt(goalArrState.get(sourceArrIdx).length);
		int destArrIdx = random.nextInt(goalArrState.size());
		int destElemIdx = random.nextInt(goalArrState.get(destArrIdx).length);
		
		goalArrState.get(destArrIdx)[destElemIdx] = goalArrState.get(sourceArrIdx)[sourceElemIdx];
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		this.appendFormatedArrNameAndIdx(str, destArrIdx, destElemIdx, usePtrArithm(random, level));
		str.append(" = ");
		this.appendFormatedArrNameAndIdx(str, sourceArrIdx, sourceElemIdx, usePtrArithm(random, level));
		str.append(';');
		this.commandText = str.toString();
	}
	
}
