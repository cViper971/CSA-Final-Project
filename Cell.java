import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	boolean occupied;
	Color c;
	int x;
	int y;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		c = Color.BLACK;
		occupied = false;
	}
	
	public void paintCell(Graphics g) {
		if(occupied) {
			g.setColor(c);
			g.fillRect(x*Constants.blockSize, y*Constants.blockSize, Constants.blockSize, Constants.blockSize);
		}
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public Color getC() {
		return c;
	}
	public void setColor(Color c) {
		this.c = c;
	}
}