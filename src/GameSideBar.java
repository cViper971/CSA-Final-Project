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
	private int level = 1;
	private long highScore;
	private BufferedImage Tile;
	private Scanner sc;
	private PrintWriter pw;
	private Button exit;

	public GameSideBar (int w, int h)
	{
		setBackground(Color.WHITE);
		this.w = w;
		this.h = h;

		Tile = null;
		try {
			Tile = ImageIO.read(new File(Properties.img));
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
		exit = new Button(w/2-40, 3*h/4+20, 80, 35, 3, 5, new Color[] {Color.BLACK, new Color(235, 106, 127), Color.BLACK}, "QUIT", new Font("Monospace", Font.PLAIN, 20));
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		g.setColor(Color.BLACK);
		if(Properties.darkMode)
			g.setColor(new Color(199, 215, 240));
		g.setFont(new Font ("Monospace", Font.BOLD, 20));

		int offset = g.getFontMetrics().stringWidth("Upcoming Piece");
		
		g.drawString("Upcoming Piece", (w - offset) / 2, 70);
		drawUpcomingPiece(g);
		
		g.setColor(Color.BLACK);
		if(Properties.darkMode)
			g.setColor(new Color(199, 215, 240));
		offset = g.getFontMetrics().stringWidth("Score");
		g.drawString("Score", (w - offset) / 2, 230);
		offset = g.getFontMetrics().stringWidth("" + score);
		g.drawString("" + score, (w - offset) / 2, 250);
		
		offset = g.getFontMetrics().stringWidth("High Score");
		g.drawString("High Score", (w - offset) / 2, 330);
		offset = g.getFontMetrics().stringWidth("" + highScore);
		g.drawString("" + highScore, (w - offset) / 2, 350);
		
		offset = g.getFontMetrics().stringWidth("Level");
		g.drawString("Level", (w - offset) / 2, 430);
		offset = g.getFontMetrics().stringWidth("" + level);
		g.drawString("" + level, (w - offset) / 2, 450);
		
		exit.draw(g);
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

			int centerX = (w - (maxX + 1) * Properties.blockSize) / 2;

			g.setColor(nextTetrimino.colors[nextTetrimino.pieceNum]);

			nextTetrimino.drawCurrent(g, Tile, centerX, 90);

		}
	}

	public void setScore(long score) {
		this.score = score;
		if(score>highScore)
			setHighScore(score);
		repaint();
	}
	
	public void setLevel(int level) {
		this.level = level;
		repaint();
	}

	public void setHighScore(long score) {
		highScore = score;
	}

	public Button getExit() {
		return exit;
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