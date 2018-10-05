import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Icon Source: https://github.com/iconic/open-iconic
 */

public class ControlPanel extends JPanel {
	
	DrawPanel doily;
	GalleryPanel galleryPanel;

	public ControlPanel(DrawPanel doily, GalleryPanel galleryPanel) {
		this.doily = doily;
		this.galleryPanel = galleryPanel;
	}

	/**
	 * This method initializes the Control Panel
	 */
	public void init() {
		
		this.setLayout(new FlowLayout());

		JButton clear = new JButton("Clear");

		//sets icon for the clear button
		try {
			Image img = ImageIO.read(getClass().getResource("icons/clear.png"));
			clear.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		//adds clearPanel action to the clear button
		clear.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doily.clearPanel();
			}

		});
		
		//sets icon for the undo button
		JButton undo = new JButton("Undo");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/undo.png"));
			undo.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		//adds the undo action to the und button
		undo.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doily.undo();
			}

		});
		
		//sets icon for the redo button
		JButton redo = new JButton("Redo");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/redo.png"));
			redo.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		//adds the redo action to the redo button
		redo.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doily.redo();
			}

		});

		JButton save = new JButton("Save Doily");
		
		//sets icon for the save button
		try {
			Image img = ImageIO.read(getClass().getResource("icons/star.png"));
			save.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		//adds the save action to the save button
		save.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				galleryPanel.addDoily(doily);
			}

		});

		JButton color = new JButton("Change Colour");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/color.png"));
			color.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		//adds the color action to the color button
		color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//opens a pop-up windows where the user can choose the colour for drawing
				Color newColor = JColorChooser.showDialog(doily, "Choose Pen Colour", doily.getColor());
				if(newColor != null){
					doily.setColor(newColor);
				}				
			}

		});

		JLabel penLabel = new JLabel("Pen size:");
		SpinnerModel penSizeValue = new SpinnerNumberModel(doily.getPenSize(), 1, 1000, 1);
		JSpinner penSize = new JSpinner(penSizeValue);
		penSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				doily.setPenSize((int) penSize.getValue());
			}

		});

		JToggleButton eraser = new JToggleButton("Eraser");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/pencil.png"));
			eraser.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		eraser.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (eraser.isSelected()){
					eraser.setText("Pen");
				} else {
					eraser.setText("Eraser");
				}
				doily.toogleErase();
			}

		});

		JLabel sectorLabel = new JLabel("Number of sectors:");
		SpinnerModel valueSectors = new SpinnerNumberModel(doily.getNoOfSectors(), 1, 1000, 1);   
		JSpinner sectorSize = new JSpinner(valueSectors);
		sectorSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				doily.setNoOfSectors((int) sectorSize.getValue());
			}

		});

		JToggleButton sectorLines = new JToggleButton("Hide Sector Lines");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/eye.png"));
			sectorLines.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		sectorLines.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (sectorLines.isSelected()){
					sectorLines.setText("Show Sector Lines");
				} else {
					sectorLines.setText("Hide Sector Lines");
				}
				doily.toogleDisplaySectorLines();
			}

		});
		JToggleButton reflectPoints = new JToggleButton("Reflect Points: ON");
		
		try {
			Image img = ImageIO.read(getClass().getResource("icons/reflect.png"));
			reflectPoints.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		reflectPoints.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				if (reflectPoints.isSelected()){
					reflectPoints.setText("Reflect Points: OFF");
				} else {
					reflectPoints.setText("Reflect Points: ON");
				}
				doily.toogleReflectPoints();
			}

		});
		
		this.add(clear);
		this.add(undo);
		this.add(redo);
		this.add(save);

		this.add(color);
		this.add(penLabel);
		this.add(penSize);
		this.add(eraser);

		this.add(sectorLabel);
		this.add(sectorSize);
		this.add(sectorLines);
		this.add(reflectPoints);
	}

	/**
	 * This method paints the background of the ControlPanel as a gradient
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();
		Color color1 = new Color(167, 207, 223);
		Color color2 = new Color(35, 83, 138);
		GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
		g2D.setPaint(gp);
		g2D.fillRect(0, 0, width, height);

		g2D.setColor(Color.WHITE);
		g2D.setStroke(new BasicStroke(4));
		g2D.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight());

	}
}
