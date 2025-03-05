package old;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.JButton;

public class Game {
	JFrame frame;
	JPanel startScreenPanel; // TEMPORARY
	GamePanel gamePanel;
	//MyGlassPane glassPane;
	
	public Game() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.startScreen();
	}
	
	// THIS START SCREEN IS VERY TEMPORARY, CLEAN IT UP LATER
	public void startScreen() {
		startScreenPanel = new JPanel(); // TEMPORARY
		startScreenPanel.setPreferredSize(new Dimension(1000, 800));
		frame.add(startScreenPanel);
		startScreenPanel.add(new JLabel("Start Screen Placeholder"));
		JButton button = new JButton("Start");
		button.addActionListener(e -> start());
		startScreenPanel.add(button);
		startScreenPanel.repaint();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void start() {
		frame.remove(startScreenPanel);
		
		gamePanel = new GamePanel();
		frame.add(gamePanel);
		gamePanel.repaint();
		frame.pack();
	}
}
