import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Tutorial extends JFrame {
	
	private int width;
	private int height;
	
    final static String BUTTONPANEL = "Tab with JButtons";
    final static String TEXTPANEL = "Tab with JTextField";
    final static int extraWindowWidth = 100;
	
    private GridBagConstraints constraints = new GridBagConstraints(
		    0, GridBagConstraints.RELATIVE,    // x = 0, y = below previous element
		    1, 1,           // cell width = 1, cell height = 1
		    0.0, 0.0,        // how to distribute space: weightx = 0.0, weighty = 0,0 
		    GridBagConstraints.FIRST_LINE_END,  // anchor
		    GridBagConstraints.BOTH,    // fill
		    new Insets(0, 0, 0, 0),     // cell insets
		    0, 0);          // internal padding
    
	public Tutorial(String name) {
		super(name);
	}
	
	private void initScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
	}
	
	private Image getScaledImage(Image srcImg, int w){
	    int h = (w*srcImg.getHeight(null)) / srcImg.getWidth(null);
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	public void init() {
		
		//initialize the screen size
		this.initScreenSize();
		this.setSize(width/2, height/2);
		this.setLocation(this.width/2-this.getSize().width/2, this.height/2-this.getSize().height/2);
		
		Container cards = this.getContentPane();
		cards.setLayout(new CardLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		 
        //Create the "cards".
        JPanel card1 = new JPanel();
        
        JLabel welcomeText = new JLabel("<html>Welcome to <strong>Digital Doilies Drawer</strong>!  <br/>" + 
        		"Short Keyboard Commands: <ul>" + 
        		"<li><strong>CTRL(COMMAND) + Z</strong> - Undo</li>" + 
        		"<li><strong>CTRL(COMMAND) + S</strong> - Save current doily</li>" + 
        		"<li><strong>CTRL(COMMAND) + F</strong> - Clear drawing panel</li></ul>" + 
        		"The app is divided in 3 main sections: <ul><li>Draw Panel (1)</li> <li>Control Panel (2)</li> <li>Gallery (3)</li></ul</html>", SwingConstants.LEFT);
        JLabel welcomeImage = new JLabel("", SwingConstants.CENTER);
        try {
			Image img = ImageIO.read(getClass().getResource("tutorialImages/photo1.png"));
			welcomeImage.setIcon(new ImageIcon(this.getScaledImage(img,this.width/2 - 50)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
        
		card1.setLayout(new GridBagLayout());
		card1.add(welcomeText, constraints);
		card1.add(welcomeImage, constraints);
 
        JPanel card2 = new JPanel();
        JLabel drawPanelText = new JLabel("<html>Your drawing panel is ready to start. Just click to draw a point or <br/>"
        		+ "drag the mouse on the screen in order to draw a streak.</html>", SwingConstants.LEFT);
        JLabel drawPanelImage = new JLabel("", SwingConstants.CENTER);
        try {
			Image img = ImageIO.read(getClass().getResource("tutorialImages/photo2.png"));
			drawPanelImage.setIcon(new ImageIcon(this.getScaledImage(img,this.width/2 - 50)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
        
		card2.setLayout(new GridBagLayout());
		card2.add(drawPanelText, constraints);
		card2.add(drawPanelImage, constraints);
        
        JPanel card3 = new JPanel();
        JLabel controlPanelText = new JLabel("<html>In your Control Panel, you can clear, undo or redo any changes you make on the doily.<br/>"
        		+ "Also, you can choose your pen size, pen color or whether to reflect what you are drawing. <br/>"
        		+ "An eraser is there if you want to remove something you previously drawn, a toogle for displaying <br/>"
        		+ "the sector lines and a spinner to choose how many sectors do you have.<br/></html>", SwingConstants.LEFT);
        JLabel controlPanelImage = new JLabel("", SwingConstants.CENTER);
        try {
			Image img = ImageIO.read(getClass().getResource("tutorialImages/photo3.png"));
			controlPanelImage.setIcon(new ImageIcon(this.getScaledImage(img,this.width/2 - 50)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
        
		card3.setLayout(new GridBagLayout());
		card3.add(controlPanelText, constraints);
		card3.add(controlPanelImage, constraints);
        
        JPanel card4 = new JPanel();
        JLabel galleryPanelText = new JLabel("<html>Gallery will show when you save your first doily and it will dissaper after you remove everything from it.<br/>"
        		+ "For every doily saved, you can choose to save it on your harddisk as a .png image or to remove it.<br/>"
        		+ "By pressing the thumbnail, a pop-up window will appear where you can see you doily in full screen.</html>", SwingConstants.LEFT);
        JLabel galleryPanelImage = new JLabel("", SwingConstants.CENTER);
        try {
			Image img = ImageIO.read(getClass().getResource("tutorialImages/photo4.png"));
			galleryPanelImage.setIcon(new ImageIcon(this.getScaledImage(img,this.width/2 - 50)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
        
		card4.setLayout(new GridBagLayout());
		card4.add(galleryPanelText, constraints);
		card4.add(galleryPanelImage, constraints);
 
        tabbedPane.addTab("Introduction", new JScrollPane(card1));
        tabbedPane.addTab("Draw Panel", new JScrollPane(card2));
        tabbedPane.addTab("Control Panel", new JScrollPane(card3));
        tabbedPane.addTab("Gallery", new JScrollPane(card4));
 
        cards.add(tabbedPane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
