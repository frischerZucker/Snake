import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

/*
 * all the content gets drawn on to the "GamePanel"
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
	/*
	 * size of the board on which the game is played
	 */
	private final int BOARD_WIDTH = 22, BOARD_HEIGHT = 22;

	/*
	 * constants for the directions towards the snake can move
	 */
	private final String UP = "up", DOWN = "down", RIGHT = "right", LEFT = "left";

	/*
	 * constants that represent possible states of the board and what they mean
	 */
	private final int SNAKE = 1, BORDER = 2, APPLE = 3;

	private Dimension panelSize;

	private BufferedImage img;

	private Thread thread;

//TODO board in eigene Klasse auslagern

	private int[] pixels;

	/*
	 * how often the game gets updated per second
	 */
	private int tickSpeed = 5, score = 0;

	private String direction;

	private Boolean isRunning = false, isPaused = false, isGameOver = false;

	private Snake snake;

	private Board board;
	
	/*
	 * function that initializes some variables
	 */
	public GamePanel(Dimension frameSize) {
		panelSize = new Dimension(frameSize);

		setSize(panelSize);

		setBackground(Color.black);

		setVisible(true);

		setFocusable(true);

		addKeyListener(this);

		/*
		 * initializes img and connects the pixels array to it
		 */
		img = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		/*
		 * initializes a array that stores the content of the board
		 */
		board = new Board(BOARD_WIDTH, BOARD_HEIGHT);

		/*
		 * direction the snake is moving to in the beginning
		 */
		direction = UP;

		/*
		 * creates a new snake with a length of 4
		 */
		snake = new Snake(BOARD_WIDTH, BOARD_HEIGHT);
		snake.addNode();
		snake.addNode();
		snake.addNode();

		board.addApple();

		thread = new Thread(this);

		start();
	}

	private Boolean checkForCollision(int object) {
		for (SnakeNode node : snake.getSnake()) {
			if (board.board[node.x + node.y * BOARD_WIDTH] == object) {
				return true;
			}
		}
		return false;
	}

	/*
	 * updates the image that is displayed iterates through all the pixels and sets
	 * it to a color dependent on what is at the pixels position on the board
	 */
	private void updateScreen() {
		for (int a = 0; a < pixels.length; a++) {
			if (board.board[a] == SNAKE) {
				pixels[a] = Color.DARK_GRAY.getRGB();
				continue;
			}
			if (board.board[a] == BORDER) {
				pixels[a] = Color.black.getRGB();
				continue;
			}
			if (board.board[a] == APPLE) {
				pixels[a] = Color.red.getRGB();
				continue;
			}

			pixels[a] = Color.gray.getRGB();
		}
	}

	/*
	 * used to start the thread
	 */
	private void start() {
		isRunning = true;
		thread.start();
	}

	/*
	 * used to stop the thread
	 */
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}

	/*
	 * called when the thread is started
	 */
	@Override
	public void run() {
		/*
		 * time of the last iteration of the game loop
		 */
		long lastTime = System.nanoTime();

		/*
		 * 
		 */
		double deltaTime = 0;

		/*
		 * game loop
		 */
		while (isRunning) {
			/*
			 * time of this iteration
			 */
			long currentTime = System.nanoTime();

			deltaTime += currentTime - lastTime;

			lastTime = currentTime;

			/*
			 * skips the rest of the code inside of this loop if its to early
			 */
			if (deltaTime < 1000000000 / tickSpeed) {
				continue;
			}

			/*
			 * skips the rest of the code if game is either paused or over
			 */
			if (isGameOver || isPaused) {
				repaint();
				continue;
			}

			snake.updateSnakePosition(direction);

			// TODO check for collision with snake

			/*
			 * checks for a collision with the border, which would toggle a "game over"
			 */
			isGameOver = checkForCollision(BORDER);

			/*
			 * checks for collision with an apple -> increases the score and the snakes
			 * length
			 */
			if (checkForCollision(APPLE)) {
				score++;

				snake.addNode();

				board.addApple();

				/*
				 * checks for collision to check if the snakes tail grows into the border
				 */
//				if(checkForCollision(BORDER)) {
//					isGameOver = true;
//					continue;
//				}
			}

			board.updateBoard(snake);

			updateScreen();

			repaint();

			deltaTime = 0;
		}
	}

	/*
	 * draws all the things
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setFont(new Font("", 1, 20));
		g.setColor(Color.green);

		g.drawImage(img, 0, 0, panelSize.width, panelSize.height, this);

		g.drawString("score: " + Integer.toString(score), 10, 18);

		g.setFont(new Font("", 1, 50));

		if (isGameOver) {
			g.drawString("GAME OVER", 100, panelSize.height / 2);
		}

		if (isPaused) {
			g.drawString("game paused", 100, panelSize.height / 2);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * keyevents for movement
		 */
		if (e.getKeyCode() == KeyEvent.VK_W && !direction.equals(DOWN)) {
			direction = UP;
		} else if (e.getKeyCode() == KeyEvent.VK_S && !direction.equals(UP)) {
			direction = DOWN;
		} else if (e.getKeyCode() == KeyEvent.VK_A && !direction.equals(RIGHT)) {
			direction = LEFT;
		} else if (e.getKeyCode() == KeyEvent.VK_D && !direction.equals(LEFT)) {
			direction = RIGHT;
		}
		/*
		 * keyevent used to pause the game
		 */
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (isPaused) {
				isPaused = false;
			} else {
				isPaused = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}