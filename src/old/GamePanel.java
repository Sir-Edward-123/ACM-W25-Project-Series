package old;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	ArrayList<Array> arrays = new ArrayList<Array>();
	
	JPanel memory;
	JPanel stack;
	JPanel heap;
	JPanel commandList;
	
	ArraySpace clicked;
	ArraySpace mostRecentlyEntered;
	
	Timer timer = new Timer(100, this);
	
	public GamePanel() {
		this.UIInit();
		//this.setPreferredSize(new Dimension(1000, 800));
	}
	
	private void UIInit() {
		JPanel memory = new JPanel();
		JPanel stack = new JPanel();
		JPanel heap = new JPanel();
		JPanel commandList = new JPanel();
		
		memory.setBackground(new Color(220, 220, 220));
		stack.setBackground(new Color(200, 200, 200));
		heap.setBackground(new Color(200, 200, 200));
		commandList.setBackground(new Color(200, 200, 200));
		commandList.add(new JLabel("Commands here"));
		
		stack.setPreferredSize(new Dimension(ArraySpace.SPACE_WIDTH + 20, 600));
		heap.setPreferredSize(new Dimension(ArraySpace.SPACE_WIDTH * 9 + 20, 600));
		commandList.setPreferredSize(new Dimension(300, 600));
		
		//memory.add(new JLabel("Memory"));
		memory.add(stack);
		//stack.add(new JLabel("Stack"));
		memory.add(heap);
		//heap.add(new JLabel("Heap"));
		this.add(memory);
		this.add(commandList);
		
		arrays.add(new Array(this, 5));
		heap.add(arrays.get(0));
		arrays.add(new Array(this, 8));
		heap.add(arrays.get(1));
		//System.out.println(arrays.get(0).getY());
		
		arrays.get(0).repaint();
		arrays.get(1).repaint();
		
		heap.repaint();
		stack.repaint();
		memory.repaint();
		
		commandList.repaint();
		
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(clicked != null) {
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			g.setColor(new Color(0, 100, 0));
			g.fillRect(x, y, 20, 20);
			g.setColor(new Color(255, 255, 255));
			g.drawString("" + clicked.getValue(), x + 5, y + 5);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == timer) {
			this.repaint();
			timer.restart();
		}
	}
}
