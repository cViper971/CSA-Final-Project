import java.awt.Color;

public class Tetrimino {
	static int[][][] shapes = {
			{{0,0},{0,1},{0,2},{0,3}},//Line
			{{0,0},{0,1},{0,2},{1,2}},//L-block
			{{1,0},{1,1},{1,2},{0,2}},//J-block
			{{0,0},{0,1},{1,0},{1,1}},//Square
			{{1,0},{0,1},{1,1},{2,1}},//T-block
			{{0,1},{1,1},{1,0},{2,0}},//S-block
			{{0,0},{1,0},{1,1},{2,1}}//Z-block
	};
	
	static Color[] colors = {
			new Color(176,255,244),//cyan
			new Color(255,185,122),//orange
			new Color(171,178,255),//blue
			new Color(255,242,123),//yellow
			new Color(255,125,131),//red
			new Color(177,233,109),//green
			new Color(249,172,254),//pink
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
	            shape[i][1] = -temp[0];
	        }
	}
	
	public void rotateRight() {
		 for (int i = 0; i < 4; ++i) {
			 	int[] temp = shape[i].clone();
	            shape[i][0] = -temp[1];
	            shape[i][1] = temp[0];
	        }
	}
	
	public void moveDown() {
		y+=1;
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