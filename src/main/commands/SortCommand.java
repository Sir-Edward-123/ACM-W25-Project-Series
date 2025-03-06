package main.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;

public class SortCommand extends Command {
	public SortCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
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
		int[] sourceArr = goalArrState.get(sourceArrIdx);
		boolean increasing = random.nextBoolean();
		
		for(int i = 1; i < sourceArr.length; i++) {
			for(int j = i - 1; j >= 0; j--) {
				if((increasing && sourceArr[j] > sourceArr[j + 1]) || (!increasing && sourceArr[j] < sourceArr[j + 1])) {
					int temp = sourceArr[j + 1];
					sourceArr[j + 1] = sourceArr[j];
					sourceArr[j] = temp;
					continue;
				}
				break;
			}
		}
		this.pointValue = 2 * goalArrState.size();
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		if(increasing) {
			str.append("sortIncreasing(");
		}
		if(!increasing) {
			str.append("sortDecreasing(");
		}
		str.append(gameManager.getNameFromArr(sourceArrIdx) + ")");
		str.append(';');
		this.commandText = str.toString();
	}
}
