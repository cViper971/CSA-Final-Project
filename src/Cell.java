import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cell {
	public boolean occupied;
	public Color c;
	public int x;
	public int y;
	public BufferedImage tile;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		c = Color.BLACK;
		occupied = false;
		
		try {
			tile = ImageIO.read(new File(Constants.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintCell(Graphics g) {
		
		if(occupied) {
			g.setColor(c);
			g.fillRect(x*Constants.blockSize, y*Constants.blockSize, Constants.blockSize, Constants.blockSize);
			g.drawImage(tile, x*Constants.blockSize, y*Constants.blockSize, null);
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