package main.spaces;

import javax.swing.JLabel;

import main.managers.GameManager;

public class VariableSpace extends IntSpace{
	private static final long serialVersionUID = -1095326806907417423L;
	
	private String name;
	
	private JLabel info;

	// Variable Constructor
	public VariableSpace(GameManager manager, String name, int value, boolean readOnly) {
		this.gameManager = manager;
		this.name = name;
		this.value = value;
		this.readOnly = readOnly;
		
		this.spaceSetup();
		this.variableSetup();
	}
	
	private void variableSetup() {
		gameManager.visualManager().styleIntJLabel(valueDisp);
		
		info = new JLabel("int " + name);
		/*
		if(readOnly) {
			info.setText(info.getText() + " (r-only)");
		}
		*/
		this.add(info);
		
		this.addMouseListener(this);
	}
	
	public void setValue(int value) {
		if(readOnly) {
			return;
		}
		this.value = value;
		this.updateDispValue();
	}
	
	// Use only when the game has to set a readonly value
	public void gameSetValue(int value) {
		this.value = value;
		this.updateDispValue();
	}
	
	public String getName() {
		return name;
	}
	
}
