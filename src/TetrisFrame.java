import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TetrisFrame extends JFrame {
	
	private MainMenu main;
	private GameSideBar sideBar;
	private GamePanel panel;
	private JPanel container;
	
	/*
		
		0: MainMenu
		1: Game
		2: 
	*/
	private int panelNum;
	
	// Testing mode boots you straight into the game
	public TetrisFrame (String window, boolean testingMode)
	{
		super(window);
		panelNum = 0;
		setPreferredSize(new Dimension(Constants.mainWindowWidth + 16, Constants.mainWindowHeight + Constants.blockSize + 9));
		
        if (testingMode)
        {
        	initalizeGame();
        	panelNum = 1;
        } else {
        	main = new MainMenu();
        	getContentPane().add(main);
        	addMouseListener(new ClickListener());
        }
        pack();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
	}
	
	private class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (panelNum == 0)
			{
				int mX = MouseInfo.getPointerInfo().getLocation().x - main.getLocationOnScreen().x;
				int mY = MouseInfo.getPointerInfo().getLocation().y - main.getLocationOnScreen().y;
				
				if (main.singlePlay.isMouseInside(mX, mY))
				{
					getContentPane().remove(main);
					
					getContentPane().setFocusable(false);
					initalizeGame();
					getContentPane().revalidate();
					
					panelNum = 1;
			        
				}
					
				if (main.Settings.isMouseInside(mX, mY))
				{
					getContentPane().remove(main);
					
					getContentPane().setFocusable(false);
					loadSettings();
					getContentPane().revalidate();
					
					panelNum = 2;
				}
			}
			if (panelNum == 1&&panel.gameOver==true)
			{
				int mX = MouseInfo.getPointerInfo().getLocation().x - panel.getLocationOnScreen().x;
				int mY = MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y;
				
				if (panel.playYes.isMouseInside(mX, mY))
				{
					getContentPane().remove(panel);
					
					getContentPane().setFocusable(false);
					initalizeGame();
					getContentPane().revalidate();
				}
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
	
	public void initalizeGame ()
	{
		int width = Constants.mainWindowWidth - Constants.blockSize*Constants.gridWidth;
        int height = Constants.blockSize*Constants.gridLength;
        
		sideBar = new GameSideBar(width, height);
		sideBar.setPreferredSize(new Dimension(width, height));
        sideBar.setBounds(Constants.blockSize*Constants.gridWidth, 0, Constants.mainWindowWidth, Constants.blockSize*Constants.gridLength);
        
		panel = new GamePanel(sideBar);
		
//		https://stackoverflow.com/questions/1082504/requesting-focus-in-window
		SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				panel.requestFocus();
				panel.requestFocusInWindow();
				panel.requestFocus(true);
				panel.setBackground(Color.BLACK);
		        panel.setPreferredSize(new Dimension(Constants.blockSize*Constants.gridWidth,Constants.blockSize*Constants.gridLength));
		        panel.setBounds(0, 0, Constants.blockSize*Constants.gridWidth, Constants.blockSize*Constants.gridLength);
				
			}
		});
		 
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        
        container.add(panel);
        container.add(sideBar);
        getContentPane().add(container);
        
       
        container.repaint();
        sideBar.repaint();
        
	}
	
	public void loadSettings() {
		SettingsPanel page = new SettingsPanel();
		getContentPane().add(page);
		
	}
}
