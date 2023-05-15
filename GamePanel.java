import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
		Timer t = new Timer(300,new timey());
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
				currT.setX(currT.getX()-1);
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				currT.setX(currT.getX()+1);
			if(e.getKeyCode()==KeyEvent.VK_UP)
				currT.rotateLeft();
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				boxY+=5;
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public boolean isLanded() {
		for(int i = 0;i<4;i++) {
			if(currY(i)>=Constants.gridLength-2)
				return true;
			else if(board[currY(i)+1][currX(i)].isOccupied()==true){
				return true;
			}
		}
		return false;
	}

	public void drawCurrent(Graphics g) {
		for(int[] coord:currT.shape) {
			g.setColor(currT.color);
			g.fillRect(Constants.blockSize*(currT.x+coord[0]), Constants.blockSize*(currT.y+coord[1]), Constants.blockSize, Constants.blockSize);
		}
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
			currT.setY(currT.getY()+1);
			if(isLanded()) {
				System.out.println("landed");
				for(int i = 0;i<4;i++) {
					System.out.println("x: "+currX(i)+" y: "+currY(i));
					setCell(currX(i),currY(i),currT.color,true);
				}
				currT = new Tetrimino(Constants.gridWidth/2,1);
			}
			repaint();
		}
		
	}
}