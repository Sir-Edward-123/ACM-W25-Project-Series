package main.commands;

import java.util.ArrayList;

import main.managers.GameManager;

public abstract class Command {
	protected GameManager gameManager;
	protected ArrayList<int[]> goalState = new ArrayList<int[]>();
	protected String commandText;
	
	protected abstract void computeGoalState();
	
	public abstract void setup();
	
	public boolean checkGoalReached(ArrayList<int[]> currState) {
		for(int i = 0; i < currState.size(); i++) {
			for(int j = 0; j < currState.get(i).length; j++) {
				if(currState.get(i)[j] != goalState.get(i)[j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public ArrayList<int[]> getGoalState() {
		return goalState;
	}
	
	public String getCommandText() {
		return commandText;
	}
	
	protected void deepCopyGameStateToGoalState(ArrayList<int[]> currState) {
		for(int i = 0; i < currState.size(); i++) {
			int currArrLen = currState.get(i).length;
			goalState.add(new int[currArrLen]);
			for(int j = 0; j < currArrLen; j++) {
				goalState.get(i)[j] = currState.get(i)[j];
			}
		}
	}
}
