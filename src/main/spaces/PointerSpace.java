package main.spaces;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.managers.GameManager;
import main.managers.MouseEventType;

public class PointerSpace extends JPanel implements MouseListener {
	private static final long serialVersionUID = -5489542104657810875L;
	
	GameManager gameManager;
	
	String name;
	ArraySpace arrSpacePointingTo;
	
	JLabel valueDisp;
	
	public PointerSpace(GameManager manager, String name, ArraySpace pointTo) {
		this.gameManager = manager;
		this.name = name;
		this.arrSpacePointingTo = pointTo;
		
		valueDisp = new JLabel();
		//gameManager.visualManager().styleArrJLabel(valueDisp);
		this.updateDispValue();
		
		JPanel nameLabelAlignmentPanel = new JPanel();
		nameLabelAlignmentPanel.setPreferredSize(new Dimension(gameManager.visualManager().spaceDimensions().width, 40));
		nameLabelAlignmentPanel.setOpaque(false);
		nameLabelAlignmentPanel.add(valueDisp);
		this.add(nameLabelAlignmentPanel);
		this.addMouseListener(this);
		
		this.addMouseListener(this);
	}
	
	protected void updateDispValue() {
		valueDisp.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_RELEASED);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
