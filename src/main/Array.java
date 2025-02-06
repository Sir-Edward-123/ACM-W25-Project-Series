package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Array extends JComponent {
	
	final int ARRAY_SIZE;
	final int BORDER_WEIGHT = 10;
	int arrayWidth;
	int arrayHeight;
	
	ArraySpace[] spaces;
	
	public Array(int x, int y, int size) {
		
		this.ARRAY_SIZE = size;
		this.spaces = new ArraySpace[ARRAY_SIZE];
		
		this.arrayWidth = BORDER_WEIGHT;
		for(int i = 0; i < ARRAY_SIZE; i++) {
			spaces[i] = new ArraySpace(arrayWidth, BORDER_WEIGHT, 0);
			arrayWidth += ArraySpace.getWidth() + BORDER_WEIGHT;
		}
		this.arrayHeight = 2 * BORDER_WEIGHT + ArraySpace.getHeight();
		
		this.setPreferredSize(new Dimension(arrayWidth, arrayHeight));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, arrayWidth, arrayHeight);
		g.setColor(Color.BLUE);
		for(int i = 0; i < ARRAY_SIZE; i++) {
			ArraySpace curr = spaces[i];
			g.fillRect(curr.getX(), curr.getY(), ArraySpace.getWidth(), ArraySpace.getHeight());
		}
	}
	
}
