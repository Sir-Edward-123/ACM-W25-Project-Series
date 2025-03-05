package old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Array extends JPanel {
	
	final int ARRAY_SIZE;
	final int BORDER_WEIGHT = 5;
	int arrayWidth;
	int arrayHeight;
	
	GamePanel gamePanel;
	
	ArraySpace[] spaces;
	
	public Array(GamePanel panel, int size) {
		
		gamePanel = panel;
		
		this.ARRAY_SIZE = size;
		this.spaces = new ArraySpace[ARRAY_SIZE];
		
		//this.arrayWidth = BORDER_WEIGHT;
		Random random = new Random();
		for(int i = 0; i < ARRAY_SIZE; i++) {
			spaces[i] = new ArraySpace(this, random.nextInt(100));
			this.add(spaces[i]);
			spaces[i].repaint();
			//arrayWidth += ArraySpace.SPACE_WIDTH + BORDER_WEIGHT;
		}
		//this.arrayHeight = 2 * BORDER_WEIGHT + ArraySpace.SPACE_HEIGHT;
		
		//this.setPreferredSize(new Dimension(arrayWidth, arrayHeight));
		setBackground(new Color(140, 140, 140));
	}
	
	public void passUpMouseEvent(ArraySpace space, int type) {
		if(type == 1) {
			gamePanel.clicked = space;
			System.out.println("" + space.getValue());
		}
		if(type == 2) {
			gamePanel.mostRecentlyEntered = space;
		}
		if(type == 3) {
			if(gamePanel.mostRecentlyEntered == null) {
				gamePanel.clicked = null;
				return;
			}
			int value = gamePanel.clicked.getValue();
			gamePanel.mostRecentlyEntered.setValue(value);
			gamePanel.mostRecentlyEntered.valueLabel.setText(value + "");
			gamePanel.clicked = null;
		}
		if(type == 4) {
			gamePanel.mostRecentlyEntered = null;
		}
	}
	
	/*
	@Override
	public void paintComponent(Graphics g) {
		//g.setColor(new Color(140, 140, 140));
		//g.fillRect(0, 0, arrayWidth, arrayHeight);
		//g.setColor(new Color(160, 160, 160));
		//for(int i = 0; i < ARRAY_SIZE; i++) {
			//ArraySpace curr = spaces[i];
			//g.fillRect(curr.getX(), curr.getY(), ArraySpace.SPACE_WIDTH, ArraySpace.SPACE_HEIGHT);
			//spaces[i].repaint();
		//}
	}
	*/
	
}
