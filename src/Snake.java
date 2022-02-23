import java.util.ArrayList;

/*
 * represents the snake
 */
public class Snake {
	/*
	 * constants for the directions towards the snake can move
	 */
	private final String UP = "up", DOWN = "down", RIGHT = "right", LEFT = "left";

	private final int BOARD_WIDTH, BOARD_HEIGHT;

	/*
	 * list that stores all the nodes that are a part of the snake the snakes head
	 * is at index 0
	 */
	private ArrayList<SnakeNode> snake;

	public Snake(int boardWidth, int boardHeight) {
		BOARD_WIDTH = boardWidth;
		BOARD_HEIGHT = boardHeight;

		snake = new ArrayList<SnakeNode>();
		/*
		 * creates the first node (head) of the snake
		 */
		snake.add(new SnakeNode(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, UP));
	}

	/*
	 * function that moves the snake one step in a direction
	 */
	public void updateSnakePosition(String direction) {
		snake.get(0).direction = direction;

		/*
		 * starts at the last node and sets its attributes to those of the node infront
		 * of it repeats for all nodes except the head
		 */
		for (int a = snake.size() - 1; a >= 1; a--) {
			snake.set(a, new SnakeNode(snake.get(a - 1).x, snake.get(a - 1).y, snake.get(a - 1).direction));
		}

		/*
		 * updates the head
		 */
		snake.set(0, nodeInFrontOfNode(snake.get(0)));
	}

	/*
	 * used to add a new node at the snakes tail
	 */
	public void addNode() {
		/*
		 * direction of the last node
		 */
		String direction = snake.get(snake.size() - 1).direction;

		/*
		 * coordinates of the last node
		 */
		int x = snake.get(snake.size() - 1).x;
		int y = snake.get(snake.size() - 1).y;

		/*
		 * moves coordinates one step in the opposite direction of the last node
		 */
		if (direction.equals(RIGHT)) {
			x -= 1;
		} else if (direction.equals(LEFT)) {
			x += 1;
		} else if (direction.equals(DOWN)) {
			y -= 1;
		} else {
			y += 1;
		}

		/*
		 * adds a new node at the calculated coordinates with the last nodes direction
		 * to the list
		 */
		snake.add(new SnakeNode(x, y, direction));
	}

	/*
	 * returns the node one step in the input nodes direction
	 */
	private SnakeNode nodeInFrontOfNode(SnakeNode node) {
		if (node.direction.equals(UP)) {
			node.y -= 1;
		} else if (node.direction.equals(DOWN)) {
			node.y += 1;
		} else if (node.direction.equals(RIGHT)) {
			node.x += 1;
		} else {
			node.x -= 1;
		}

		return node;
	}

	/*
	 * used to acces the list from outside of this class
	 */
	public ArrayList<SnakeNode> getSnake() {
		return snake;
	}
}