package hr.fer.axilis;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	
	private final double mutationRatio = 0.1;
	private int x;
	private int y;
	private double fitnessValue;
	private int moveX; 
	private int moveY; 
	private BoardElement boardElement;
	private ArrayList<ArrayList<BoardElement>> board;
	private Random rand = new Random();
	
	public Chromosome(int x, int y, BoardElement boardElement, ArrayList<ArrayList<BoardElement>> board) {
		this.x = x;
		this.y = y;
		this.setBoardElement(boardElement);
		this.board = board;
	}

	
	public void mutation(){
		if (rand.nextDouble() < mutationRatio) {
			int randId = rand.nextInt(6); 
			if (rand.nextDouble() < 0.5) {	
				setX(randId); 
			} 
			else { setY(randId); }
		} 
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public double getFitnessValue() {
		return fitnessValue;
	}


	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public int getMoveX() {
		return moveX;
	}
	
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	
	public int getMoveY() {
		return moveY; 
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}

	public double getMutationRatio() {
		return mutationRatio;
	}


	public BoardElement getBoardElement() {
		return boardElement;
	}


	public void setBoardElement(BoardElement boardElement) {
		this.boardElement = boardElement;
	}
	
	@Override
	public String toString() {
		return "(x,y)= (" + x + ", " + y + ")  Fitness value: " + fitnessValue; 
	}
	
	
	
	// Search algorithm and evaluation of scores and move
	public void evaluate() {
		// TODO Auto-generated method stub
		
		
		
		// postavlja fitness value
		// postavlja moveX, moveY za navedeni fitness 
	}
	
	

}
