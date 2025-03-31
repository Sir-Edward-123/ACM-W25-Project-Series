package main.spaces;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.managers.GameManager;
import main.managers.MouseEventType;

public class PointerSpace extends Space implements MouseListener {
	private static final long serialVersionUID = -5489542104657810875L;
	
	String name;
	ArraySpace arrSpacePointingTo;
	
	int pointerListIdx;
	
	JLabel info;
	
	public PointerSpace(GameManager manager, String name, ArraySpace pointTo, boolean readOnly, int pointerListIdx) {
		this.gameManager = manager;
		this.name = name;
		this.arrSpacePointingTo = pointTo;
		this.readOnly = readOnly;
		this.pointerListIdx = pointerListIdx;
		
		this.spaceSetup();
		this.pointerSetup();
	}
	
	private void pointerSetup() {
		gameManager.visualManager().stylePointerJLabel(valueDisp);
		
		info = new JLabel("int *" + name);
		/*
		if(readOnly) {
			info.setText(info.getText() + " (r-only)");
		}
		*/
		this.add(info);
		
		this.addMouseListener(this);
	}
	
	public ArraySpace getPointingTo() {
		return arrSpacePointingTo;
	}
	
	public void setValue(ArraySpace pointTo) {
		if(readOnly) {
			return;
		}
		this.arrSpacePointingTo = pointTo;
		this.updateDispValue();
		gameManager.updatePtrState(pointerListIdx, pointTo);
	}
	
	public int getPtrListIdx() {
		return pointerListIdx;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	protected void updateDispValue() {
		StringBuilder str = new StringBuilder();
		str.append(arrSpacePointingTo.getParentArr().getName());
		str.append("[" + arrSpacePointingTo.getIdx() + "]");
		valueDisp.setText(str.toString());
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
