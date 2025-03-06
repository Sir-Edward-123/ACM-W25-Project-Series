package main.managers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import main.commands.Command;
import main.custompanels.*;
import main.Array;
import main.spaces.*;

public class VisualManager {
	//public static final Color TRANSPARENT = new Color(255, 255, 255, 0);
	private static final Color MEMORY_COLOR = new Color(220, 220, 220);
	private static final Color SUB_MEMORY_COLOR = new Color(200, 200, 200);
	private static final Color INFO_PANEL_COLOR = new Color(220, 220, 220);
	private static final Color COMM_LIST_COLOR = new Color(200, 200, 200);
	private static final Color COMM_LIST_DANGER_COLOR = new Color(200, 160, 160, 255);
	private static final Color COMM_BOX_COLOR = new Color(230, 230, 230);
	private static final Color ARRAY_COLOR = new Color(140, 140, 140, 255);
	//private static final Color ARRAY_COLOR_TRANSPARENT = new Color(140, 140, 140, 0);
	private static final Color SPACE_COLOR = new Color(160, 160, 160, 255);
	private static final Color READONLY_SPACE_COLOR = new Color(200, 160, 160, 255);
	//private static final Color SPACE_COLOR_TRANSPARENT = new Color(160, 160, 160, 0);
	private static final Color SPACE_TEXT_COLOR = new Color(50, 50, 50);
	private static final Color BUTTON_COLOR = new Color(180, 180, 180);
	// These colors are for the DrawPanel
	private static final Color HELD_VAL_BOX_COLOR = new Color(100, 120, 100);
	private static final Color HELD_VAL_FONT_COLOR = new Color(40, 80, 40);
	
	private static final Font PANEL_LABEL_FONT = new Font("Consolas", Font.BOLD, 20);
	private static final Font BUTTON_FONT = new Font("Consolas", Font.PLAIN, 20);
	private static final Font INT_FONT = new Font("Consolas", Font.BOLD, 45);
	private static final Font POINTER_FONT = new Font("Consolas", Font.BOLD, 20);
	private static final Font COMMAND_FONT = new Font("Consolas", Font.PLAIN, 15);
	// These fonts are for the DrawPanel
	private static final Font HELD_VAL_FONT = new Font("Consolas", Font.PLAIN, 25);
	
	public static final int WINDOW_WIDTH = 1440;
	public static final int WINDOW_HEIGHT = 810;
	public static final Dimension WINDOW_DIMENSIONS = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	private static final Dimension STACK_DIMENSIONS = new Dimension((int)(1.8 * (WINDOW_WIDTH - 30) / 10), WINDOW_HEIGHT - 20);
	private static final Dimension SUB_STACK_DIMENSIONS = new Dimension(STACK_DIMENSIONS.width / 2 - 10, STACK_DIMENSIONS.height);
	private static final Dimension HEAP_DIMENSIONS = new Dimension((int)(6.2 * (WINDOW_WIDTH - 30) / 10), WINDOW_HEIGHT - 20);
	private static final Dimension INFO_PANEL_DIMENSIONS = new Dimension(2 * (WINDOW_WIDTH - 30) / 10, WINDOW_HEIGHT - 10);
	private static final Dimension COMM_LIST_DIMENSIONS = new Dimension(INFO_PANEL_DIMENSIONS.width - 10, INFO_PANEL_DIMENSIONS.height - 150);
	private static final Dimension COMM_BOX_DIMENSIONS = new Dimension(COMM_LIST_DIMENSIONS.width - 20, COMMAND_FONT.getSize() + 10);
	private static final Dimension SPACE_DIMENSIONS = new Dimension(100, 65);
	private static final Dimension BUTTON_DIMENSIONS = new Dimension(100, 50);
	
	private static final int RIGID_AREA_HEIGHT = 20;
	
	private GameManager gameManager;
	
	private JLayeredPane layeredPane;
	private GamePanel gamePanel;
	private DrawPanel drawPanel;
	private JPanel memory;
	private JPanel stack;
	private JPanel subStackLeft;
	private JPanel subStackRight;
	private JPanel heap;
	private JPanel infoPanel;
	private JPanel commandList;
	private JButton undoButton;
	private JButton resetButton;
	private Queue<JPanel> commandBoxes;
	
	public VisualManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public JPanel startScreen() {
		JPanel startScrn = new JPanel();
		startScrn.setPreferredSize(WINDOW_DIMENSIONS);
		
		// Start screen elems
		startScrn.add(new JLabel("Array Anarchy: A Java game about C++ arrays"));
		JButton button = new JButton("Start");
		button.addActionListener(e -> gameManager.game().startGame());
		startScrn.add(button);
		//
		startScrn.repaint();
		return startScrn;
	}
	
