package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.util.ArrayList;

public class GamePanel extends JPanel{
	ArrayList<Array> arrays = new ArrayList<Array>();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(1000, 800));
		arrays.add(new Array(30, 30, 5));
		this.add(arrays.get(0));
		//arrays.get(0).setLocation(100, 100);
		//SpringLayout layout = new SpringLayout();
		//this.setLayout(layout);
		//layout.putConstraint(SpringLayout.WEST, arrays.get(0), 20, SpringLayout.WEST, this);
		//layout.putConstraint(SpringLayout.NORTH, arrays.get(0), 20, SpringLayout.NORTH, this);
		System.out.println(arrays.get(0).getY());
	}

	@Override
	public void paintComponent(Graphics g) {
		arrays.get(0).repaint();
	}
}
