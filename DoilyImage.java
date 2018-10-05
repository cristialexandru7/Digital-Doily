import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DoilyImage extends JPanel
{

	GalleryShow galleryShow;

	private BufferedImage doilySaved;
	private BufferedImage doilyOriginalSaved;
	private int position;

	public DoilyImage(BufferedImage doilySaved, GalleryShow galleryShow, int position) {
		this.doilyOriginalSaved = doilySaved;
		this.doilySaved = DoilyImage.createThumbnail(doilySaved);
		this.galleryShow = galleryShow;
		this.position = position;
	}

	/**
	 * This method creates a thumbnail for a doily
	 * @param img the saved doily
	 * @return the thumbnail of the saved doily
	 */
	private static BufferedImage createThumbnail(BufferedImage img) {

		int thumbnailWidth = 150;

		int widthToScale, heightToScale;
		if (img.getWidth() > img.getHeight()) {

			heightToScale = (int)(1.1 * thumbnailWidth);
			widthToScale = (int)((heightToScale * 1.0) / img.getHeight() * img.getWidth());

		} else {
			widthToScale = (int)(1.1 * thumbnailWidth);
			heightToScale = (int)((widthToScale * 1.0) / img.getWidth() * img.getHeight());
		}

		BufferedImage resizedImage = new BufferedImage(widthToScale, heightToScale, img.getType());
		Graphics2D g2D = resizedImage.createGraphics();

		g2D.setComposite(AlphaComposite.Src);
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2D.drawImage(img, 0, 0, widthToScale, heightToScale, null);
		g2D.dispose();

		int x = (resizedImage.getWidth() - thumbnailWidth) / 2;
		int y = (resizedImage.getHeight() - thumbnailWidth) / 2;

		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
		}

		return resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailWidth);
	}

	public void decrementPosition() {
		this.position--;
	}

	public void init() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(doilySaved));
		image.setToolTipText("Click on this to open the doily in a new window.");
		image.addMouseListener(new MouseListener() {

			/**
			 * This method creates a pop-up window with a saved doily when the thumbnail is clicked
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame imageContent = new JFrame("Saved Dolie " + (position + 1));

				imageContent.setSize(doilyOriginalSaved.getWidth(), doilyOriginalSaved.getHeight()+100);

				JLabel originalImage = new JLabel("", SwingConstants.CENTER);
				originalImage.setIcon(new ImageIcon(doilyOriginalSaved));


				JScrollPane scrollableImage = new JScrollPane(originalImage);
				scrollableImage.setBorder(BorderFactory.createEmptyBorder());
				imageContent.add(scrollableImage);
				imageContent.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		this.add(image, BorderLayout.CENTER);

		JButton remove = new JButton();
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				galleryShow.remove(position);
				galleryShow.revalidate();
				galleryShow.repaint();
				galleryShow.decrementSize(position);
			}

		});

		JButton saveLocally = new JButton();
		saveLocally.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//creates a pop-up window in which the user can choose where to save the doily
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				fileChooser.setFileFilter(filter);

				int userSelection = fileChooser.showSaveDialog(fileChooser);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					fileToSave = new File(fileToSave.toString() + ".png"); 
					try {
						//saves the doily as a png image
						ImageIO.write(doilyOriginalSaved,"png",fileToSave.getAbsoluteFile());
						JOptionPane.showMessageDialog(null, "Saved succesfully.");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Please try again.");
					}
				}
			}

		});

		JPanel saveRemovePanel = new JPanel();
		saveRemovePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		saveRemovePanel.setLayout(new GridLayout(1,2));

		try {
			Image img = ImageIO.read(getClass().getResource("icons/save.png"));
			saveLocally.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			Image img = ImageIO.read(getClass().getResource("icons/remove.png"));
			remove.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		saveRemovePanel.add(saveLocally);
		saveRemovePanel.add(remove);

		this.add(saveRemovePanel, BorderLayout.SOUTH);

		this.setSize(200,200);
	}
}
