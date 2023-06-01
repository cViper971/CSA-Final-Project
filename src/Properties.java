import java.awt.event.KeyEvent;

public class Properties {
	public static final int blockSize = 30;
	public static final String img="Tile.png";
	public static final int tick = 10;
	
	// The gridWidth must be divisible by 2
	public static int gridWidth = 10;
	public static int gridLength = 25;
	public static boolean colorOutlines = true;
	public static boolean darkMode = true;
	
	public static int player1Left = KeyEvent.VK_LEFT;
	public static int player1Right = KeyEvent.VK_RIGHT;
	public static int player1Rotate = KeyEvent.VK_UP;
	public static int player1Soft = KeyEvent.VK_DOWN;
	public static int player1Hard = KeyEvent.VK_SPACE;
	
	public static int player2Left = KeyEvent.VK_A;
	public static int player2Right = KeyEvent.VK_D;
	public static int player2Rotate = KeyEvent.VK_W;
	public static int player2Soft = KeyEvent.VK_S;
	public static int player2Hard = KeyEvent.VK_SHIFT;
	
	
	// Should be kept at least 500 by 600 for optimal gameplay
	
	// Relative dimensions:
	public static final int mainWindowWidth = 700;
	public static final int mainWindowHeight = blockSize * gridLength;
	
}