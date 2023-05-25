import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameSideBar extends JPanel{
	
	private Tetrimino nextTetrimino;
	private int w, h;
	private long score;
	private long highScore;
	private BufferedImage Tile;
	private Scanner sc;
	private PrintWriter pw;
	
	public GameSideBar (int w, int h)
	{
		setBackground(Color.WHITE);
		this.w = w;
		this.h = h;
		
		Tile = null;
		try {
			Tile = ImageIO.read(new File(Constants.img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			sc = new Scanner(new File("highScore.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		highScore = sc.nextLong();
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font ("Monospace", Font.BOLD, 20));
		
		int widthUpcoming = g.getFontMetrics().stringWidth("Upcoming Piece");
		g.drawString("Upcoming Piece", (w - widthUpcoming) / 2, 30);
		
		drawUpcomingPiece(g);
		
		int widthScore = g.getFontMetrics().stringWidth("Score");
		int widthActualScore = g.getFontMetrics().stringWidth("" + score);
		
		g.setColor(Color.BLACK);
		g.drawString("Score", (w - widthScore) / 2, 250);
		g.drawString("" + score, (w - widthActualScore) / 2, 270);
		g.drawString("High Score", (w - widthScore) / 2, 310);
		g.drawString("" + highScore, (w - widthActualScore) / 2, 330);
	}
	
	public void drawUpcomingPiece (Graphics g)
	{
		if (nextTetrimino != null)
		{
			int maxX = 0;
			int maxY = 0;
			
			for (int i = 0; i < nextTetrimino.shape.length; i++)
			{
				if (nextTetrimino.shape[i][0] > maxX)
				{
					maxX = nextTetrimino.shape[i][0];
				}
				
				if (nextTetrimino.shape[i][1] > maxY)
				{
					maxY = nextTetrimino.shape[i][1];
				}
			}
			
			int centerX = (w - (maxX + 1) * Constants.blockSize) / 2;
			
			g.setColor(nextTetrimino.colors[nextTetrimino.pieceNum]);
			
			nextTetrimino.drawCurrent(g, Tile, centerX, 50);
			
		}
	}
	
	public void setScore(long score) {
		this.score = score;
		if(score>highScore)
			setHighScore(score);
		repaint();
	}
	
	public void setHighScore(long score) {
		highScore = score;
	}
	
	public void saveHighScore() {
		sc.close();
		try {
			pw = new PrintWriter(new File("highScore.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.write(""+highScore);
		pw.close();
	}
	
	public void setNext(Tetrimino t) {
		this.nextTetrimino = t;
		repaint();
	}
}