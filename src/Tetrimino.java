import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tetrimino {
	public int[][] shape;
	public Color color;
	public int x, y, pieceNum;

	public static int[][][] shapes = {
			{{0,0},{0,1},{0,2},{0,3}},//Line
			{{0,0},{0,1},{0,2},{1,2}},//L-block
			{{1,0},{1,1},{1,2},{0,2}},//J-block
			{{0,0},{0,1},{1,0},{1,1}},//Square
			{{1,0},{0,1},{1,1},{2,1}},//T-block
			{{0,1},{1,1},{1,0},{2,0}},//S-block
			{{0,0},{1,0},{1,1},{2,1}}//Z-block
	};

	public static Color[] colors = {
			new Color(176,255,244),//cyan
			new Color(255,185,122),//orange
			new Color(171,178,255),//blue
			new Color(255,242,123),//yellow
			new Color(255,125,131),//red
			new Color(177,233,109),//green
			new Color(249,172,254),//pink
	};

	// Parallel Array with points mapped to each shape
	public static double[][] rotationPoints = {
			{1, 2},
			{0.5, 1.5},
			{1.5, 1.5},
			{1, 1},
			{1.5, 1.5},
			{1.5, 1.5},
			{1.5, 1.5}
	};


	public Tetrimino(int x, int y) {
		this(x,y,(int)(Math.random() * 7));
	}

	public Tetrimino (int x, int y, int piece)
	{
		this(x,y,piece,shapes[piece]);
	}
	public Tetrimino (int x, int y, int piece, int[][] shape)
	{
		this.x = x;
		this.y = y;
		this.pieceNum = piece;
		this.shape = deepClone(shape);
		color = colors[piece];
	}

	/* 
		Because of the way we have organized our coordinate system (positive x and y instead of typical Cartesian positive x and negative y), 
		we must invert all Cartesian transforms. 
	*/

	public void rotateLeft () {

		// All calculations are relative to the upper left hand corner (0, 0)
		double rX = rotationPoints[pieceNum][0];
		double rY = rotationPoints[pieceNum][1];

		rY = -rY;

		for (int i = 0; i < 4; i++)
		{
			int[] temp = shape[i].clone();

			int[] offsets = offsets(false);

			int coordX = (int)(temp[1] + rY + rX);
			int coordY = (int)(-temp[0] - rY + rX);

			shape[i][0] = (int)(coordX + offsets[0]);
			shape[i][1] = (int)(coordY + offsets[1]);
		}
	}

	public void rotateRight () {

		// All calculations are relative to the upper left hand corner (0, 0)
		double rX = rotationPoints[pieceNum][0];
		double rY = rotationPoints[pieceNum][1];

		rY = -rY;

		for (int i = 0; i < 4; i++)
		{
			int[] temp = shape[i].clone();

			int[] offsets = offsets(true);

			int coordX = (int)(-temp[1] - rY + rX);
			int coordY = (int)(temp[0] - rY - rX);

			shape[i][0] = (int)(coordX + offsets[0]);
			shape[i][1] = (int)(coordY + offsets[1]);
		}
	}

	public int[] offsets (boolean right)
	{
		if (right)
		{
			return new int[] {-1, 0};
		}

		return new int[] {0, -1};

	}

	public void moveDown() {
		y+=1;
	}

	public int drop(Cell[][] board) {
		int init = y;
		while(!isLanded(board))
			y+=1;
		return y-init;
	}

	public boolean isLanded(Cell[][] board) {
		for(int i = 0;i<4;i++) {
			
			
			if(y+shape[i][1]>=Properties.gridLength-1) {
				return true;
			}else if(board[(int)(y+shape[i][1])+1][(int)(x+shape[i][0])].isOccupied()==true){
				return true;
			}
		}
		return false;
	}

	public void drawCurrent(Graphics g, BufferedImage Tile) {
		for(int i=0;i<4;i++) {
			int currY = y + shape[i][1];
			int currX = x + shape[i][0];


			g.setColor(color);
			g.fillRect(Properties.blockSize*currX, Properties.blockSize*currY, Properties.blockSize, Properties.blockSize);
			g.drawImage(Tile,currX*Properties.blockSize, currY*Properties.blockSize, null);
		}
	}

	// Using the non-relative tetris coordinates
	public void drawCurrent(Graphics g, BufferedImage Tile, int pX, int pY)
	{
		for(int i=0;i<4;i++) {
			int currY = shape[i][1];
			int currX = shape[i][0];


			g.setColor(color);
			g.fillRect(Properties.blockSize*currX + pX, Properties.blockSize*currY + pY, Properties.blockSize, Properties.blockSize);
			g.drawImage(Tile,currX*Properties.blockSize + pX, currY*Properties.blockSize + pY, null);
		}
	}

	public static int[][] deepClone (int[][] array)
	{
		int[][] copy = new int[array.length][array[0].length];

		for (int i = 0; i < array.length; i++)
		{
			copy[i] = array[i].clone();
		}
		return copy;
	}

	public int[][] getShape() {
		return shape;
	}
	public void setShape(int[][] shape) {
		this.shape = shape;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tetrimino copyTetrimino ()
	{
		return new Tetrimino (x, y, pieceNum);
	}
	public Tetrimino copy ()
	{
		return new Tetrimino (x, y, pieceNum, deepClone(shape));
	}
}