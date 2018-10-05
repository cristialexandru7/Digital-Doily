import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class GalleryPanel extends JPanel {

	private GalleryShow galleryShow;

	/**
	 * This method takes the current DrawPanel and prepares it for the GallaryShow
	 * @param doily current DrawPanel
	 */
	public void addDoily(DrawPanel doily) {
		int width = doily.getWidth();
		int height = doily.getHeight();
		
		//creates a new image where the saved Doily will be stored
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//creates graphics on the image
		Graphics2D g2D = image.createGraphics();

		//sets the render hints in order to have a much leaner image
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//paints into the image the current Doily
		doily.paint(g2D);

		//adds in the GalleryShow the current image
		galleryShow.addImage(image);

		this.revalidate();
		this.repaint();
	}

	/**
	 * This method initializes the current class layout, create a new
	 */
	public void init() {
		this.setLayout(new BorderLayout());

		//creates and initializes the Gallery Show
		galleryShow = new GalleryShow(this);
		galleryShow.init();

		//sets a new scroll panel for cases when there is not enough space to display all saved Doilies
		JScrollPane scroll = new JScrollPane(galleryShow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		this.add(scroll, BorderLayout.CENTER);
	}

}
