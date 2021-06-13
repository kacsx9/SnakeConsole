package kck.SnakeConsole.Elements;

public abstract class Element {
	
	protected char img; //representative of the element on the board
	protected int x, y; //coordinates of the element (x  - vertical, y - horizontal)

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//returning the char of the element
	public char getImg() {
		return img;
	}
	
	//returning co-ordinates of the element
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//updating coordinates of the element after move
	public void moveRight() {
		y++;
	}
	public void moveLeft() {
		y--;	
	}
	public void moveUp() {
		x--;
	}
	public void moveDown() {
		x++;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
