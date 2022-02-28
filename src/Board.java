public class Board {
	public final int SNAKE = 1, BORDER = 2, APPLE = 3;
	
	private final int BOARD_WIDTH, BOARD_HEIGHT;
	
	public int[] board;
	
	public Boolean isSnakeTouchingItself = false;
	
	public Board(int boardWidth, int boardHeight) {
		BOARD_WIDTH = boardWidth;
		BOARD_HEIGHT = boardHeight;
	
		initializeBoard();
	}
	
	private void initializeBoard() {
		board = new int[BOARD_WIDTH * BOARD_HEIGHT];
	
		/*
		 * adds black borders at the edge of the board
		 */
		for (int a = 0; a < BOARD_WIDTH; a++) {
			board[a] = BORDER;
			board[a + (BOARD_HEIGHT - 1) * BOARD_WIDTH] = BORDER;
		}
		for (int a = 0; a < BOARD_HEIGHT; a++) {
			board[a * BOARD_WIDTH] = BORDER;
			try {
				board[(a * BOARD_WIDTH) - 1] = BORDER;
			} catch (Exception e) {
			}
		}
	}
	
	/*
	 * updates the content of the board
	 */
	public void updateBoard(Snake snake) {
		/*
		 * removes all old nodes of the snake from the board
		 */
		for (int pos = 0; pos < board.length; pos++) {
			if (board[pos] == SNAKE) {
				board[pos] = 0;
			}
		}
		
		/*
		 * adds the border again
		 */
		for (int a = 0; a < BOARD_WIDTH; a++) {
			board[a] = BORDER;
			board[a + (BOARD_HEIGHT - 1) * BOARD_WIDTH] = BORDER;
		}
		for (int a = 0; a < BOARD_HEIGHT; a++) {
			board[a * BOARD_WIDTH] = BORDER;
			try {
				board[(a * BOARD_WIDTH) - 1] = BORDER;
			} catch (Exception e) {
			}
		}
		
		/*
		 * adds all new nodes of the snake to the board
		 */
		for (SnakeNode node : snake.getSnake()) {
			/*
			 * checks if the snake is crossing over / crashed into itself
			 */
			if(board[node.x + node.y * BOARD_WIDTH] == SNAKE) {
				isSnakeTouchingItself = true;				
			}
			
			board[node.x + node.y * BOARD_WIDTH] = SNAKE;
		}
	}
	
	/*
	 * adds a new apple to the board
	 */
	public void addApple() {
		int pos;

		/*
		 * creates new positions for apples until it gets an empty field
		 */
		do {
			pos = (int) (Math.random() * board.length);
		} while (board[pos] == SNAKE || board[pos] == BORDER);

		/*
		 * places an apple at this position
		 */
		board[pos] = APPLE;
	}
}
