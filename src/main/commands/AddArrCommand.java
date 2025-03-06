package main.commands;

import java.util.ArrayList;
import java.util.Random;

import main.Array;
import main.managers.GameManager;
import main.spaces.ArraySpace;

public class AddArrCommand extends Command{
	Array newArr;
	
	public AddArrCommand(GameManager gameManager, ArrayList<int[]> startArrState, ArraySpace[] startPtrState) {
		this.gameManager = gameManager;
		this.deepCopyGameStateToGoalState(startArrState, startPtrState);
		this.pointValue = 0;
		this.computeGoalState(0); // We don't care about level for this
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		gameManager.addArray(newArr);
	}

	@Override
	protected void computeGoalState(int level) {
		Random random = new Random();
		int size = random.nextInt(3, 9);
		newArr = new Array(gameManager, size, goalArrState.size());
		goalArrState.add(newArr.getValues());
		
		StringBuilder str = new StringBuilder();
		str.append("int *" + newArr.getName());
		str.append(" = ");
		str.append("new int[" + size + "]");
		str.append(';');
		this.commandText = str.toString();
	}
	
}
