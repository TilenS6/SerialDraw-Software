package net.tilens.serialdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * razred namenjen risanju kvadratov
 */
public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 7774158272893625426L;
	
	private int vert, hor, lineWidth;
	private Color lineColor;
	private Color backgroundColor;
	private Color[][] kjeBarvam;
	/**
	 * vzame se default line width 1px in črna barva za črte ter belo ozadje
	 * @param vert koliko pokončnih črt
	 * @param hor koliko ležečih črt
	 */
	public DrawingPanel(int vert, int hor) {
		this(vert, hor, 1);
	}
	
	/**
	 * vzame se default črna barva črte in belo ozadje
	 * @param vert koliko pokončnih črt 
	 * @param hor koliko ležečih črt
	 * @param lineWidth debelina črte
	 
	 */
	public DrawingPanel(int vert, int hor, int lineWidth) {
		this(vert, hor, 1, Color.BLACK);
	}
	
	/**
	 * vzame se belo ozadje
	 * @param vert koliko pokončnih črt 
	 * @param hor koliko ležečih črt
	 * @param lineWidth debelina črte
	 * @param lineColor barva črte
	 
	 */
	public DrawingPanel(int vert, int hor, int lineWidth, Color lineColor) {
		this(vert, hor, lineWidth, lineColor, Color.WHITE);
	}
	/**
	 * 
	 * @param vert koliko pokončnih črt 
	 * @param hor koliko ležečih črt
	 * @param lineWidth debelina črte
	 * @param lineColor barva črte
	 * @param backgroundColor barva ozadja
	 
	 */
	public DrawingPanel(int vert, int hor, int lineWidth, Color lineColor, Color backgroundColor) {
		this.vert = vert;
		this.hor = hor;
		this.lineWidth = lineWidth;
		this.lineColor = lineColor;
		this.backgroundColor = backgroundColor;
		kjeBarvam = new Color[vert][hor];
	}
	
	/**
	 * funkcija, ki se kliče ob vsake ponovnem risanju komponente
	 
	 */
	public void paintComponent(Graphics g) {
		Dimension d = this.getSize();
		int vertSpace = d.width/vert;
		int horSpace = d.height/hor;
		int maxHeight = horSpace*hor;
		int maxWidth = vertSpace*vert;
        // začnemo najprej risat črte
		g.setColor(lineColor);
		for(int i = 0; i<=vert; i++) {
			g.fillRect(i *vertSpace, 0, lineWidth, maxHeight);//bomo risali pravokotnike zato, da lahko namesto tretjega parametra napišeš kolk pixlov je široka zadeva
			
		}
		for(int i = 0; i<=hor;i++) {
			g.fillRect(0, i*horSpace, maxWidth, lineWidth);
		}
        
        for (int i = 0; i < kjeBarvam.length; i++) {
			for (int j = 0; j < kjeBarvam[i].length; j++) {
				if(kjeBarvam[i][j]==null) {
					g.setColor(backgroundColor);
				}else {
					g.setColor(kjeBarvam[i][j]);	
				}
				g.fillRect(i*vertSpace +lineWidth, j*horSpace +lineWidth, vertSpace-lineWidth, horSpace-lineWidth);
			}
		}
    }

	/**
	 * barva se nastavi na default črno
	 * @param x mesto kvadrata v vrstici
	 * @param y mesto kvadrata v stolpcu
	 
	 */
	public void risiKvadrat(int x, int y) {
		risiKvadrat(x, y, Color.BLACK);
	}
	
	/**
	 * funkcija pobarva kvadrat mreže z željeno barvo
	 * @param x mesto kvadrata v vrstici
	 * @param y mesto kvadrata v stolpcu
	 * @param c barva kvadrata
	 
	 */
	public void risiKvadrat(int x, int y, Color c) {
		if(x>=0&&x<vert) {
			if(y>=0&&y<hor) {
				kjeBarvam[x][y]=c;
			}
		}
		repaint();
	}
	
	/**
	 * funkcija pobriše kvadrat iz mreže
	 * @param x mesto kvadrata v vrstici
	 * @param y mesto kvadrata v stolpcu
	 
	 */
	public void brisiKvadrat(int x, int y) {
		if(x>=0&&x<vert) {
			if(y>=0&&y<hor) {
				kjeBarvam[x][y]=null;
			}
		}
		repaint();
	}
	
	/**
	 * nastavimo barvo ozadja
	 * @param c
	 
	 */
	public void setBackground(Color c) {
		backgroundColor = c;
		repaint();
	}
	
	
	public void clearScreen() {
		for (int i = 0; i < 60; i++) {
			for (int j = 0; j < 60; j++) {
				brisiKvadrat(i, j);
			}
		}
	}
	
	public void connectRequest() {
		clearScreen();
		setBackground(Color.BLACK);
	}
	
	/**
	 * funkcija vrne željeno višino canvasa...
	 * @return
	 */
	public int getGoodHeight() {
		Dimension d = this.getSize();
		int horSpace = d.height/hor;
		return horSpace*hor;
	}
	
	/**
	 * cyka blyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	 * @return
	 */
	public void fillArea(int startX, int startY, int endX, int endY, Color c) {
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
				risiKvadrat(x, y, c);
			}
		}
	}
}