	public void initGameScreen() {
		layeredPane = new JLayeredPane();
		gamePanel = new GamePanel(gameManager, this);
		drawPanel = new DrawPanel(gameManager, this);
		memory = new JPanel();
		stack = new JPanel();
		subStackLeft = new JPanel();
		subStackRight = new JPanel();
		heap = new JPanel();
		infoPanel = new JPanel();
		commandList = new JPanel();
		undoButton = new JButton("Undo");
		resetButton = new JButton("Reset");
		commandBoxes = new LinkedList<JPanel>();
		
		JLabel memoryLabel = new JLabel("Memory");
		JLabel stackLabel = new JLabel("Stack");
		JLabel heapLabel = new JLabel("Heap");
		JLabel commLabel = new JLabel("Command Display");
		memoryLabel.setFont(PANEL_LABEL_FONT);
		stackLabel.setFont(PANEL_LABEL_FONT);
		heapLabel.setFont(PANEL_LABEL_FONT);
		commLabel.setFont(PANEL_LABEL_FONT);
		
		heap.setLayout(new BoxLayout(heap, BoxLayout.Y_AXIS));
		heap.setBorder(new EmptyBorder(10, 10, 10, 5));
		
		layeredPane.setPreferredSize(WINDOW_DIMENSIONS);
		gamePanel.setPreferredSize(WINDOW_DIMENSIONS);
		drawPanel.setPreferredSize(WINDOW_DIMENSIONS);
		stack.setPreferredSize(STACK_DIMENSIONS);
		subStackLeft.setPreferredSize(SUB_STACK_DIMENSIONS);
		subStackRight.setPreferredSize(SUB_STACK_DIMENSIONS);
		heap.setPreferredSize(HEAP_DIMENSIONS);
		infoPanel.setPreferredSize(INFO_PANEL_DIMENSIONS);
		commandList.setPreferredSize(COMM_LIST_DIMENSIONS);
		undoButton.setPreferredSize(BUTTON_DIMENSIONS);
		resetButton.setPreferredSize(BUTTON_DIMENSIONS);
		
		gamePanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		drawPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		drawPanel.setOpaque(false);
		memory.setBackground(MEMORY_COLOR);
		stack.setBackground(SUB_MEMORY_COLOR);
		subStackLeft.setOpaque(false);
		subStackRight.setOpaque(false);
		heap.setBackground(SUB_MEMORY_COLOR);
		infoPanel.setBackground(INFO_PANEL_COLOR);
		commandList.setBackground(COMM_LIST_COLOR);
		undoButton.setBackground(BUTTON_COLOR);
		resetButton.setBackground(BUTTON_COLOR);
		
		undoButton.addActionListener(e -> gameManager.undoMove());
		resetButton.addActionListener(e -> gameManager.resetMoves());
		undoButton.setEnabled(false);
		resetButton.setEnabled(false);
		undoButton.setFont(BUTTON_FONT);
		resetButton.setFont(BUTTON_FONT);
		undoButton.setFocusable(false);
		resetButton.setFocusable(false);
		
		JPanel stackLabelAlignmentPanel = new JPanel();
		stackLabelAlignmentPanel.setPreferredSize(new Dimension(STACK_DIMENSIONS.width, 30));
		stackLabelAlignmentPanel.setOpaque(false);
		stackLabelAlignmentPanel.add(stackLabel);
		
		JPanel heapLabelAlignmentPanel = new JPanel();
		heapLabelAlignmentPanel.setMaximumSize(new Dimension(HEAP_DIMENSIONS.width, 30));
		heapLabelAlignmentPanel.setBackground(SUB_MEMORY_COLOR);
		heapLabelAlignmentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		heapLabelAlignmentPanel.add(heapLabel);
		
		JPanel commandListLabelAlignmentPanel = new JPanel();
		commandListLabelAlignmentPanel.setPreferredSize(new Dimension(INFO_PANEL_DIMENSIONS.width, 30));
		commandListLabelAlignmentPanel.setOpaque(false);
		commandListLabelAlignmentPanel.add(commLabel);
		
		JPanel infoPanelSpaceFiller = new JPanel();
		infoPanelSpaceFiller.setPreferredSize(new Dimension(INFO_PANEL_DIMENSIONS.width - 10, 10));
		infoPanelSpaceFiller.setOpaque(false);
		
		gamePanel.add(memory);
		
		memory.add(stack);
		stack.add(stackLabelAlignmentPanel);
		stack.add(subStackLeft);
		stack.add(subStackRight);
		
		memory.add(heap);
		heap.add(heapLabelAlignmentPanel);
		
		gamePanel.add(infoPanel);
		infoPanel.add(commandList);
		commandList.add(commandListLabelAlignmentPanel);
		infoPanel.add(infoPanelSpaceFiller);
		infoPanel.add(undoButton);
		infoPanel.add(resetButton);
		
		gamePanel.repaint();
		drawPanel.repaint();
		
		layeredPane.add(gamePanel, 1);
		layeredPane.add(drawPanel, 0);
		layeredPane.setVisible(true);
	}
	
