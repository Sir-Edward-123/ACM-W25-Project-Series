package old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArraySpace extends JPanel implements MouseListener {
	Array parentArr;
	JLabel valueLabel;
	private int value;
	
	static final int SPACE_WIDTH = 80;
	static final int SPACE_HEIGHT = 50;
	
	public ArraySpace(Array parent, int value) {
		parentArr = parent;
		this.value = value;
		this.setBackground(new Color(160, 160, 160));
		this.setPreferredSize(new Dimension(SPACE_WIDTH, SPACE_HEIGHT));
		this.addMouseListener(this);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setAlignmentX(CENTER_ALIGNMENT);
		//this.setAlignmentY(CENTER_ALIGNMENT);
		valueLabel = new JLabel(value + "");
		this.add(valueLabel);
	}
	
	
	int getValue() {
		return value;
	}
	
	void setValue(int value) {
		this.value = value;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(value + "pressed");
		parentArr.passUpMouseEvent(this, 1);
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(value + "released");
		parentArr.passUpMouseEvent(this, 3);
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(value + "entered");
		parentArr.passUpMouseEvent(this, 2);
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(value + "exited");
		parentArr.passUpMouseEvent(this, 4);
		
	}
	
	/*
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(160, 160, 160));
		
	}
	*/
}
