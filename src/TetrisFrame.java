import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
		
		playMusic();
		panelNum = 0;
		setPreferredSize(new Dimension(Properties.mainWindowWidth + 16, Properties.mainWindowHeight + Properties.blockSize + 9));
		
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
	
	public void playMusic() {
		File music = new File("Sounds/music.wav");
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				if (panel.playNo.isMouseInside(mX, mY))
				{
					getContentPane().remove(panel);
					
					getContentPane().setFocusable(false);
					getContentPane().add(main);
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
		int width = Properties.mainWindowWidth - Properties.blockSize*Properties.gridWidth;
        int height = Properties.blockSize*Properties.gridLength;
        
		sideBar = new GameSideBar(width, height);
		sideBar.setPreferredSize(new Dimension(width, height));
        sideBar.setBounds(Properties.blockSize*Properties.gridWidth, 0, Properties.mainWindowWidth, Properties.blockSize*Properties.gridLength);
        
		panel = new GamePanel(sideBar);
		
//		https://stackoverflow.com/questions/1082504/requesting-focus-in-window
		SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				panel.requestFocus();
				panel.requestFocusInWindow();
				panel.requestFocus(true);
				panel.setBackground(Color.BLACK);
		        panel.setPreferredSize(new Dimension(Properties.blockSize*Properties.gridWidth,Properties.blockSize*Properties.gridLength));
		        panel.setBounds(0, 0, Properties.blockSize*Properties.gridWidth, Properties.blockSize*Properties.gridLength);
				
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