package main.custompanels;

import javax.swing.JPanel;

import main.managers.GameManager;
import main.managers.VisualManager;

public class GamePanel extends JPanel {
	private GameManager gameManager;
	private VisualManager visualManger;
	
	// Maybe put stuff like memory in here
	
	public GamePanel(GameManager gameManager, VisualManager visualManager) {
		this.gameManager = gameManager;
		this.visualManger = visualManager;
	}
	
	// TODO: TEST IF I CAN PAINT OVER COMPONENTS IN GAMEPANEL BY OVERRIDING PAINTCOMPONENT
	// MAYBE TRY PAINTING CHILD ELEMS FIRST, THEN PAINTING STUFF ON TOP
}
