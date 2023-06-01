import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
	SmartRectangle backButton;
	SettingsPanel thisPanel = this;
	public SettingsPanel() {
		backButton = new SmartRectangle(15, 15, 20, 20, 2, 10, new Color[] {Color.BLACK, Color.RED, Color.BLACK}, "X", new Font("Monospace", Font.PLAIN, 32));
		//Gamemode = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 100, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "Gamemode", new Font("Monospace", Font.PLAIN, 32));
		//Instructions = new SmartRectangle((Constants.mainWindowWidth - 150) / 2, y + 200, 150, 20, 5, 10, new Color[] {Color.BLACK, Color.WHITE, Color.BLACK}, "How to Play", new Font("Monospace", Font.PLAIN, 32));
		//Play = new SmartRectangle((Constants.mainWindowWidth - 100) / 2, y + 300, 100, 20, 5, 10, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "Play", new Font("Monospace", Font.PLAIN, 32));
		//setFocusable(true);
		addMouseListener(new ClickListener());
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		backButton.draw(g);
	}
	
	private class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int mX = e.getX();
			int mY = e.getY();
			
			if (backButton.isMouseInside(mX, mY))
			{
				System.out.println(thisPanel.getRootPane());
				
				//getRootPane().setFocusable(false);
				//returnToMain();
				//getRootPane().revalidate();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
