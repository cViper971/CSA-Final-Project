import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {
	SmartRectangle backButton;
	SettingsPanel thisPanel = this;
	
	private int startX = 50;
	private int startY = 50;
	private int spacing = 50;
	private int otherPlayerSpacing = 350;
	
	public SettingsPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension (Properties.gridWidth, Properties.gridLength));
		
		// Absolute Positioning: https://docs.oracle.com/javase/tutorial/uiswing/layout/none.html
		setLayout(null);
		backButton = new SmartRectangle(15, 15, 20, 20, 2, 10, new Color[] {Color.BLACK, Color.RED, Color.BLACK}, "X", new Font("Monospace", Font.PLAIN, 32));
		//Gamemode = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 100, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "Gamemode", new Font("Monospace", Font.PLAIN, 32));
		//Instructions = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 200, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "How to Play", new Font("Monospace", Font.PLAIN, 32));
		//Play = new SmartRectangle((Constants.mainWindowWidth - 100) / 2, y + 300, 100, 20, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "Play", new Font("Monospace", Font.PLAIN, 32));
		//setFocusable(true);
		setFocusable(true);
		
		// https://www.javatpoint.com/java-jradiobutton
		
		
		int x = Properties.mainWindowWidth;
		int width = 150;
		int height = 50;
		
		Font f = new Font("Monospace", Font.PLAIN, 20);
		
		JCheckBox outlines = new JCheckBox();
		JCheckBox darkMode = new JCheckBox();
		JTextField boardX = new JTextField();
		
		addCheckBox(outlines, (x - width) / 2, startY - 15, width, 15, f);
		addCheckBox(darkMode, (x - width) / 2, startY + spacing - 15, width, 15, f);
		addTextField(boardX, (x - width) / 2, startY + spacing * 2 - 15, width, 15, f);
		
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
		add(boardX);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		Font f = new Font("Monospace", Font.PLAIN, 20);
		
		g.setFont(f);
		
		g.setColor(Color.BLACK);
		g.drawString("Outlines", startX, startY);
		g.drawString("Dark Mode", startX, startY + spacing);
		g.drawString("Board X", startX, startY + spacing * 2);
		g.drawString("Board Y", startX, startY + spacing * 3);
		
		
		
		/*
		int width = g.getFontMetrics().stringWidth("Keybind Move Left");
		g.drawString("Player 1:", (startX + width) / 2, startY + spacing * 4);
		g.drawString("Player 2:", Properties.mainWindowWidth - (startX + 2 * width) / 2, startY + spacing * 4);
		
		g.drawString("Keybind Move Left", startX, startY + spacing * 5);
		g.drawString("Keybind Move Right", startX, startY + spacing * 6);
		g.drawString("Keybind Rotate", startX, startY + spacing * 7);
		g.drawString("Keybind Hard Drop", startX, startY + spacing * 8);
		g.drawString("Keybind Soft Drop", startX, startY + spacing * 9);
		
		int player2Starting = (startX + 2 * width) / 2 - width / 2;
		
		g.drawString("Keybind Move Left", player2Starting, startY + spacing * 5);
		g.drawString("Keybind Move Right", player2Starting, startY + spacing * 6);
		g.drawString("Keybind Rotate", player2Starting, startY + spacing * 7);
		g.drawString("Keybind Hard Drop", player2Starting, startY + spacing * 8);
		g.drawString("Keybind Soft Drop", player2Starting, startY + spacing * 9);
		
		*/
		//backButton.draw(g);
	}
	
	public void addCheckBox (JCheckBox input, int x, int y, int w, int h, Font f)
	{
		input.setBackground(Color.WHITE);
		input.setBounds(x, y, w, h);
		input.setFont(f);
	}
	
	public void addTextField (JTextField input, int x, int y, int w, int h, Font f)
	{
		input.setBackground(Color.WHITE);
		input.setBounds(x, y, w, h);
		input.setFont(f);
	}
	
	
	
	
	
	
	
}