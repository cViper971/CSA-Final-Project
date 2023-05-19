import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SmartRectangle {
	
	private int x, y, w, h, edgeLength;
	private Color[] colors;
	private String msg; 
	private Font font;
	
	// The colors array should be formatted as [edgeColor, fillColor, textColor, hoverColor]
	public SmartRectangle (int x, int y, int w, int h, int edgeLength, Color[] colors, String msg, Font font) 
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.edgeLength = edgeLength;
		this.colors = colors;
		this.msg = msg;
		this.font = font;
		
		
	}

	public void draw (Graphics g)
	{
		g.setColor(colors[0]);
		g.fillRect(x - edgeLength, y - edgeLength, w + 2 * edgeLength, h + 2 * edgeLength);
		
		g.setColor(colors[1]);
		g.fillRect(x, y, w, h);
		
		g.setColor(colors[2]);
		g.setFont(font);
		
		/* Font Metrics: https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
		
			Oddly, the drawing takes place from the bottom left corner
		*/
		
		int width = g.getFontMetrics().stringWidth(msg);
		int height = g.getFontMetrics().getMaxAscent() - g.getFontMetrics().getMaxDescent();
		
		g.drawString(msg, x + w / 2 - width / 2, y + h / 2 + height / 2);
	
		
		
		
		
		
	}
	
	
	public boolean isMouseInside (int mX, int mY)
	{
		return mX >= x && mX <= x + w && mY >= y && mY <= y + w;
	}
}
