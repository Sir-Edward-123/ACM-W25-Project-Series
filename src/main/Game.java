package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.managers.GameManager;

public class Game {
	private JFrame gameFrame;
	private GameManager gameManager;
	
	public Game() {
		gameFrame = new JFrame();
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameManager = new GameManager(this);
		
		gameFrame.add(gameManager.visualManager().startScreen());
		gameFrame.repaint();
		gameFrame.pack();
		gameFrame.setVisible(true);
	}
	
	public void startGame() {
		gameFrame.getContentPane().removeAll();
		gameManager.start();
		gameFrame.add(gameManager.visualManager().gameScreen());
		gameFrame.repaint();
		gameFrame.pack();
	}
	
	public void lose() {
		JOptionPane.showMessageDialog(gameFrame, "Time Limit Exceeded: You Lose!");
		reset();
	}
	
	public void reset() {
		gameFrame.getContentPane().removeAll();
		
		gameManager = new GameManager(this);
		
		gameFrame.add(gameManager.visualManager().startScreen());
		gameFrame.repaint();
		gameFrame.pack();
		gameFrame.setVisible(true);
	}
}
