import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	public Cell[][] board;
	public Tetrimino currT;
	public Tetrimino nextT;

	public GameSideBar sb;

	public long score;
	public int level = 1;
	public int totalLines;

	public BufferedImage Tile;
	public Timer t;
	
	public int amountTicks = 0;
	public int movementDelay = 60;
	public int movementTickDelay = movementDelay;
	public int animationTickDelay = 5;
	
	public Cell[] animationBlocks = new Cell[8];
	public boolean breakAnimationStart = false;
	public int lowestIndex = 0;
	
	public boolean gameOver = false;
	public Button playYes, playNo;
	
	public int gridWidth = Properties.gridWidth;
	public int leftKey, rightKey, rotateKey, softKey, hardKey;
	


	boolean grounded = false;
	public GamePanel(GameSideBar sideBar, boolean isPlayer1, boolean isTwoPlayer){

		if (isPlayer1)
		{
			this.leftKey = Properties.player1Left;
			this.rightKey = Properties.player1Right;
			this.rotateKey = Properties.player1Rotate;
			this.softKey = Properties.player1Soft;
			this.hardKey = Properties.player1Hard;
		} else {
			this.leftKey = Properties.player2Left;
			this.rightKey = Properties.player2Right;
			this.rotateKey = Properties.player2Rotate;
			this.softKey = Properties.player2Soft;
			this.hardKey = Properties.player2Hard;
		}

		if (isTwoPlayer)
		{
			gridWidth /= 2;
		}

		board = new Cell[Properties.gridLength][gridWidth];
		for(int i=0;i<Properties.gridLength;i++) {
			for(int j=0;j<gridWidth;j++) {
				board[i][j] = new Cell(j,i);
			}
		}
		Tile = null;
		try {
			Tile = ImageIO.read(new File(Properties.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb = sideBar;
		t = new Timer(Properties.tick,new timey());
		t.start();

		if (!isTwoPlayer)
		{
			addKeyListener(new keyboard());
		}
		
		// Just a reminder that this will not necessarily work for some all board dimensions (such as 4 by 4) as some blocks are positioned with their coordinates starting way to the left. 
		currT = new Tetrimino(gridWidth/2, 1);
		nextT = new Tetrimino(gridWidth/2, 1);
		sb.setNext(nextT);
		
		playYes = new Button((gridWidth*Properties.blockSize)/2-80, (Properties.gridLength*Properties.blockSize)/2+60, 50, 20, 2, 5, new Color[] {new Color(58, 130, 47), new Color(154, 205, 50), Color.BLACK}, "YES", new Font("Monospace", Font.PLAIN, 22));
		playNo = new Button((gridWidth*Properties.blockSize)/2+40, (Properties.gridLength*Properties.blockSize)/2+60, 50, 20, 2, 5, new Color[] {new Color(140, 49, 58), new Color(230, 83, 97), Color.BLACK}, "NO", new Font("Monospace", Font.PLAIN, 22));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		currT.drawCurrent(g, Tile);
		if(Properties.colorOutlines)
			drawOutline(g);
		for(Cell[] cells:board)
		{
			for(Cell c:cells)
			{
				c.paintCell(g);
			}
		}
		
		if(gameOver) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

			g.setColor(Color.RED);
			g.setFont(new Font ("Monospace", Font.BOLD, 50));
			int offset = g.getFontMetrics().stringWidth("GAME OVER");
			g.drawString("GAME OVER", (this.getWidth() - offset) / 2, this.getHeight()/2-10);

			g.setColor(Color.BLACK);
			if(Properties.darkMode)
				g.setColor(Color.WHITE);
			g.setFont(new Font ("Monospace", Font.BOLD, 25));

			int offsetSmall = g.getFontMetrics().stringWidth("Play Again?");
			g.drawString("Play Again?", (this.getWidth() - offsetSmall) / 2, this.getHeight()/2 + 30);
			playYes.draw(g);
			playNo.draw(g);
		}else {
			
		}
	}

	private class keyboard implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(gameOver)
				return;
			// TODO Auto-generated method stub
			if(e.getKeyCode()==leftKey)
				if(canMove(currT.x-1,currT.y))
					currT.setX(currT.getX()-1);
			if(e.getKeyCode()==rightKey)
				if(canMove(currT.x+1,currT.y))
					currT.setX(currT.getX()+1);
			if(e.getKeyCode()==rotateKey)
				if (canRotate(false))
					currT.rotateRight();
			if(e.getKeyCode()==softKey) 
				movementTickDelay = 4;
	
			if(e.getKeyCode()==hardKey) {
				updateScore(2*currT.drop(board));
				updateGrid();
				checkLines();
			}
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(gameOver)
				return;
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				movementTickDelay = movementDelay;
		}
	}

	public void checkLines() {

		int lines = 0;
		int relIndex = 0;
		
		if (!breakAnimationStart) {
			
		Arrays.fill(animationBlocks, null);
		
		for(int i=0;i<board.length;i++) {
			boolean isFull = true;
			for(Cell cell:board[i]) {
				if(cell.isOccupied()==false)
				{
					isFull=false;
					break;
				}
				
			}
	
			if(isFull) {
				lines += 1;

				lowestIndex = i;
				
				if (board.length % 2 == 0)
				{
					animationBlocks[relIndex] = getCell(gridWidth / 2, i).copyCell();
					animationBlocks[relIndex + 1] = getCell(gridWidth / 2 - 1, i).copyCell();
				} else {
					animationBlocks[relIndex] = getCell(gridWidth / 2, i).copyCell();
					animationBlocks[relIndex + 1] = getCell(gridWidth / 2, i).copyCell();
				}
				
				
				relIndex += 2;
			}
		}
		if (lines > 0)
		{
			playSound("lineClear.wav");
			breakAnimationStart = true;
			totalLines+=lines;
			updateLevel();
		}
	}

		
		switch(lines) {
		case 1:
			updateScore(100);
			break;
		case 2:
			updateScore(300);
			break;
		case 3:
			updateScore(500);
			break;
		case 4:
			updateScore(1000);
		}
		repaint();
	}
	
	public void animateBlockBreak ()
	{
		if (breakAnimationStart)
		{
			if (amountTicks >= animationTickDelay)
			{
				for (int i = 0; i < animationBlocks.length - 1; i+=2)
				{
					Cell rightBlock = animationBlocks[i];
					Cell leftBlock = animationBlocks[i + 1];
					
					if (leftBlock != null && rightBlock != null)
					{
						if (rightBlock.x <= gridWidth - 1 && leftBlock.x >= 0)
						{
							animationBlocks[i] = new Cell(rightBlock.x + 1, rightBlock.y);
							animationBlocks[i + 1] = new Cell(leftBlock.x - 1, leftBlock.y);
							
							setCell(rightBlock.x, rightBlock.y, Color.WHITE, true);
							setCell(leftBlock.x, leftBlock.y, Color.WHITE, true);
						} else {
							
							animationBlocks[i] = new Cell(rightBlock.x - 1, rightBlock.y);
							animationBlocks[i + 1] = new Cell(leftBlock.x + 1, leftBlock.y);
							
							moveBlocksDown(lowestIndex, animationBlocks);
							repaint();
							breakAnimationStart = false;
							break;
						}
						
						
					}
				}
				
				amountTicks = 0;
			}
		}
	}
	
	public void moveBlocksDown (int maxY, Cell[] animationBlocks)
	{
		
		int totalRows = 0;
		
		for (int i = 0; i < animationBlocks.length; i+=2)
		{
			if (animationBlocks[i] != null)
			{
				int y = animationBlocks[i].y;
				
				for (int j = 0; j < gridWidth; j++)
					setCell(j, y, Color.BLACK, false);
			}
			
		}
		
		for (int i = Properties.gridLength - 1; i > 0; i--)
		{
			if (isRowMoveable(i))
			{
				int row = i;
				
				while (isRowMoveable(row))
				{
					moveRow(row);
					row += 1;
				}
			}
			
		}
	}
	
	public void moveRow (int row)
	{
		for (int i = 0; i < gridWidth; i++)
		{
			Cell currentCell = getCell(i, row);
			setCell(i, row + 1, currentCell.c, currentCell.occupied);
			
			setCell(i, row, Color.BLACK, false);
		}
	}
	
	public boolean isRowMoveable (int row)
	{
		if (row >= Properties.gridLength - 1)
		{
			return false;
		}
		
		boolean isOccupied = false;
		boolean isBelowOccupied = false;
		
		for (int j = 0; j < gridWidth; j++)
		{
			if (getCell(j, row).isOccupied())
			{
				isOccupied = true;
			}
			
			if (getCell(j, row + 1).isOccupied())
			{
				isBelowOccupied = true;
			}
		}
		
		return !isBelowOccupied && isOccupied;
	}



	public boolean canMove(int x, int y) {
		for(int i=0;i<4;i++) {
			if(!isOpen(x+(int)currT.shape[i][0],y+(int)currT.shape[i][1]))
				return false;
		}
		return true;
	}

	public boolean canRotate(boolean left) {

		Tetrimino copy = currT.copy();

		if(!left) {

			copy.rotateRight();

			for (int i = 0; i < 4; i++)
			{
				if (!isOpen(copy.x + (int)(copy.shape[i][0]), copy.y + (int)(copy.shape[i][1])))
				{
					return false;
				}
			}


		} else {

			copy.rotateLeft();

			for (int i = 0; i < 4; i++)
			{
				if (!isOpen(copy.x + (int)(copy.shape[i][0]), copy.y + (int)(copy.shape[i][1])))
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean isOpen(int x,int y) {
		if(x>gridWidth-1||x<0)
			return false;
		if(y>Properties.gridLength-1||y<0)
			return false;
		if(board[y][x].isOccupied())
			return false;
		return true;
	}
	
	public void playSound(String file) {
		File music = new File("Sounds/"+file);
		
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawOutline(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Tetrimino copy = currT.copy();
		copy.drop(board);
		g2.setStroke(new BasicStroke(3));
		for(int i= 0;i<4;i++) {
			g.drawRect((int)(Properties.blockSize*(copy.x+copy.shape[i][0])),(int)(Properties.blockSize*(copy.y+copy.shape[i][1])), Properties.blockSize, Properties.blockSize);
		}
	}

	public void updateGrid() {
		for(int i = 0;i<4;i++) {
			setCell(currX(i),currY(i),currT.color,true);
		}
		playSound("drop.wav");
		currT = nextT;
		nextT = new Tetrimino(gridWidth/2,0);
		sb.setNext(nextT);

		for(int i=0;i<4;i++) {
			if(!isOpen(currX(i),currY(i))) {
				t.stop();
				sb.saveHighScore();
				playSound("gameOver.wav");
				runGameOver();
			}
		}
	}

	public void updateScore(int change) {
		score+=change*level;
		sb.setScore(score);
	}
	
	public void updateLevel() {
		level = totalLines/10+1;
		if(level>10)
			level=10;
		movementDelay = (int)(60*Math.pow(0.7899771419, level)); //multiplier so last level has tick of 6
		movementTickDelay = movementDelay;
		System.out.println(movementDelay);
		sb.setLevel(level);
	}
	
	public void runGameOver(){
		gameOver = true;
	}

	public int currX(int i) {
		return currT.x + (int)currT.shape[i][0];
	}
	public int currY(int i) {
		return currT.y + (int)currT.shape[i][1];
	}

	public Cell getCell(int x, int y) {
		
		return board[y][x];
	}

	public void setCell(int x, int y, Color c, boolean occupied) {
		this.board[y][x].setColor(c);
		this.board[y][x].setOccupied(occupied);
	}

	private class timey implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			
			if (amountTicks >= movementTickDelay) 
			{
				if(grounded) {
					if(currT.isLanded(board))
					{
						checkLines();
						updateGrid();
					}
					else
						currT.moveDown();
					grounded = false;
				}else if(canMove(currT.x,currT.y+1) && !breakAnimationStart)
					currT.moveDown();
					if(movementTickDelay==5)
						updateScore(1);
					amountTicks = 0;
					
				if(currT.isLanded(board)) {
					grounded = true;
	
				}
			}
			
			
			amountTicks+= 1;
			
			animateBlockBreak();
			repaint();
		}

	}

}
