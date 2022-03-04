/*
 * a "SnakeNode" represents one part of the snake
 * it stores its coordinates and the direction towards it is moving
 */
public class SnakeNode {
	public int x, y;

	public byte direction;

	public SnakeNode(int x, int y, byte direction) {
		this.x = x;
		this.y = y;

		this.direction = direction;
	}
}