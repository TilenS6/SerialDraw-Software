package net.tilens.serialdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Class intended for drawing pixels to Canvas
 * @author TilenS
 * @author Znidi
 * @author chocoearly44
 */
public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 7774158272893625426L;
	
	private int vert, hor, lineWidth;
	private Color lineColor;
	private Color backgroundColor;
	private Color[][] whereToColor;
	
	/**
	 * Function draws <i>vert</i> number of vertical lines and <i>hor</i> number of horizontal lines.
	 * @param vert Number of vertical lines
	 * @param hor Number of horizontal lines
	 */
	public DrawingPanel(int vert, int hor) {
		this(vert, hor, 1);
	}
	
	/**
	 * Function draws <i>vert</i> number of vertical lines and <i>hor</i> number of horizontal lines with width of <i>lineWidth</i>.
	 * @param vert Number of vertical lines
	 * @param hor Number of horizontal lines
	 * @param lineWidth Width of lines
	 
	 */
	public DrawingPanel(int vert, int hor, int lineWidth) {
		this(vert, hor, 1, Color.BLACK);
	}
	
	/**
	 * Function draws <i>vert</i> number of vertical lines and <i>hor</i> number of horizontal lines with width of <i>lineWidth</i> and color of <i>lineColor</i>.
	 * @param vert Number of vertical lines
	 * @param hor Number of horizontal lines
	 * @param lineWidth Width of lines
	 * @param lineColor Line color
	 */
	public DrawingPanel(int vert, int hor, int lineWidth, Color lineColor) {
		this(vert, hor, lineWidth, lineColor, Color.WHITE);
	}
	
	/**
	 * 
	 * <p>Function draws <i>vert</i> number of vertical lines and <i>hor</i> number of horizontal lines with width of <i>lineWidth</i> and color of <i>lineColor</i>.</p>
	 * <p>It sets background to color of <i>backgroundColor</i>.
	 * @param vert Number of vertical lines
	 * @param hor Number of horizontal lines
	 * @param lineWidth Width of lines
	 * @param backgroundColor Background color
	 
	 */
	public DrawingPanel(int vert, int hor, int lineWidth, Color lineColor, Color backgroundColor) {
		this.vert = vert;
		this.hor = hor;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.backgroundColor = backgroundColor;
		whereToColor = new Color[vert][hor];
	}
	
	/**
	 * Function is called when drawing of component starts
	 */
	public void paintComponent(Graphics g) {
		Dimension d = this.getSize();
		int vertSpace = d.width/vert;
		int horSpace = d.height/hor;
		int maxHeight = horSpace*hor;
		int maxWidth = vertSpace*vert;
		
        //Firstly, we draw lines
		g.setColor(lineColor);
		for(int i = 0; i<=vert; i++) {
			g.fillRect(i *vertSpace, 0, lineWidth, maxHeight);
			
		}
		for(int i = 0; i<=hor;i++) {
			g.fillRect(0, i*horSpace, maxWidth, lineWidth);
		}
        
        for (int i = 0; i < whereToColor.length; i++) {
			for (int j = 0; j < whereToColor[i].length; j++) {
				if(whereToColor[i][j]==null) {
					g.setColor(backgroundColor);
				}else {
					g.setColor(whereToColor[i][j]);	
				}
				g.fillRect(i*vertSpace +lineWidth, j*horSpace +lineWidth, vertSpace-lineWidth, horSpace-lineWidth);
			}
		}
    }

	/**
	 * Function draws pixel with coordinates <i>x</i> and <i>y</i> with black color.
	 * @param x Pixel position in a row
	 * @param y Pixel position in a column
	 */
	public void drawPixel(int x, int y) {
		drawPixel(x, y, Color.BLACK);
	}
	
	/**
	 * Function draws pixel with coordinates <i>x</i> and <i>y</i> with color of <i>color</i>.
	 * @param x Pixel position in a row
	 * @param y Pixel position in a column
	 * @param color Color of pixel
	 */
	public void drawPixel(int x, int y, Color color) {
		if(x>=0&&x<vert) {
			if(y>=0&&y<hor) {
				whereToColor[x][y]=color;
			}
		}
		repaint();
	}
	
	/**
	 * Function removes pixel from the canvas.
	 * @param x Pixel position in a row
	 * @param y Pixel position in a column
	 */
	public void removePixel(int x, int y) {
		if(x>=0&&x<vert) {
			if(y>=0&&y<hor) {
				whereToColor[x][y]=null;
			}
		}
		repaint();
	}
	
	/**
	 * Function sets color of canvas.
	 * @param color Background color
	 */
	public void setBackground(Color color) {
		backgroundColor = color;
		repaint();
	}
	
	/**
	 * Function clears pixels from the canvas.
	 */
	public void clearScreen() {
		for (int i = 0; i < 60; i++) {
			for (int j = 0; j < 60; j++) {
				removePixel(i, j);
			}
		}
	}
	
	/**
	 * Function displays prompt to connect Serial.
	 */
	public void connectRequest() {
		clearScreen();
		setBackground(Color.BLACK);
		App.window.setTitle("Serial Draw - Waiting for connection");
	}

	/**
	 * Function returns Height of canvas.
	 * @return canvas height
	 */
	public int getGoodHeight() {
		Dimension d = this.getSize();
		int horSpace = d.height/hor;
		return horSpace*hor;
	}
	/**
	 * Function returns Width of canvas.
	 * @return canvas width
	 */
	public int getGoodWidth() {
		Dimension d = this.getSize();
		int vertSpace = d.width/vert;
		return vertSpace*vert;
	}
	
	/**
	 * Function fills given area (From <i>startX</i>, <i>startY</i> to <i>endX</i>, <i>endY</i>) with color of <i>color</i>.
	 * @param startX Start position in a row.
	 * @param startX Start position in a column.
	 * @param startX End position in a row.
	 * @param startX End position in a column.
	 * @param color Color of the area.
	 */
	public void fillArea(int startX, int startY, int endX, int endY, Color color) {
		int tempX = 0;
		int tempY = 0;
		if (startX>endX) {
			tempX = startX;
			startX = endX;
			endX = tempX;
		}
		
		if (startY>endY) {
			tempY = startY;
			startY = endY;
			endY = tempY;
		}
		
		for(int y = startY; y < endY; y++) {
			for(int x = startX; x < endX; x++) {
				drawPixel(x, y, color);
			}
		}
	}
}