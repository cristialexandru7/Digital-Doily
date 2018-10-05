import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class DrawPanel extends JPanel{

	boolean displaySectorLines = true;
	private int noOfSectors = 12;
	private double angleOfSectors;
	private int width;
	private int height;
	private int minWH;
	private Color color = Color.red;
	private int penSize = 4;

	private ArrayList<Drawing> drawings = new ArrayList<Drawing>();
	private ArrayList<Drawing> drawingsRemoved = new ArrayList<Drawing>();

	private DrawingStreak pointsList = new DrawingStreak(this);
	private DrawingPoint point = new DrawingPoint(this);
	private PointEraser erasedPoint = new PointEraser(this);
	private StreakEraser erasedPointsList = new StreakEraser(this);

	private boolean reflectPoints = true;
	private boolean erase = false;
	private Color erasedColor;
	private boolean dragging = false;

	private BufferedImage bi;

	public void toogleErase() {
		if(this.erase == true) {
			this.erase = false;
			this.setColor(erasedColor);
		} else {
			erasedColor = this.getColor();
			this.setColor(this.getBackground());
			this.erase = true;
		}
	}

	public boolean getReflectPoints() {
		return this.reflectPoints;
	}

	public void toogleReflectPoints() {
		if(this.reflectPoints == true)
			this.reflectPoints = false;
		else
			this.reflectPoints = true;
	}

	public void toogleDisplaySectorLines() {
		if(this.displaySectorLines == true)
			this.displaySectorLines = false;
		else
			this.displaySectorLines = true;
		repaint();
	}

	public void setColor(Color color) {
		if(this.erase == true)
			this.erasedColor = color;
		else
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

	public BufferedImage getBackgroundImage() {
		return this.bi;
	}

	public void clearPanel() {
		if(drawings.size() != 0) {
			String[] options = {"Ok", "Cancel"};
			int option = JOptionPane.showOptionDialog(null, "You are about to clear the doily. You can't undo the clear opperation.\n"
					+ "Please, save any progress you may want to keep.\n\nPress Ok to continue.",
					"Clear Warning",
					JOptionPane.WARNING_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if(option == 0) {
				drawings = new ArrayList<Drawing>();
				drawingsRemoved = new ArrayList<Drawing>();
			}
			repaint();
		}
	}

	public void undo() {
		if(drawings.size() != 0) {
			drawingsRemoved.add(drawings.get(drawings.size() - 1));
			drawings.remove(drawings.size() - 1);
		}
		repaint();
	}

	public void redo() {
		if(drawingsRemoved.size() != 0) {
			drawings.add(drawingsRemoved.get(drawingsRemoved.size() - 1));
			drawingsRemoved.remove(drawingsRemoved.size() - 1);
		} 
		repaint();
	}

	public boolean getDisplaySectorLines() {
		return displaySectorLines;
	}

	public void setDisplaySectorLines(boolean displaySectorLines) {
		this.displaySectorLines = displaySectorLines;
	}

	public int getNoOfSectors() {
		return noOfSectors;
	}

	public void setNoOfSectors(int noOfSectors) {
		this.noOfSectors = noOfSectors;
		repaint();
	}

	public double getAngleOfSectors() {
		return angleOfSectors;
	}

	public void initAngleOfSectors() {
		this.angleOfSectors = (double) 360/this.getNoOfSectors();
	}

	public int getMinWH() {
		return minWH;
	}

	public void initMinWH() {
		if(this.getWidth() < this.getHeight())
			this.minWH = this.getWidth();
		else
			this.minWH = this.getHeight();
	}

	public void init() {
		this.setBackground(Color.BLACK);

		width = getWidth();
		height = getHeight();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(erase == true) {
					erasedPoint.init();
					erasedPoint.addPoint(new Point(e.getX() - width/2 - penSize/2, e.getY() - height/2 - penSize/2));
					drawings.add(erasedPoint);
					repaint();
					erasedPoint = new PointEraser(DrawPanel.this);
				} else {
					point.init();
					point.addPoint(new Point(e.getX() - width/2 - penSize/2, e.getY() - height/2 - penSize/2));
					drawings.add(point);
					repaint();
					point = new DrawingPoint(DrawPanel.this);
				}
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent me) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {

				if(erase == true) {
					erasedPointsList.init();
				} else
					pointsList.init();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(dragging) {
					if(erase == true) {
						drawings.add(erasedPointsList);
						erasedPointsList = new StreakEraser(DrawPanel.this);
						drawingsRemoved = new ArrayList<Drawing>();
						dragging = false;
					} else {
						drawings.add(pointsList);
						pointsList = new DrawingStreak(DrawPanel.this);
						drawingsRemoved = new ArrayList<Drawing>();
						dragging = false;
					}
					repaint();
				}
			}


		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				dragging = true;
				if(erase == true) {
					erasedPointsList.addPoint(new Point(e.getX() - width/2, e.getY() - height/2));
				} else {
					pointsList.addPoint(new Point(e.getX() - width/2, e.getY() - height/2));
				}
				repaint();
			}

			public void mouseMoved(MouseEvent e) {

			}
		});

	}

	/**
	 * This method draws the sector lines on the screen.
	 * @param g2D
	 */
	public void paintBackgroundImage(Graphics2D g2D) {
		bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D big = bi.createGraphics();

		big.setColor(Color.black);
		big.fillRect(0, 0, this.getWidth(), this.getHeight());

		big.translate(this.width / 2, this.height / 2);

		if(this.displaySectorLines && this.getNoOfSectors() > 0) {

			big.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			big.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for(int i = 0; i < this.getNoOfSectors(); i++) {
				big.setColor(Color.white);
				big.drawLine(0, 0, 0, -this.minWH / 2);
				big.rotate(Math.toRadians(this.getAngleOfSectors()));
			}
		}

		big.translate( - this.width / 2, - this.height / 2);

		g2D.drawImage(bi, 0, 0, this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;

		this.initAngleOfSectors();
		this.initMinWH();

		width = getWidth();
		height = getHeight();

		this.paintBackgroundImage(g2D);

		g2D.translate(this.width / 2, this.height / 2);

		for(Drawing pointsList : drawings)
			pointsList.paintDrawing(g2D);

		if(erase == false)
			this.pointsList.paintDrawing(g2D);
		else
			this.erasedPointsList.paintDrawing(g2D);

	}

}
