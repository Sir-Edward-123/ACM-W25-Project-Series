package main;

import javax.swing.JFrame;

public class Game {
	JFrame frame = new JFrame("Array Anarchy");
	GamePanel panel = new GamePanel();
	
	public Game() {
		frame.add(panel);
		panel.repaint();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void start() {
		
	}
}
