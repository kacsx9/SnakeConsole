package kck.SnakeConsole;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import kck.SnakeConsole.Elements.*;

public class Board {
	
	private Element[][] board; //game's board
	private ArrayDeque<Element> body; //snake's body 
	private boolean fruitEaten = true; //if the fruit's been eaten at the moment
	private Fruit fruit;
	
	//Constructor
	public Board() {
		board = new Element[Game.HEIGHT][Game.WIDTH];
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {
					board[i][j]= null;		
			}
		}
		body = new ArrayDeque<Element>();
		body.add((Head)new Head(0,0));
		fruit = new Fruit(10,10);
		this.generateFruit();
		this.updateBoard();		
	}
	
	//Printing the board method
	public char[][] getCharBoard() {
		
		char[][] charBoard = new char[Game.HEIGHT+2][Game.WIDTH+2];
		for (int i=0; i<charBoard.length; i++) {
			for (int j=0; j<charBoard[0].length; j++) {
				if (i==0 || i==charBoard.length-1 || j==0 || j==charBoard[0].length-1) {
					charBoard[i][j] = '#';
				}
				else {
					if (board[j-1][i-1] != null) {
						charBoard[i][j] = board[j-1][i-1].getImg();
					}
					else {
						charBoard[i][j] = ' ';
					}
				}
			}
		}
		
		return charBoard;
	}
	
	//Snake's moving methods
	public boolean moveRight() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.addLast( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );		
		body.getFirst().moveRight();
		boolean validation = moveBody(previousX, previousY);
		if (validation) {
			
			this.updateBoard();
		}
		return validation;
	}
	public boolean moveLeft() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveLeft();
		boolean validation = moveBody(previousX, previousY);
		if (validation) {
			
			this.updateBoard();
		}
		return validation;		
	}
	public boolean moveUp() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveUp();
		boolean validation = moveBody(previousX, previousY);
		if (validation) {
			this.updateBoard();
		}
		return validation;
	}
	public boolean moveDown() {
		int previousX, previousY;
		previousX = body.getFirst().getX();
		previousY = body.getFirst().getY();
		body.add( new BodyEl( body.getLast().getX(), body.getLast().getY() ) );
		body.getFirst().moveDown();
		boolean validation = moveBody(previousX, previousY);
		if (validation) {
			this.updateBoard();
		}
		return validation;
	}
	
	//Checking if the coordinates after move are valid and move the snake's tail
	private boolean moveBody(int previousX, int previousY) {
		if ( body.getFirst().getX() == fruit.getX() && body.getFirst().getY() == fruit.getY() ) {
			this.fruitEaten = true;
		}
		//if the coordinates are outside the board
		if ( body.getFirst().getX() < 0 || body.getFirst().getX() >= Game.WIDTH || body.getFirst().getY() < 0 || body.getFirst().getY() >= Game.HEIGHT ) {
			return false;
		}	
		else {		
			int tempX, tempY; 
			int i = 0;
			
			//if snake's head touches iltself's body
			tempX = body.getFirst().getX();
			tempY = body.getFirst().getY();
			boolean bodyTouched = false;
			for (Element e: body) {
				if ( i>0 && i+1 < body.size() ) {
					
					if (e.getX() == tempX && e.getY() == tempY) {
						bodyTouched = true;
						break;
					}
				}
				i++;
			}
			if (!bodyTouched ) {
				//moving snake's tail
				i = 0;
				for (Element e: body) {
					if ( i>0 && i+1 < body.size() ) {					
						tempX = e.getX();
						tempY = e.getY();
						e.setX(previousX);
						e.setY(previousY);
						previousX = tempX;
						previousY = tempY;	
					}
					i++;				
				}
				return true;
			}
			else {
				return false;
			}
			
		}
	}
	
	//Updating the Board after move
	private void updateBoard() {
				
		if(body.size()>1 && fruitEaten == false) {
			board[body.getLast().getX()][body.getLast().getY()] = null;
			body.removeLast();			
		}
		else if (body.size()>1 && fruitEaten == true) {
			board[fruit.getX()][fruit.getY()] = null;
			this.generateFruit();
		}

		for (Element e: body) {
			board[e.getX()][e.getY()] = e;			
		}
		
		board[fruit.getX()][fruit.getY()] = fruit;
		this.fruitEaten = false;
	}
	
	private void generateFruit() {
		
		boolean isOnBody = true;
		int fruitX = 0, fruitY = 0;
		while (isOnBody) {
			isOnBody = false;
			fruitX = ThreadLocalRandom.current().nextInt(0, Game.WIDTH );
			fruitY = ThreadLocalRandom.current().nextInt(0, Game.HEIGHT );
			for (Element e: body) {
				if ( fruitX == e.getX() && fruitY == e.getY() ) {
					isOnBody = true;
					break;
				}
			}
		}
		this.fruit.setX(fruitX);
		this.fruit.setY(fruitY);		
	}
	
}
