import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GalleryShow extends JPanel{

	private GalleryPanel galleryPanel;
	private int size = 0;
	private ArrayList<DoilyImage> doilies = new ArrayList<DoilyImage>();;
	private static final int MAX_CAPACITY = 12;
	private GridBagConstraints constraints = new GridBagConstraints(
		    0, GridBagConstraints.RELATIVE,    // x = 0, y = below previous element
		    1, 1,           // cell width = 1, cell height = 1
		    0.0, 0.0,        // how to distribute space: weightx = 0.0, weighty = 0,0 
		    GridBagConstraints.FIRST_LINE_END,  // anchor
		    GridBagConstraints.BOTH,    // fill
		    new Insets(0, 0, 0, 0),     // cell insets
		    0, 0);          // internal padding
	
	/**
	 * This constructor sets the parent object where GalleryShow is displayed
	 * @param galleryPanel	stores the GalleryPanel object of the app
	 */
	public GalleryShow(GalleryPanel galleryPanel) {
		this.galleryPanel = galleryPanel;
	}
	/**
	 * This method adds an saved Doily to the GalleryShow
	 * @param doiliesAsImages	the saved doily
	 */
	public void addImage(BufferedImage doilyAsImage) {
		if(size < MAX_CAPACITY) {
			//creates a new DoilyImage and initializing it
			DoilyImage doilyImage = new DoilyImage(doilyAsImage, this, size);
			doilyImage.init();
			size++;
			
			//add the saved doily in order to be displayed
			this.add(doilyImage, constraints);
			doilies.add(doilyImage);
		} else {
			//displays a error message if the user is trying to save more doilies than MAX_CAPACITY
			JOptionPane.showMessageDialog(null, "You can't save more than "+ MAX_CAPACITY +" doilies. \n"
					+ "Remove one doily in order to add another!");
		}
	}
	
	/**
	 * This method decrement the current size and all the position after the removed element
	 * @param position
	 */
	public void decrementSize(int position) {
		doilies.remove(position);
		size--;
		for(int i = position ; i < size; i++) {
			doilies.get(i).decrementPosition();
		}
		
		repaint();
		
		galleryPanel.revalidate();
		galleryPanel.repaint();
	}
	/**
	 * This method initializes the layout of current GalleryShow as a GridBagLayout
	 */
	public void init() {
		this.setLayout(new GridBagLayout());

	}
	
	/**
	 * This method paints the background of the GallerySHow as a gradient
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D) g;
		
		int width = getWidth();
		int height = getHeight();
		Color color1 = new Color(35, 83, 138);
		Color color2 = new Color(167, 207, 223);
		GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
		g2D.setPaint(gp);
		g2D.fillRect(0, 0, width, height);
		
		g2D.setColor(Color.WHITE);
		g2D.setStroke(new BasicStroke(4));
		g2D.drawLine(0, 0, 0, this.getHeight());
	}
}
