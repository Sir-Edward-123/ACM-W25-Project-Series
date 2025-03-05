package commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;

public class SwapCommand extends Command {

	public SwapCommand(GameManager gameManager, ArrayList<int[]> startingState) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startingState);
		this.computeGoalState();
	}
	

	@Override
	protected void computeGoalState() {
		Random random = new Random();
		int sourceArrIdx = random.nextInt(goalState.size());
		int sourceElemIdx = random.nextInt(goalState.get(sourceArrIdx).length);
		int destArrIdx = random.nextInt(goalState.size());
		int destElemIdx = random.nextInt(goalState.get(destArrIdx).length);
		
		goalState.get(destArrIdx)[destElemIdx] = goalState.get(sourceArrIdx)[sourceElemIdx];
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		str.append(gameManager.getNameFromArr(destArrIdx));
		str.append('[');
		str.append(destElemIdx);
		str.append(']');
		str.append(" = ");
		str.append(gameManager.getNameFromArr(sourceArrIdx));
		str.append('[');
		str.append(sourceElemIdx);
		str.append(']');
		str.append(';');
		this.commandText = str.toString();
	}
	
}
