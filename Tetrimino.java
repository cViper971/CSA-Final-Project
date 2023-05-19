import java.awt.Color;

public class Tetrimino {
	public double[][] shape;
	public Color color;
	public int x, y, pieceNum;
	
	public double[][][] shapes = {
			{{0,0},{0,1},{0,2},{0,3}, {1, 2}},//Line
			{{0,0},{0,1},{0,2},{1,2}, {0.5, 1.5}},//L-block
			{{1,0},{1,1},{1,2},{0,2}, {1.5, 1.5}},//J-block
			{{0,0},{0,1},{1,0},{1,1}, {1, 1}},//Square
			{{1,0},{0,1},{1,1},{2,1}, {1.5, 1.5}},//T-block
			{{0,1},{1,1},{1,0},{2,0}, {1.5, 1.5}},//S-block
			{{0,0},{1,0},{1,1},{2,1}, {1.5, 1.5}}//Z-block
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
	
	
	public Tetrimino(int x, int y) {
		this.x = x;
		this.y = y;
		int piece = (int)(Math.random()*7);
		this.pieceNum = piece;
		shape = shapes[pieceNum].clone();
		color = colors[pieceNum];
	}
	
	public Tetrimino (int x, int y, int piece)
	{
		this.x = x;
		this.y = y;
		this.pieceNum = piece;
		shape = shapes[piece].clone();
		color = colors[piece];
		
		
	}
	
	/* 
		Because of the way we have organized our coordinate system (positive x and y instead of typical Cartesian positive x and negative y), 
		we must invert all Cartesian transforms. 
	*/
	
	public void rotateLeft () {
		
		// All calculations are relative to the upper left hand corner (0, 0)
		double rX = shape[shape.length - 1][0];
		double rY = shape[shape.length - 1][1];
		
		rY = -rY;
		
		System.out.println("X: " + rX + " Y: " + rY);
		
		for (int i = 0; i < 4; i++)
		{
			double[] temp = shape[i].clone();
			
			int[] offsets = offsets(false);
			
			int coordX = (int)(temp[1] + rY + rX);
			int coordY = (int)(-temp[0] - rY + rX);
			
			shape[i][0] = (int)(coordX + offsets[0]);
			shape[i][1] = (int)(coordY + offsets[1]);
		}
	}
	
	public void rotateRight () {
		
		// All calculations are relative to the upper left hand corner (0, 0)
		double rX = shape[shape.length - 1][0];
		double rY = shape[shape.length - 1][1];
		
		rY = -rY;
		
		System.out.println("X: " + rX + " Y: " + rY);
		
		for (int i = 0; i < 4; i++)
		{
			double[] temp = shape[i].clone();
			
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
	
	public double[][] getShape() {
		return shape;
	}
	public void setShape(double[][] shape) {
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
}