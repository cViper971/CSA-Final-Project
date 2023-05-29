import java.awt.Color;
import java.awt.Dimension;
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
		setSize(Constants.mainWindowWidth + 16, Constants.mainWindowHeight + Constants.blockSize + 9);
		
        if (testingMode)
        {
        	initalizeGame();
        	panelNum = 1;
        } else {
        	main = new MainMenu();
        	getContentPane().add(main);
        	addMouseListener(new ClickListener());
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        
	}
	
	private class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (panelNum != 0)
			{
				return;
			}
			
			
			
			int mX = e.getX();
			int mY = e.getY();
			
			if (main.Play.isMouseInside(mX, mY))
			{
				System.out.println("Clicked!");
				
				getContentPane().remove(main);
				
				getContentPane().setFocusable(false);
				initalizeGame();
				getContentPane().revalidate();
				
				panelNum = 1;
		        
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
		
		// https://stackoverflow.com/questions/1082504/requesting-focus-in-window
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
}
