import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private GameSideBar sideBar, sideBar2;
	private GamePanel panel, panel2;
	private JPanel container;
	private SettingsPanel page;
	private Clip goatTetrisTheme;
	private Keyboard joined;
	
	/*
		0: MainMenu
		1: Single Player Game
		2: Settings
		3: Two Player Game
	*/
	private int panelNum;
	
	// Testing mode boots you straight into the game
	public TetrisFrame (String window, boolean testingMode)
	{
		super(window);
		
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
			goatTetrisTheme = AudioSystem.getClip();
			goatTetrisTheme.open(audioInput);
			goatTetrisTheme.start();
			goatTetrisTheme.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stopMusic() {
		goatTetrisTheme.stop();
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
					playMusic();
					
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
				

				if (main.twoPlay.isMouseInside(mX, mY))
				{
					// https://stackoverflow.com/questions/641172/how-to-focus-a-jframe
					getContentPane().remove(main);
					setVisible(true);
					toFront();
					requestFocus();
					getContentPane().setFocusable(true);
					initalizeTwoGame();
					getContentPane().revalidate();
					playMusic();

					panelNum = 3;
				}
			}
			if (panelNum ==1) {
				if(panel.gameOver==true) {
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
						panel.t.stop();
						stopMusic();
						getContentPane().removeAll();
	
						getContentPane().setFocusable(true);
						getContentPane().add(main);
						getContentPane().revalidate();
						getContentPane().repaint();
						panelNum = 0;
					}
				}else {
					int mX = MouseInfo.getPointerInfo().getLocation().x - sideBar.getLocationOnScreen().x;
					int mY = MouseInfo.getPointerInfo().getLocation().y - sideBar.getLocationOnScreen().y;
					if (sideBar.getExit().isMouseInside(mX, mY))
					{
						panel.t.stop();
						panel.sb.saveHighScore();
						stopMusic();
						getContentPane().removeAll();
	
						getContentPane().setFocusable(false);
						getContentPane().add(main);
						getContentPane().revalidate();
						getContentPane().repaint();
						panelNum = 0;
					}
				}
			}
			if (panelNum == 2)
			{
				int mX = MouseInfo.getPointerInfo().getLocation().x - page.getLocationOnScreen().x;
				int mY = MouseInfo.getPointerInfo().getLocation().y - page.getLocationOnScreen().y;

				if (page.backButton.isMouseInside(mX, mY))
				{
					getContentPane().removeAll();
					
					getContentPane().add(main);
					getContentPane().revalidate();
					getContentPane().repaint();

					panelNum = 0;
				}
			}
			if (panelNum == 3)
			{
				int mX1 = MouseInfo.getPointerInfo().getLocation().x - panel.getLocationOnScreen().x;
				int mY1 = MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y;
				
				int mX2 = MouseInfo.getPointerInfo().getLocation().x - panel2.getLocationOnScreen().x;
				int mY2 = MouseInfo.getPointerInfo().getLocation().y - panel2.getLocationOnScreen().y;
				
				System.out.println("Mouse: " + mX1 + " " + mY1);
				
				if (panel.playYes.isMouseInside(mX1, mY1) || panel2.playYes.isMouseInside(mX2, mY2) )
				{
					getContentPane().removeAll();
					
					getContentPane().setFocusable(false);
					initalizeTwoGame();
					getContentPane().revalidate();
					panelNum = 3;
				}
				if (panel.playNo.isMouseInside(mX1, mY1) || panel2.playNo.isMouseInside(mX2, mY2) )
				{
					panel.t.stop();
					panel2.t.stop();
					stopMusic();
					getContentPane().removeAll();
					getContentPane().removeKeyListener(joined);
					getContentPane().setFocusable(false);
					getContentPane().add(main);
					getContentPane().revalidate();
					getContentPane().repaint();
					panelNum = 0;
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
        sideBar.setBackground(new Color(237, 239, 245));
        if(Properties.darkMode)
			sideBar.setBackground(new Color(13, 16, 20));
        
        panel = new GamePanel(sideBar, true, false);
        
        System.out.println(Properties.darkMode);
		
//		https://stackoverflow.com/questions/1082504/requesting-focus-in-window
		SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				panel.requestFocus();
				panel.requestFocusInWindow();
				panel.requestFocus(true);
				
				panel.setBackground(new Color(255,255,255));
				if(Properties.darkMode)
					panel.setBackground(new Color(26, 32, 41));
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
	
	public void initalizeTwoGame ()
	{
		
		int width = Properties.mainWindowWidth / 2 - Properties.blockSize*Properties.gridWidth / 2;
        int height = Properties.blockSize*Properties.gridLength;

		sideBar = new GameSideBar(width, height);
		sideBar.setPreferredSize(new Dimension(width, height));
        sideBar.setBounds(Properties.blockSize*Properties.gridWidth / 2, 0, Properties.mainWindowWidth / 2, Properties.blockSize*Properties.gridLength);

        sideBar2 = new GameSideBar(width, height);
		sideBar2.setPreferredSize(new Dimension(width, height));
        sideBar2.setBounds(Properties.blockSize*Properties.gridWidth / 2, 0, Properties.mainWindowWidth, Properties.blockSize*Properties.gridLength);
        
        sideBar.setBackground(new Color(237, 239, 245));
        sideBar2.setBackground(new Color(237, 239, 245));
        if(Properties.darkMode)
        	sideBar.setBackground(new Color(13, 16, 20));
			sideBar2.setBackground(new Color(13, 16, 20));

		panel = new GamePanel(sideBar, false, true);
		panel2 = new GamePanel(sideBar2, true, true);
		
		panel.setBackground(new Color(255,255,255));
		panel2.setBackground(new Color(255,255,255));
		if(Properties.darkMode)
			panel.setBackground(new Color(26, 32, 41));
			panel2.setBackground(new Color(26, 32, 41));

//		https://stackoverflow.com/questions/1082504/requesting-focus-in-window
		SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				//panel.requestFocus(true);
		        panel.setPreferredSize(new Dimension(Properties.blockSize*Properties.gridWidth / 2,Properties.blockSize*Properties.gridLength));
		        panel.setBounds(0, 0, Properties.blockSize*Properties.gridWidth / 2, Properties.blockSize*Properties.gridLength);

				//panel2.requestFocus(true);
		        panel2.setPreferredSize(new Dimension(Properties.blockSize*Properties.gridWidth / 2,Properties.blockSize*Properties.gridLength));
		        panel2.setBounds(Properties.blockSize*Properties.gridWidth / 2, 0, Properties.blockSize*Properties.gridWidth, Properties.blockSize*Properties.gridLength);



			}
		});

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.add(panel);
        container.add(sideBar);
        container.add(panel2);
        container.add(sideBar2);
        getContentPane().add(container);

        joined = new Keyboard();
        joined.panel1 = panel;
        joined.panel2 = panel2;

        addKeyListener(joined);
        container.setFocusable(true);

        container.repaint();
	}

	private class Keyboard implements KeyListener {

		public GamePanel panel1;
		public GamePanel panel2;

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyPressed(KeyEvent e) {
			
			System.out.println("Typed");
			
			if(!panel1.gameOver)
			{
				// TODO Auto-generated method stub
				if(e.getKeyCode()==panel1.leftKey)
					if(panel1.canMove(panel1.currT.x-1,panel1.currT.y))
						panel1.currT.setX(panel1.currT.getX()-1);
				if(e.getKeyCode()==panel1.rightKey)
					if(panel1.canMove(panel1.currT.x+1,panel1.currT.y))
						panel1.currT.setX(panel1.currT.getX()+1);
				if(e.getKeyCode()==panel1.rotateKey)
				{
					if (panel1.canRotate(false))
					{
						panel1.currT.rotateRight();
					}
				}

				if(e.getKeyCode()==panel1.softKey) {
//					if (canRotate(true))
//					{
//						currT.rotateLeft();
//					}
					panel1.movementTickDelay = 5;


				}
				if(e.getKeyCode()==panel1.hardKey) {
					panel1.updateScore(2*panel1.currT.drop(panel1.board));
					panel1.updateGrid();
					panel1.checkLines();
				}
			}

			if (!panel2.gameOver)
			{
				// TODO Auto-generated method stub
				if(e.getKeyCode()==panel2.leftKey)
					if(panel2.canMove(panel2.currT.x-1,panel2.currT.y))
						panel2.currT.setX(panel2.currT.getX()-1);
				if(e.getKeyCode()==panel2.rightKey)
					if(panel2.canMove(panel2.currT.x+1,panel2.currT.y))
						panel2.currT.setX(panel2.currT.getX()+1);
				if(e.getKeyCode()==panel2.rotateKey)
				{
					if (panel2.canRotate(false))
					{
						panel2.currT.rotateRight();
					}
				}

				if(e.getKeyCode()==panel2.softKey) {
					panel2.movementTickDelay = 5;


				}
				if(e.getKeyCode()==panel2.hardKey) {
					panel2.updateScore(2*panel2.currT.drop(panel2.board));
					panel2.updateGrid();
					panel2.checkLines();
				}
			}


			panel1.repaint();
			panel2.repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(panel1.gameOver || panel2.gameOver)
				return;
			// TODO Auto-generated method stub
			if(e.getKeyCode()==panel1.softKey)
				panel1.movementTickDelay = panel1.movementDelay;
			
			if(e.getKeyCode()==panel2.softKey)
				panel2.movementTickDelay = panel2.movementDelay;

		}
	}
	
	public void loadSettings() {
		page = new SettingsPanel();
		getContentPane().add(page);
	}
}