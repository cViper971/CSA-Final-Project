import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class SmartRectangle {

	private int x, y, w, h, edgeLength, padding;
	private Color[] colors;
	private String msg; 
	private Font font;

	// The colors array should be formatted as [edgeColor, fillColor, textColor, hoverColor]
	public SmartRectangle (int x, int y, int w, int h, int edgeLength, int padding, Color[] colors, String msg, Font font) 
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.edgeLength = edgeLength;
		this.padding = padding;
		this.colors = colors;
		this.msg = msg;
		this.font = font;


	}

	public void draw (Graphics g)
	{
		g.setColor(colors[0]);
		g.fillRect(x - edgeLength - padding, y - edgeLength - padding, w + 2 * (edgeLength + padding), h + 2 * (edgeLength + padding));

		g.setColor(colors[1]);
		g.fillRect(x - padding, y - padding, w + 2 * padding, h + 2 * padding);

		g.setColor(colors[2]);
		g.setFont(font);

		/* Font Metrics: https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
		
			Oddly, the drawing takes place from the bottom left corner
		*/

		int width = g.getFontMetrics().stringWidth(msg);
		int height = g.getFontMetrics().getMaxAscent() - g.getFontMetrics().getMaxDescent();

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		g.drawString(msg, x + w / 2 - width / 2, y + h / 2 + height / 2);

	}


	public boolean isMouseInside (int mX, int mY)
	{
	
		int minX = x - edgeLength - padding;
		int maxX = minX + w + 2 * (edgeLength + padding);
		int minY = y - edgeLength - padding;
		int maxY = minY + h + 2 * (edgeLength + padding);
		
		System.out.println(minX + " " + maxX);
		System.out.println(minY + " " + maxY);
		System.out.println("******************");
		
		return mX >= minX && mX <= maxX && mY >= minY && mY <= maxY;
	}
}