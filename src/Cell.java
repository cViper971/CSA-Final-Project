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
		this(x, y, Properties.darkMode, false);
		 
	}
	
	public Cell (int x, int y, Color col, boolean occupied)
	{
		this.x = x;
		this.y = y;
		this.c = col;
		this.occupied = occupied;
		
		try {
			tile = ImageIO.read(new File(Properties.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Cell (int x, int y, boolean darkMode, boolean occupied)
	{
		this.x = x;
		this.y = y;
		
		if (darkMode)
		{
			this.c = Color.BLACK;
		} else {
			this.c = Color.WHITE;
		}
		
		this.occupied = occupied;
		
		try {
			tile = ImageIO.read(new File(Properties.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void paintCell(Graphics g) {
		
		if(occupied) {
			g.setColor(c);
			g.fillRect(x*Properties.blockSize, y*Properties.blockSize, Properties.blockSize, Properties.blockSize);
			g.drawImage(tile, x*Properties.blockSize, y*Properties.blockSize, null);
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
	
	public Cell copyCell ()
	{
		return new Cell(this.x, this.y, this.c, this.occupied);
	}
}