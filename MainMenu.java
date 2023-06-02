import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;	

import javax.swing.JPanel;

public class MainMenu extends JPanel {
	
	public Button Settings, singlePlay, twoPlay;

	public MainMenu ()
	{
		setBackground(new Color(65, 121, 196));
		
		int y = 200;
	
		Settings = new Button(Properties.mainWindowWidth - 115, 17, 100, 20, 3, 10, new Color[] {Color.BLACK, new Color(159, 160, 161), Color.BLACK}, "Settings", new Font("Monospace", Font.PLAIN, 32));
		singlePlay = new Button((Properties.mainWindowWidth - 250) / 2, y + 200, 250, 30, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "One Player", new Font("Monospace", Font.PLAIN, 40));
		twoPlay = new Button((Properties.mainWindowWidth - 250) / 2, y + 300, 250, 30, 5, 10, new Color[] {new Color(140, 49, 58), new Color(230, 83, 97), Color.BLACK}, "Two Player", new Font("Monospace", Font.PLAIN, 40));
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font ("Monospace", Font.BOLD, 90));
		int offset = g.getFontMetrics().stringWidth("TETRIS");
		g.drawString("TETRIS", (Properties.mainWindowWidth - offset) / 2, 260);
		
		g.setFont(new Font ("Monospace", Font.PLAIN, 20));
		offset = g.getFontMetrics().stringWidth("By: Vivek Chandy and Anthony Fedewa");
		g.drawString("By: Vivek Chandy and Anthony Fedewa", (Properties.mainWindowWidth - offset) / 2, 300);
		
		Settings.draw(g);
		singlePlay.draw(g);
		twoPlay.draw(g);
	}


}