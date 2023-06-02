import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	public boolean isTwoPlayer;
	
	public Cell(int x, int y, boolean isTwoplayer) {
		this(x, y, Color.BLACK, false, isTwoplayer);
	}
	
	public Cell (int x, int y, Color col, boolean occupied, boolean isTwoPlayer)
	{
		this.x = x;
		this.y = y;
		this.c = col;
		this.occupied = occupied;
		this.isTwoPlayer = isTwoPlayer;
		
		try {
			tile = ImageIO.read(new File(Properties.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (isTwoPlayer)
		{
			tile = resize(tile, Properties.blockSize, Properties.blockSize);
		}
	}
	
	public BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage resizedImage = new BufferedImage(width, height,
	    BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, width, height, null);
	    g.dispose();
	    return resizedImage;
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
		return new Cell(this.x, this.y, this.c, this.occupied, this.isTwoPlayer);
	}
}