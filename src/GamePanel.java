import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	public Cell[][] board;
	public Tetrimino currT;
	public Tetrimino nextT;
	
	public GameSideBar sb;
	
	public long score;
	
	public BufferedImage Tile;
	public Timer t;
	
	
	boolean grounded = false;
	public GamePanel(GameSideBar sideBar){
		board = new Cell[Constants.gridLength][Constants.gridWidth];
		for(int i=0;i<Constants.gridLength;i++) {
			for(int j=0;j<Constants.gridWidth;j++) {
				board[i][j] = new Cell(j,i);
			}
		}
		Tile =null;
		try {
			Tile = ImageIO.read(new File(Constants.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb = sideBar;
		t = new Timer(Constants.tick,new timey());
		t.start();
		addKeyListener(new keyboard());
		setFocusable(true);
		
		currT = new Tetrimino(Constants.gridWidth/2, 1);
		nextT = new Tetrimino(Constants.gridWidth/2, 1);
		sb.setNext(nextT);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		currT.drawCurrent(g, Tile);
		drawOutline(g);
		for(Cell[] cells:board)
			for(Cell c:cells)
				c.paintCell(g);
		
		
		
	}
	
	private class keyboard implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				if(canMove(currT.x-1,currT.y))
					currT.setX(currT.getX()-1);
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				if(canMove(currT.x+1,currT.y))
					currT.setX(currT.getX()+1);
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				if (canRotate(false))
				{
					currT.rotateRight();
				}
			}
				
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
//				if (canRotate(true))
//				{
//					currT.rotateLeft();
//				}
				t.setInitialDelay(0);
				t.setDelay(50);
				t.restart();
			}
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				updateScore(2*currT.drop(board));
				updateGrid();
				checkLines();
			}
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				t.setDelay(500);
		}
	}
	
	public void checkLines() {
		
		int lines = 0;
		
		for(int i=0;i<board.length;i++) {
			boolean isFull = true;
			for(Cell cell:board[i]) {
				if(cell.isOccupied()==false)
				{
					isFull=false;
					break;
				}
			}
			System.out.println(i);
			
			if(isFull) {
				System.out.println(i);
				repaint();
				lines += 1;
				
//
//				for(int j=0;j<=Constants.gridWidth/2;j++) {
//					System.out.println("("+(Constants.gridWidth/2+j)+","+i+")");
//					setCell(5,5, Color.white,true);
//					//setCell(Constants.gridWidth/2+j,i, Color.white,false);
//					setCell(Constants.gridWidth/2-j,i, Color.white,false);
//					try {
//						//Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				for(int j=i;j>=1;j--) {
					for(int k=0;k<Constants.gridWidth;k++) {
						setCell(k,j,board[j-1][k].getC(),board[j-1][k].isOccupied());
					}
				}
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
		if(x>Constants.gridWidth-1||x<0)
			return false;
		if(y>Constants.gridLength-1||y<0)
			return false;
		if(board[y][x].isOccupied())
			return false;
		return true;
	}
	
	public void drawOutline(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Tetrimino copy = currT.copy();
		copy.drop(board);
		g2.setStroke(new BasicStroke(3));
		for(int i= 0;i<4;i++) {
			g.drawRect((int)(Constants.blockSize*(copy.x+copy.shape[i][0])),(int)(Constants.blockSize*(copy.y+copy.shape[i][1])), Constants.blockSize, Constants.blockSize);
		}
	}
	
	public void updateGrid() {
		for(int i = 0;i<4;i++) {
			setCell(currX(i),currY(i),currT.color,true);
		}
		currT = nextT;
		nextT = new Tetrimino(Constants.gridWidth/2,0);
		sb.setNext(nextT);
		
		for(int i=0;i<4;i++) {
			if(!isOpen(currX(i),currY(i))) {
				t.stop();
				sb.saveHighScore();
				System.out.println("game over");
			}
		}
	}
	
	public void updateScore(int change) {
		score+=change;
		sb.setScore(score);
	}
	
	public int currX(int i) {
		return currT.x + (int)currT.shape[i][0];
	}
	public int currY(int i) {
		return currT.y + (int)currT.shape[i][1];
	}

	public Cell getCell(int x, int y) {
		return board[x][y];
	}

	public void setCell(int x, int y, Color c, boolean occupied) {
		this.board[y][x].setColor(c);
		this.board[y][x].setOccupied(occupied);
	}
	
	private class timey implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(grounded) {
				if(currT.isLanded(board))
					updateGrid();
				else
					currT.moveDown();
				grounded = false;
			}else if(canMove(currT.x,currT.y+1))
				currT.moveDown();
			if(currT.isLanded(board)) {
				grounded = true;

			}
			if(t.getDelay()==50)
				updateScore(1);
			checkLines();
		}
		
	}
	
}