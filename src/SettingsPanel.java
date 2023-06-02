import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {
	Button backButton;
	SettingsPanel thisPanel = this;
	
	private int startX = 30;
	private int startY = 150;
	private int spacing = 50;
	private int otherPlayerSpacing = 100;
	private int maxWidth = 0;
	
	public SettingsPanel() {
		setBackground(new Color(120, 196, 148));
		setPreferredSize(new Dimension (Properties.gridWidth, Properties.gridLength));
		
		// Absolute Positioning: https://docs.oracle.com/javase/tutorial/uiswing/layout/none.html
		setLayout(null);
		backButton = new Button(Properties.mainWindowWidth / 2 - 10, startY + spacing * 10, 20, 20, 2, 10, new Color[] {Color.BLACK, Color.RED, Color.BLACK}, "X", new Font("Monospace", Font.PLAIN, 32));
		setFocusable(true);
		
		// https://www.javatpoint.com/java-jradiobutton
		
		
		int x = Properties.mainWindowWidth;
		int width = 150;
		int height = 50;
		
		Font f = new Font("Monospace", Font.PLAIN, 20);
		
		JCheckBox outlines = new JCheckBox();
		outlines.setSelected(Properties.colorOutlines);
		JCheckBox darkMode = new JCheckBox();
		darkMode.setSelected(Properties.darkMode);
		
		addCheckBox(outlines, (x - width) / 2, startY - 15, 20, 20, f);
		addCheckBox(darkMode, (x - width) / 2, startY + spacing - 15, 20, 20, f);
		
		outlines.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Properties.colorOutlines = outlines.isSelected();
			}
		});
		
		darkMode.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Properties.darkMode = darkMode.isSelected();
			}
		});
				
		
		add(outlines);
		add(darkMode);
		
		// Text Fields
		int textWidth = 80;

		int relIndex = 4;
		
		// For each text field we attach a listener for all of them. Half of the textfields are shifted over for player 2.
		for (int i = 0; i < 10; i++)
		{
			String keyName = KeyEvent.getKeyText(getKeyCodeFromVariable(i));

			JTextField option = new JTextField(keyName);

			if (i < 5)
			{
				addTextField(option, startX + 200, startY + spacing * relIndex - 23, textWidth, 30, f, i);
			} else {
				if (i == 5)
				{
					relIndex = 4;
				}
				addTextField(option, startX + 525, startY + spacing * relIndex - 23, textWidth, 30, f, i);
			}
			add(option);
			relIndex += 1;
		}
		
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		g.setColor(Color.BLACK);
		g.fillRect(12,97,676,606);
		g.setColor(Color.WHITE);
		g.fillRect(15,100,670,600);
		
		g.setColor(Color.BLACK);
		Font fTitle = new Font("Monospace", Font.BOLD, 32);
		g.setFont(fTitle);
		int titleWidth = g.getFontMetrics().stringWidth("Settings");

		g.drawString("Settings", (Properties.mainWindowWidth - titleWidth) / 2, 60);
		
		Font f = new Font("Monospace", Font.PLAIN, 20);
		
		g.setFont(f);
		
		g.setColor(Color.BLACK);
		g.drawString("Outlines", startX, startY);
		g.drawString("Dark Mode", startX, startY + spacing);
		
		
		
		maxWidth = g.getFontMetrics().stringWidth("Keybind Move Left");
		int width = g.getFontMetrics().stringWidth("Keybind Move Left");
		g.drawString("Player 1:", (startX + width) / 2, startY + spacing * 3);
		g.drawString("Player 2:", Properties.mainWindowWidth - (startX + 2 * width) / 2 - 50, startY + spacing * 3);
		
		g.drawString("Keybind Move Left", startX, startY + spacing * 4);
		g.drawString("Keybind Move Right", startX, startY + spacing * 5);
		g.drawString("Keybind Rotate", startX, startY + spacing * 6);
		g.drawString("Keybind Soft Drop", startX, startY + spacing * 7);
		g.drawString("Keybind Hard Drop", startX, startY + spacing * 8);
		
		int player2Starting =  Properties.mainWindowWidth - (startX + 2 * width) / 2 - (width - g.getFontMetrics().stringWidth("Player 2")) / 2 - 110;
		
		g.drawString("Keybind Move Left", player2Starting, startY + spacing * 4);
		g.drawString("Keybind Move Right", player2Starting, startY + spacing * 5);
		g.drawString("Keybind Rotate", player2Starting, startY + spacing * 6);
		g.drawString("Keybind Soft Drop", player2Starting, startY + spacing * 7);
		g.drawString("Keybind Hard Drop", player2Starting, startY + spacing * 8);
		
		
		backButton.draw(g);
	}
	
	public void addCheckBox (JCheckBox input, int x, int y, int w, int h, Font f)
	{
		input.setBackground(Color.WHITE);
		input.setBounds(x, y, w, h);
		input.setFont(f);
	}
	
	public void addSlider (JSlider input, int x, int y, int w, int h, int minor, int major, Color col)
	{
		input.setBounds(x, y, w, h);
		input.setBackground(col);
		input.setSnapToTicks(true);
		input.setPaintTicks(true);
		input.setPaintLabels(true);
		input.setMinorTickSpacing(minor);
		input.setMajorTickSpacing(major);
	}
	
	public void addTextField (JTextField input, int x, int y, int w, int h, Font f, int variableIndex)
	{
		input.setBackground(Color.WHITE);
		input.setBounds(x, y, w, h);
		input.setFont(f);
		
		input.addKeyListener(new KeyAdapter () {

			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String keyName = e.getKeyText(e.getKeyCode());
				
				if (keyName.indexOf("Unknown keyCode") == -1)
				{
					input.setText("");
					input.setText(keyName);
					setVariableIndex(variableIndex, e.getKeyCode());
				}
			}
			
			
		});
	}
	
	public void setVariableIndex (int variableIndex, int val)
	{
		switch (variableIndex)
		{
			case 0:
				Properties.player1Left = val;
				break;
				
			case 1:
				Properties.player1Right = val;
				break;
				
			case 2:
				Properties.player1Rotate = val;
				break;
				
			case 3:
				Properties.player1Soft = val;
				break;
				
			case 4:
				Properties.player1Hard = val;
				break;
				
			case 5:
				Properties.player2Left = val;
				break;
				
			case 6:
				Properties.player2Right = val;
				break;
				
			case 7:
				Properties.player2Rotate = val;
				break;
				
			case 8:
				Properties.player2Soft = val;
				break;
				
			case 9:
				Properties.player2Hard = val;
				break;
				
		}
	}
	
	public int getKeyCodeFromVariable (int variableIndex)
	{
		switch (variableIndex)
		{
		case 0:
			return Properties.player1Left;
			
		case 1:
			return Properties.player1Right;
			
		case 2:
			return Properties.player1Rotate;
			
		case 3:
			return Properties.player1Soft;
			
		case 4:
			return Properties.player1Hard;
			
		case 5:
			return Properties.player2Left;
			
		case 6:
			return Properties.player2Right;
			
		case 7:
			return Properties.player2Rotate;
			
		case 8:
			return Properties.player2Soft;
			
		case 9:
			return Properties.player2Hard;
		}
		
		return -1;
	}
}