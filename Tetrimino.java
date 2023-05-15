import java.awt.Color;

public class Tetrimino {
	static int[][][] shapes = {
			{{0,0},{0,1},{0,2},{0,3}},//Line
			{{0,0},{0,1},{0,2},{1,2}},//L-block
			{{1,0},{1,1},{1,2},{0,2}},//J-block
			{{0,0},{0,1},{1,0},{1,1}},//Square
			{{1,0},{0,1},{1,1},{2,1}},//T-block
			{{0,1},{1,1},{1,0},{2,0}},//S-block
			{{0,0},{0,1},{1,1},{2,1}}//Z-block
	};
	
	static Color[] colors = {
			Color.BLACK,
			Color.BLUE,
			Color.RED,
			Color.GREEN,
			Color.YELLOW,
			Color.CYAN,
			Color.MAGENTA,
	};
	int[][] shape;
	Color color;
	int x;
	int y;
	public Tetrimino(int x, int y) {
		this.x = x;
		this.y = y;
		int piece = (int)(Math.random()*7);
		shape = shapes[piece];
		color = colors[piece];
	}
	
	public void rotateLeft() {
		 for (int i = 0; i < 4; ++i) {
			 	int[] temp = shape[i].clone();
	            shape[i][0] = temp[1];
	            shape[i][1] = -temp[0]+1;
	        }
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
}