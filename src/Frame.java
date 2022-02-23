import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private Dimension frameSize;

	private GamePanel gamePanel;

	public Frame() {
		initializeFrame();
	}

	/*
	 * function that initializes some variables
	 */
	private void initializeFrame() {
		frameSize = new Dimension(500, 500);

		setSize(frameSize);
		setResizable(false);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUndecorated(true);

		setVisible(true);

		/*
		 * creates a new instance of "GamePanel" and sets it as content pane of "f" ->
		 * "gamePanel" includes all the things that will be displayed
		 */
		gamePanel = new GamePanel(frameSize);
		setContentPane(gamePanel);
	}

	public static void main(String[] args) {
		/*
		 * creates a new instance of "Frame" to start everything
		 */
		Frame f = new Frame();
	}
}