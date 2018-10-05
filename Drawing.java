import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Drawing {
	
	/**
	 * This method adds a point to be drawn
	 * @param point
	 */
	public void addPoint(Point point);
	
	/**
	 * This method sets the colour of the drawing
	 * @param color
	 */
	public void setColor(Color color);
	
	/**
	 * This method gets the curent colour of the drawing
	 * @return color
	 */
	public Color getColor();
	
	/**
	 * This method sets the PenSize of the drawing
	 * @param penSize
	 */
	public void setPenSize(int penSize);
	
	/**
	 * This method gets the current the current PenSize
	 * @return the int value of the current PenSize
	 */
	public int getPenSize();
	
	/**
	 * This method sets the whether it should reflect points or not
	 */
	public void setReflectPoints();
	
	public void init();
	
	public void paintDrawing(Graphics g);
}
