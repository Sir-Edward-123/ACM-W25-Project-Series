package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;
import main.spaces.PointerSpace;

public class CopyCommand extends Command {

	public CopyCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState, int level) {
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
		
		goalArrState.get(destArrIdx)[destElemIdx] = goalArrState.get(sourceArrIdx)[sourceElemIdx];
		
		// Generate command text
		StringBuilder str = new StringBuilder();
		if(destUsingPtr) {
			str.append("*" + gameManager.getNameFromPtr(destPtrIdx));
		}
		else {
			this.appendFormatedArrNameAndIdx(str, destArrIdx, destElemIdx, usePtrArithm(random, level), true);
		}
		str.append(" = ");
		if(sourceUsingPtr) {
			str.append("*" + gameManager.getNameFromPtr(sourcePtrIdx));
		}
		else {
			this.appendFormatedArrNameAndIdx(str, sourceArrIdx, sourceElemIdx, usePtrArithm(random, level), true);
		}
		str.append(';');
		this.commandText = str.toString();
	}
	
}
