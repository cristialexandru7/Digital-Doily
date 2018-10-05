import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class PointEraser implements Drawing {

	private Point point;
	private Color color;
	private int penSize;
	private DrawPanel panel;
	private boolean reflectPoints = true;

	private float alphaValue = 1.0f;
	private int compositeRule = AlphaComposite.SRC_IN;
	private AlphaComposite ac;

	public PointEraser(DrawPanel panel) {
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
		this.setPenSize(panel.getPenSize());
		this.setReflectPoints();
	}

	public void paintDrawing(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D big = bi.createGraphics();

		ac = AlphaComposite.getInstance(compositeRule, alphaValue);

		big.setColor(Color.BLACK);
		
		big.translate(panel.getWidth() / 2, panel.getHeight() / 2);

		for(int j = 0; j < panel.getNoOfSectors(); j++) {
			big.fillOval((int) point.getX(), (int) point.getY(), penSize, penSize);
			if(reflectPoints)
				big.fillOval(-(int) point.getX() - penSize, (int) point.getY(), penSize, penSize);
			big.rotate(Math.toRadians(panel.getAngleOfSectors()));
		}

		big.translate( - panel.getWidth() / 2, - panel.getHeight() / 2);
		
		big.setComposite(ac);
		big.drawImage(panel.getBackgroundImage(), 0, 0, panel);

		g2D.drawImage(bi, - panel.getWidth() / 2, - panel.getHeight() / 2, panel);
	}
}
