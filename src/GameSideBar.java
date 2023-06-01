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
	private int level;
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
		
		try {
			pw = new PrintWriter(new File("highScore.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			highScore = sc.nextLong();
		} catch (Exception e) {
			pw.write("0");
			highScore = 0;
			pw.close();
		}
		
		
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		int y = 0;

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		g.setColor(Color.BLACK);
		g.setFont(new Font ("Monospace", Font.BOLD, 20));

		int widthUpcoming = g.getFontMetrics().stringWidth("Upcoming Piece");
		g.drawString("Upcoming Piece", (w - widthUpcoming) / 2, y + 30);

		drawUpcomingPiece(g);

		int widthScore = g.getFontMetrics().stringWidth("Score");
		int widthActualScore = g.getFontMetrics().stringWidth("" + score);
		
		int widthHighScore = g.getFontMetrics().stringWidth("High Score");
		int widthActualHighScore = g.getFontMetrics().stringWidth("" + score);

		g.setColor(Color.BLACK);
		g.drawString("Score", (w - widthScore) / 2, y + 250);
		g.drawString("" + score, (w - widthActualScore) / 2, y + 270);
		g.drawString("High Score", (w - widthHighScore) / 2, y + 310);
		g.drawString("" + highScore, (w - widthActualHighScore) / 2, y + 330);
		
		int levelWidth = g.getFontMetrics().stringWidth("Level");
		int actualLevelWidth = g.getFontMetrics().stringWidth("" + level);
		g.drawString("Level" , (w - levelWidth) / 2, y + 370);
		g.drawString("" + level , (w - actualLevelWidth) / 2, y + 390);
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
		pw.write(""+highScore);
		pw.close();
	}

	public void setNext(Tetrimino t) {
		this.nextTetrimino = t;
		repaint();
	}
}