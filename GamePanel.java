import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
	int boxX = 100;
	int boxY = 100;
	Cell[][] board;
	Tetrimino currT;
	public GamePanel(){
		board = new Cell[Constants.gridLength][Constants.gridWidth];
		for(int i=0;i<Constants.gridLength;i++) {
			for(int j=0;j<Constants.gridWidth;j++) {
				board[i][j] = new Cell(j,i);
			}
		}
		Timer t = new Timer(Constants.tick,new timey());
		t.start();
		currT = new Tetrimino(Constants.gridWidth/2,1);
		addKeyListener(new keyboard());
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawCurrent(g);
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
				//if(canRotate(false))
					currT.rotateRight();
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				//if(canRotate(true))
					currT.rotateLeft();
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
				drop();
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public boolean isLanded() {
		for(int i = 0;i<4;i++) {
			if(currY(i)>=Constants.gridLength-2) {
				return true;
			}else if(board[currY(i)+1][currX(i)].isOccupied()==true){
				return true;
			}
		}
		return false;
	}
	
	public void checkLines() {
		for(int i=0;i<board.length;i++) {
			boolean isFull = true;
			for(Cell cell:board[i]) {
				if(cell.isOccupied()==false)
					isFull=false;
			}
			if(isFull) {
				System.out.println("full line at: "+i);
				for(int j=i;j>1;j--) {
					for(int k=0;k<Constants.gridWidth;k++) {
						setCell(k,j,board[j-1][k].getC(),board[j-1][k].isOccupied());
					}
				}
			}
		}
		repaint();
	}
	
	public boolean canMove(int x, int y) {
		for(int i=0;i<4;i++) {
			if(!isOpen(x+currT.shape[i][0],y+currT.shape[i][1]))
				return false;
		}
		return true;
	}
	
	public boolean canRotate(boolean left) {
		if(left) {
			for(int i=0;i<4;i++) {
				if(!isOpen(currY(i),-currX(i)))
					return false;
			}
		}else {
			for(int i=0;i<4;i++) {
				if(!isOpen(-currY(i),currX(i)))
					return false;
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

	public void drawCurrent(Graphics g) {
		BufferedImage img=null;
		try {
			img = ImageIO.read(new File(Constants.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<4;i++) {
			g.setColor(currT.color);
			g.fillRect(Constants.blockSize*currX(i), Constants.blockSize*currY(i), Constants.blockSize, Constants.blockSize);
			g.drawImage(img,currX(i)*Constants.blockSize, currY(i)*Constants.blockSize, null);
		}
	}
	
	public void drop() {
		while(!isLanded())
			currT.setY(currT.getY()+1);
		updateGrid();
		checkLines();
	}
	
	public void updateGrid() {
		for(int i = 0;i<4;i++) {
			setCell(currX(i),currY(i),currT.color,true);
		}
		currT = new Tetrimino(Constants.gridWidth/2,1);
	}
	
	public int currX(int i) {
		return currT.x + currT.shape[i][0];
	}
	public int currY(int i) {
		return currT.y + currT.shape[i][1];
	}
	public int getBoxX() {
		return boxX;
	}

	public void setBoxX(int boxX) {
		this.boxX = boxX;
	}

	public int getBoxY() {
		return boxY;
	}

	public void setBoxY(int boxY) {
		this.boxY = boxY;
	}

	public Cell getCell(int x, int y) {
		return board[x][y];
	}

	public void setCell(int x, int y, Color c, boolean occupied) {
		this.board[y][x].setColor(c);
		this.board[y][x].setOccupied(occupied);
	}
	
	public class timey implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			currT.moveDown();
			if(isLanded()) {
				updateGrid();
			}
			checkLines();
			repaint();
		}
		
	}
}