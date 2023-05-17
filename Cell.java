import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		BufferedImage img=null;
		try {
			img = ImageIO.read(new File("src/Untitled.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(occupied) {
			System.out.println(y*Constants.blockSize);
			g.setColor(c);
			g.fillRect(x*Constants.blockSize, y*Constants.blockSize, Constants.blockSize, Constants.blockSize);
			g.drawImage(img, x*Constants.blockSize, y*Constants.blockSize, null);
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