import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainMenu extends JPanel {
	
	public MainMenu ()
	{
		setBackground(Color.WHITE);
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		int y = 200;

		SmartRectangle Settings = new SmartRectangle((Constants.mainWindowWidth - 100) / 2, y, 100, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "Settings", new Font("Monospace", Font.PLAIN, 32));
		SmartRectangle Gamemode = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 100, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "Gamemode", new Font("Monospace", Font.PLAIN, 32));
		SmartRectangle Instructions = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 200, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "How to Play", new Font("Monospace", Font.PLAIN, 32));
		SmartRectangle Play = new SmartRectangle((Constants.mainWindowWidth - 100) / 2, y + 300, 100, 20, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "Play", new Font("Monospace", Font.PLAIN, 32));
		
		Settings.draw(g);
		Gamemode.draw(g);
		Instructions.draw(g);
		Play.draw(g);
	}
	
	
}
