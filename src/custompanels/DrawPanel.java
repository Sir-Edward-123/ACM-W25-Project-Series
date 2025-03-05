package custompanels;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.managers.GameManager;
import main.managers.VisualManager;

public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 3270259265539820363L;
	
	private GameManager gameManager;
	private VisualManager visualManager;
	
	Timer repaintTimer;
	Timer boxSizeTimer;
	
	private boolean shouldDrawHeldValue;
	private int drawValue;
	private int boxSize = 0;
	
	public DrawPanel(GameManager gameManager, VisualManager visualManager) {
		this.gameManager = gameManager;
		this.visualManager = visualManager;
		this.repaint();
		this.repaintTimer = new Timer(50, e -> this.repaint());
		this.boxSizeTimer = new Timer(2, e -> timerIncBoxSizeEvent());
		repaintTimer.start();
	}
	
	public void setShouldDrawHeldValue(boolean shouldDraw) {
		shouldDrawHeldValue = shouldDraw;
		if(shouldDraw) {
			boxSizeTimer.restart();
		}
		else {
			boxSize = 0;
		}
	}
	
	public void setDrawValue(int val) {
		drawValue = val;
	}
	
	private void timerIncBoxSizeEvent() {
		boxSize += 5;
		if(boxSize >= 30) {
			boxSizeTimer.stop();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(this.getMousePosition() == null) {
			return;
		}
		int x = this.getMousePosition().x;
		int y = this.getMousePosition().y;
		if(shouldDrawHeldValue) {
			g.setColor(visualManager.heldValBoxColor());
			g.fillRect(x - 20, y - 20, (int)(boxSize * 1.5), boxSize);
			if(boxSize >= 30) {
				g.setColor(visualManager.heldValFontColor());
				g.setFont(visualManager.heldValFont());
				if(drawValue < 10) {
					g.drawString("0" + Integer.toString(drawValue), x - 10, y + 3);
				}
				else {
					g.drawString(Integer.toString(drawValue), x - 10, y + 3);
				}
			}
		}
	}
	
}
