package main.spaces;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.managers.GameManager;

public abstract class Space extends JPanel {
	private static final long serialVersionUID = -6394259396830099875L;
	
	protected GameManager gameManager;
	protected boolean readOnly;
	
	protected JLabel valueDisp;
	
	protected void spaceSetup() {
		valueDisp = new JLabel();
		this.updateDispValue();
		
		JPanel labelAlignmentPanel = new JPanel();
		labelAlignmentPanel.setPreferredSize(new Dimension(gameManager.visualManager().spaceDimensions().width, 40));
		labelAlignmentPanel.setOpaque(false);
		labelAlignmentPanel.add(valueDisp);
		this.add(labelAlignmentPanel);
	}
	
	protected abstract void updateDispValue();
	
	public boolean readOnly() {
		return readOnly;
	}
}