	public void drawArr(Array arr) {
		arr.setBackground(ARRAY_COLOR);
		arr.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		for(IntSpace space : arr.getSpaces()) {
			space.setPreferredSize(SPACE_DIMENSIONS);
			space.setBackground(SPACE_COLOR);
			arr.add(space);
		}
		
		// Clean up how to set max size
		int max_size_x = (SPACE_DIMENSIONS.width * arr.length()) + (5 * (arr.length() + 1));
		int max_size_y = SPACE_DIMENSIONS.height + 10;
		arr.setMaximumSize(new Dimension(max_size_x, max_size_y));
		
		heap.add(arr);
		heap.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT)));
		
		//Timer transparencyTimer = new Timer(100, e -> increaseArrayTransparency((Timer) e.getSource(), arr));
		//transparencyTimer.start();
	}
	
	public void drawVariable(VariableSpace space) {
		space.setPreferredSize(SPACE_DIMENSIONS);
		if(space.readOnly()) {
			space.setBackground(READONLY_SPACE_COLOR);
		}
		else {
			space.setBackground(SPACE_COLOR);
		}
		subStackLeft.add(space);
		
		JPanel emptySpace = new JPanel();
		emptySpace.setPreferredSize(new Dimension(SUB_STACK_DIMENSIONS.width, RIGID_AREA_HEIGHT));
		emptySpace.setOpaque(false);
		subStackLeft.add(emptySpace);
	}
	
	public void drawVariablePointer(PointerSpace space) {
		space.setPreferredSize(SPACE_DIMENSIONS);
		if(space.readOnly()) {
			space.setBackground(READONLY_SPACE_COLOR);
		}
		else {
			space.setBackground(SPACE_COLOR);
		}
		subStackLeft.add(space);
		
		JPanel emptySpace = new JPanel();
		emptySpace.setPreferredSize(new Dimension(SUB_STACK_DIMENSIONS.width, RIGID_AREA_HEIGHT));
		emptySpace.setOpaque(false);
		subStackLeft.add(emptySpace);
	}
	
	public void drawArrPointer(PointerSpace space) {
		space.setPreferredSize(SPACE_DIMENSIONS);
		if(space.readOnly()) {
			space.setBackground(READONLY_SPACE_COLOR);
		}
		else {
			space.setBackground(SPACE_COLOR);
		}
		subStackRight.add(space);
		
		JPanel emptySpace = new JPanel();
		emptySpace.setPreferredSize(new Dimension(SUB_STACK_DIMENSIONS.width, RIGID_AREA_HEIGHT));
		emptySpace.setOpaque(false);
		subStackRight.add(emptySpace);
		
		drawPanel.incNumArrows();
	}
	
	/*
	public void increaseArrayTransparency(Timer transparencyTimer, Array arr) {
		Color arrColor = arr.getBackground();
		int red = arrColor.getRed();
		int green = arrColor.getGreen();
		int blue = arrColor.getBlue();
		int alpha = arrColor.getAlpha();
		arr.setBackground(new Color(red, green, blue, alpha + 5));
		
		System.out.println(alpha + 5);
		
		Color spaceColor = arr.getSpaces()[0].getBackground();
		red = spaceColor.getRed();
		green = spaceColor.getGreen();
		blue = spaceColor.getBlue();
		alpha = spaceColor.getAlpha();
		Color newSpaceColor = new Color(red, green, blue, alpha + 5);
		for(IntSpace space : arr.getSpaces()) {
			space.setBackground(newSpaceColor);
		}
		
		if(arr.getBackground().getAlpha() >= 255) {
			transparencyTimer.stop();
		}
		
		//heap.validate();
	}
	*/
	
	public void drawCommand(Command command) {
		JPanel commandBox = new JPanel();
		JPanel invisibleCommandBox = new JPanel();
		
		commandBox.setLayout(new FlowLayout(FlowLayout.LEFT));
		FlowLayout layout = (FlowLayout)(invisibleCommandBox.getLayout());
		layout.setVgap(0);
		layout.setHgap(0);
		
		commandBox.setBackground(COMM_BOX_COLOR);
		invisibleCommandBox.setOpaque(false);

		
		JLabel commandLabel = new JLabel(command.getCommandText());
		commandLabel.setFont(COMMAND_FONT);
		commandBox.add(commandLabel);
		invisibleCommandBox.add(commandBox);
		commandList.add(invisibleCommandBox);
		
		commandBox.setPreferredSize(new Dimension(0, COMM_BOX_DIMENSIONS.height));
		commandBox.setMaximumSize(COMM_BOX_DIMENSIONS);
		invisibleCommandBox.setPreferredSize(COMM_BOX_DIMENSIONS);
		
		commandBoxes.add(invisibleCommandBox);
		
		Timer growTimer = new Timer(5, e -> growCommandBox((Timer) e.getSource(), commandBox));
		growTimer.start();
	}
	
	public void removeCommand() {
		JPanel commandBox = commandBoxes.remove();
		Timer shrinkTimer = new Timer(5, e -> shrinkCommandBox((Timer) e.getSource(), commandBox));
		shrinkTimer.start();
	}
	
	public void growCommandBox(Timer growTimer, JPanel commandBox) {
		Dimension newDimensions = commandBox.getPreferredSize();
		newDimensions.width += 10;
		commandBox.setPreferredSize(newDimensions);
		commandBox.setSize(newDimensions);

		if(commandBox.getPreferredSize().width >= commandBox.getMaximumSize().width) {
			growTimer.stop();
			commandBox.setPreferredSize(commandBox.getMaximumSize());
		}
		commandList.validate();
	}
	
	public void shrinkCommandBox(Timer shrinkTimer, JPanel commandBox) {
		Dimension newDimensions = commandBox.getPreferredSize();
		newDimensions.height -= 2;
		commandBox.setPreferredSize(newDimensions);
		commandBox.setSize(newDimensions);
		
		if(commandBox.getPreferredSize().height <= 0) {
			shrinkTimer.stop();
			commandList.remove(commandBox);
		}
		commandList.validate();
	}
	
	public void flashCommandList() {
		commandList.setBackground(COMM_LIST_DANGER_COLOR);
		Timer flashTimer = new Timer(100, e -> unflashCommandList());
		flashTimer.setRepeats(false);
		flashTimer.start();
	}
	
	public void unflashCommandList() {
		commandList.setBackground(COMM_LIST_COLOR);
	}
	
	public void styleIntJLabel(JLabel label) {
		label.setFont(INT_FONT);
		label.setForeground(SPACE_TEXT_COLOR);
	}
	
	public void stylePointerJLabel(JLabel label) {
		label.setFont(POINTER_FONT);
		label.setForeground(SPACE_TEXT_COLOR);
	}
	
	public void setUndoButtonEnabled(boolean enabled) {
		undoButton.setEnabled(enabled);
	}
	
	public void setResetButtonEnabled(boolean enabled) {
		resetButton.setEnabled(enabled);
	}
	
	public void reportVisualMouseEvent(IntSpace reportedSpace, MouseEventType e) {
		if(e == MouseEventType.MOUSE_PRESSED) {
			drawPanel.setDrawValue(reportedSpace.getValue());
			drawPanel.setShouldDrawHeldValue(true);
		}
		else if(e == MouseEventType.MOUSE_RELEASED) {
			drawPanel.setShouldDrawHeldValue(false);
		}
	}
	
	public void reportVisualMouseEvent(PointerSpace pointerSpace, MouseEventType e) {
		if(e == MouseEventType.MOUSE_PRESSED) {
			drawPanel.setShouldDrawPointerArrow(true);
		}
		else if(e == MouseEventType.MOUSE_RELEASED) {
			drawPanel.setShouldDrawPointerArrow(false);
		}
	}

	public JLayeredPane gameScreen() {
		return layeredPane;
	}
	
	public Dimension spaceDimensions() {
		return SPACE_DIMENSIONS;
	}
	
	public Color heldValBoxColor() {
		return HELD_VAL_BOX_COLOR;
	}
	
	public Color heldValFontColor() {
		return HELD_VAL_FONT_COLOR;
	}
	
	public Font heldValFont() {
		return HELD_VAL_FONT;
	}
	
	/*
	public Font fontPanelLabel() {
		return PANEL_LABEL_FONT;
	}
	
	public Font fontArr() {
		return ARR_FONT;
	}
	*/
	
}
