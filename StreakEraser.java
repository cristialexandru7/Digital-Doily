import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StreakEraser implements Drawing {

	private ArrayList<Point> points;
	private Color color;
	private int penSize;
	private DrawPanel panel;
	private boolean reflectPoints = true;

	private float alphaValue = 1.0f;
	private int compositeRule = AlphaComposite.SRC_IN;
	private AlphaComposite ac;

	public StreakEraser(DrawPanel panel) {
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
		this.setPenSize(panel.getPenSize());
		this.setReflectPoints();
	}

	public void paintDrawing(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D big = bi.createGraphics();
	    
	    ac = AlphaComposite.getInstance(compositeRule, alphaValue);

	    big.setColor(Color.BLACK);
		big.setStroke(new BasicStroke(penSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		
		big.translate(panel.getWidth() / 2, panel.getHeight() / 2);
		
		for (int i = 0; i < points.size(); i++) {
			if(i != 0) {
				for(int j = 0; j < panel.getNoOfSectors(); j++) {
					big.drawLine((int) points.get(i-1).getX(), (int) points.get(i-1).getY(), (int) points.get(i).getX(), (int) points.get(i).getY());
					if(reflectPoints)
						big.drawLine(-(int) points.get(i-1).getX(), (int) points.get(i-1).getY(), -(int) points.get(i).getX(), (int) points.get(i).getY());
					big.rotate(Math.toRadians(panel.getAngleOfSectors()));
				}
			}
		}
		
		big.translate( - panel.getWidth() / 2, - panel.getHeight() / 2);
		
		big.setComposite(ac);

		big.drawImage(panel.getBackgroundImage(), 0, 0, panel);
		
		g2D.drawImage(bi, - panel.getWidth() / 2, - panel.getHeight() / 2, panel);
	}

}
