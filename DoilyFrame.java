import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class DoilyFrame extends JFrame {

	private int width;
	private int height;

	Action undoKeyAction;

	Action saveKeyAction;

	Action clearKeyAction;

	public DoilyFrame(String name) {
		super(name);
	}

	/**
	 * This method gets the screen size
	 */
	private void initScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
	}

	/**
	 * This method initializes the DoilyFrame
	 */
	public void init() {

		//initialize the screen size
		this.initScreenSize();
		this.setSize(width, height);

		this.setLocation(this.width/2-this.getSize().width/2, this.height/2-this.getSize().height/2);

		Container panel = this.getContentPane();
		panel.setLayout(new BorderLayout());

		DrawPanel doily = new DrawPanel();
		doily.init();

		GalleryPanel galleryPanel = new GalleryPanel();
		galleryPanel.init();

		//initialize the control panel
		ControlPanel controlPanel = new ControlPanel(doily, galleryPanel);
		controlPanel.init();

		//adding elements to the Container
		panel.add(doily, BorderLayout.CENTER);
		panel.add(galleryPanel, BorderLayout.EAST);

		panel.add(controlPanel, BorderLayout.NORTH);


		undoKeyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				doily.undo();
			}
		};
		
		saveKeyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				galleryPanel.addDoily(doily);
			}
		};
		
		clearKeyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				doily.clearPanel();
			}
		};

		this.addUndoKeyBind(doily);
		this.addClearKeyBind(doily);
		this.addSaveKeyBind(galleryPanel);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//creates a tutorial windows when the app is open
		Tutorial tutorial = new Tutorial("Tutorial");
		tutorial.init();
	}

	private void addUndoKeyBind(JComponent contentPane) {
		InputMap iMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = contentPane.getActionMap();
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
		aMap.put("undo", undoKeyAction);
	}
	
	private void addSaveKeyBind(JComponent contentPane) {
		InputMap iMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = contentPane.getActionMap();
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "save");
		aMap.put("save", saveKeyAction);
	}
	
	private void addClearKeyBind(JComponent contentPane) {
		InputMap iMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = contentPane.getActionMap();
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "clear");
		aMap.put("clear", clearKeyAction);
	}
}
