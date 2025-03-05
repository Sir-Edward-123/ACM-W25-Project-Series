package IntSpaces;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.managers.GameManager;
import main.managers.MouseEventType;

public abstract class IntSpace extends JPanel implements MouseListener{
	private static final long serialVersionUID = -7872764423985816177L;
	
	protected GameManager gameManager;
	protected int value;
	protected boolean readOnly;
	
	protected JLabel valueDisp;
	
	protected void setup() {
		valueDisp = new JLabel();
		gameManager.visualManager().styleArrJLabel(valueDisp);
		this.updateDispValue();
		
		JPanel numberLabelAlignmentPanel = new JPanel();
		numberLabelAlignmentPanel.setPreferredSize(new Dimension(gameManager.visualManager().intSpaceDimensions().width, 40));
		numberLabelAlignmentPanel.setOpaque(false);
		numberLabelAlignmentPanel.add(valueDisp);
		this.add(numberLabelAlignmentPanel);
		this.addMouseListener(this);
	}
	
	
	public int getValue() {
		return value;
	}
	
	public abstract void setValue(int value);
	
	public boolean readOnly() {
		return readOnly;
	}
	
	protected void updateDispValue() {
		valueDisp.setText(Integer.toString(value));
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_PRESSED);
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_RELEASED);
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_ENTERED);
	}


	@Override
	public void mouseExited(MouseEvent e) {
		gameManager.reportMouseEvent(this, MouseEventType.MOUSE_EXITED);
	}
}
