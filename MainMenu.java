import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainMenu extends JPanel {
	
	public SmartRectangle Settings, singlePlay, twoPlay;

	public MainMenu ()
	{
		setBackground(Color.WHITE);
		
		int y = 200;
	
		Settings = new SmartRectangle(Constants.mainWindowWidth - 115, 17, 100, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "Settings", new Font("Monospace", Font.PLAIN, 32));
		singlePlay = new SmartRectangle((Constants.mainWindowWidth - 250) / 2, y + 200, 250, 30, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "Single Player", new Font("Monospace", Font.PLAIN, 40));
		twoPlay = new SmartRectangle((Constants.mainWindowWidth - 250) / 2, y + 300, 250, 30, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "Two Play", new Font("Monospace", Font.PLAIN, 40));
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.RED);
		g.setFont(new Font ("Monospace", Font.BOLD, 50));
		int offset = g.getFontMetrics().stringWidth("TETRIS!!!");
		g.drawString("TETRIS!!!", (Constants.mainWindowWidth - offset) / 2, 150);
		
		Settings.draw(g);
		singlePlay.draw(g);
		twoPlay.draw(g);
	}


}