import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawingStreak implements Drawing {

	private ArrayList<Point> points;
	private Color color;
	private int penSize;
	private DrawPanel panel;
	private boolean reflectPoints = true;

	public DrawingStreak(DrawPanel panel) {
		points = new ArrayList<Point>();
		this.panel = panel;
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void setPenSize(int penSize) {
		this.penSize = penSize;
	}

	public int getPenSize() {
		return this.penSize;
	}

	public void setReflectPoints() {
		this.reflectPoints = panel.getReflectPoints();
	}

	public void init() {
		this.setColor(panel.getColor());
		this.setPenSize(panel.getPenSize());
		this.setReflectPoints();
	}

	public void paintDrawing(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(color);
		//sets the stroke in order to have a smoother drawing
		g2D.setStroke(new BasicStroke(penSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

		for (int i = 0; i < points.size(); i++) {
			if(i != 0) {
				for(int j = 0; j < panel.getNoOfSectors(); j++) {
					g2D.drawLine((int) points.get(i-1).getX(), (int) points.get(i-1).getY(), (int) points.get(i).getX(), (int) points.get(i).getY());
					if(reflectPoints)
						g2D.drawLine(-(int) points.get(i-1).getX(), (int) points.get(i-1).getY(), -(int) points.get(i).getX(), (int) points.get(i).getY());
					g2D.rotate(Math.toRadians(panel.getAngleOfSectors()));
				}
			}

		}
	}

}
