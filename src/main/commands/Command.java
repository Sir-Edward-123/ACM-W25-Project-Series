package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.managers.GameManager;
import main.spaces.ArraySpace;
import main.spaces.IntSpace;
import main.spaces.PointerSpace;

public abstract class Command {
	protected GameManager gameManager;
	protected ArrayList<int[]> goalArrState;
	protected ArraySpace[] goalPtrState;
	protected String commandText;
	protected int pointValue;
	
	protected abstract void computeGoalState(int level);
	
	public abstract void setup();
	
	public boolean checkGoalReached(ArrayList<int[]> currArrState, ArraySpace[] currPtrState) {
		for(int i = 0; i < currArrState.size(); i++) {
			for(int j = 0; j < currArrState.get(i).length; j++) {
				if(currArrState.get(i)[j] != goalArrState.get(i)[j]) {
					return false;
				}
			}
		}
		for(int i = 0; i < currPtrState.length; i++) {
			if(currPtrState[i] != goalPtrState[i]) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<int[]> getGoalArrState() {
		return goalArrState;
	}
	
	public ArraySpace[] getGoalPtrState() {
		return goalPtrState;
	}
	
	public int getPoints() {
		return pointValue;
	}
	
	public String getCommandText() {
		return commandText;
	}
	
	protected void deepCopyGameStateToGoalState(ArrayList<int[]> currArrState, ArraySpace[] currPtrState) {
		goalArrState = new ArrayList<int[]>();
		for(int i = 0; i < currArrState.size(); i++) {
			int currArrLen = currArrState.get(i).length;
			goalArrState.add(new int[currArrLen]);
			for(int j = 0; j < currArrLen; j++) {
				goalArrState.get(i)[j] = currArrState.get(i)[j];
			}
		}
		
		goalPtrState = new ArraySpace[currPtrState.length];
		for(int i = 0; i < goalPtrState.length; i++) {
			goalPtrState[i] = currPtrState[i];
		}
	}
	
	protected void appendFormatedArrNameAndIdx(StringBuilder str, int arrIdx, int elemIdx, boolean asPtrArithm, boolean deref) {
		if(asPtrArithm) {
			if(deref) {
				str.append("*");
			}
			str.append("(" + gameManager.getNameFromArr(arrIdx));
			str.append("+" + elemIdx + ")");
		}
		else {
			if(!deref) {
				str.append("&");
			}
			str.append(gameManager.getNameFromArr(arrIdx));
			str.append("[" + elemIdx + "]");
		}
	}
	
	protected boolean usePtrArithm(Random random, int level) {
		if(level >= 5) {
			return random.nextBoolean();
		}
		return false;
	}
}
