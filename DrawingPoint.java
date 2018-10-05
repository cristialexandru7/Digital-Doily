import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class DrawingPoint implements Drawing {

	private Point point;
	private Color color;
	private int penSize;
	private DrawPanel panel;
	private boolean reflectPoints = true;

	public DrawingPoint(DrawPanel panel) {
		this.panel = panel;
	}

	public void addPoint(Point point) {
		this.point = point;
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

		for(int j = 0; j < panel.getNoOfSectors(); j++) {
			g2D.fillOval((int) point.getX(), (int) point.getY(), penSize, penSize);
			if(reflectPoints)
				g2D.fillOval(-(int) point.getX() - penSize, (int) point.getY(), penSize, penSize);
			g2D.rotate(Math.toRadians(panel.getAngleOfSectors()));
		}

	}
}
