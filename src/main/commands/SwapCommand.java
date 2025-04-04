package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;

public class SwapCommand extends Command{
	
	public SwapCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
		this.pointValue = 4;
		this.computeGoalState(level);
	}
	
	@Override
	public void setup() {
		System.out.println();
	}
	

	@Override
	protected void computeGoalState(int level) {
		Random random = new Random();
		
		int sourceArrIdx;
		int sourceElemIdx;
		int destArrIdx;
		int destElemIdx;
		
		boolean sourceUsingPtr = random.nextBoolean();
		boolean destUsingPtr = random.nextBoolean();
		int sourcePtrIdx = -1;
		int destPtrIdx = -1;
		if(sourceUsingPtr) {
			sourcePtrIdx = random.nextInt(goalPtrState.length);
			ArraySpace arrSpace = goalPtrState[sourcePtrIdx];
			//ArraySpace arrSpace = gameManager.getPtrPointingTo(sourcePtrIdx);
			sourceArrIdx = arrSpace.getParentArr().getListIdx();
			sourceElemIdx = arrSpace.getIdx();
		}
		else {
			sourceArrIdx = random.nextInt(goalArrState.size());
			sourceElemIdx = random.nextInt(goalArrState.get(sourceArrIdx).length);
		}
		
		if(destUsingPtr) {
			destPtrIdx = random.nextInt(goalPtrState.length);
			ArraySpace arrSpace = goalPtrState[destPtrIdx];
			//ArraySpace arrSpace = gameManager.getPtrPointingTo(destPtrIdx);
			destArrIdx = arrSpace.getParentArr().getListIdx();
			destElemIdx = arrSpace.getIdx();
		}
		else {
			destArrIdx = random.nextInt(goalArrState.size());
			destElemIdx = random.nextInt(goalArrState.get(destArrIdx).length);
		}
		
		int temp = goalArrState.get(destArrIdx)[destElemIdx];
		goalArrState.get(destArrIdx)[destElemIdx] = goalArrState.get(sourceArrIdx)[sourceElemIdx];
		goalArrState.get(sourceArrIdx)[sourceElemIdx] = temp;
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		str.append("swap(");
		if(destUsingPtr) {
			str.append("*" + gameManager.getNameFromPtr(destPtrIdx));
		}
		else {
			this.appendFormatedArrNameAndIdx(str, destArrIdx, destElemIdx, usePtrArithm(random, level), true);
		}
		str.append(", ");
		if(sourceUsingPtr) {
			str.append("*" + gameManager.getNameFromPtr(sourcePtrIdx));
		}
		else {
			this.appendFormatedArrNameAndIdx(str, sourceArrIdx, sourceElemIdx, usePtrArithm(random, level), true);
		}
		str.append(")");
		str.append(';');
		this.commandText = str.toString();
	}
}
