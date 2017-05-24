package hr.fer.axilis;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	
	private final double mutationRatio = 0.65;
	private int x;
	private int y;
	private double fitnessValue;
	private int moveX; 
	private int moveY; 
	private String boardElement;
	private ArrayList<ArrayList<String>> board;
	private Random rand = new Random();
	
	public Chromosome(int x, int y, String boardElement, ArrayList<ArrayList<String>> board) {
		this.x = x;
		this.y = y;
		this.setBoardElement(boardElement);
		this.board = board;
	}

	public Chromosome(Chromosome c) {
		this.x = c.getX(); 
		this.y = c.getY(); 
		this.fitnessValue = c.getFitnessValue(); 
		this.moveX = c.getMoveX(); 
		this.moveY = c.getMoveY();
		this.boardElement = c.getBoardElement(); 
		this.board = c.getBoard(); 
	}
	
	public void mutation(){
		if (rand.nextDouble() < mutationRatio) {
			int randId = rand.nextInt(9); 
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


	public String getBoardElement() {
		return boardElement;
	}

	public ArrayList<ArrayList<String>> getBoard() {
		return this.board; 
	}

	public void setBoardElement(String boardElement) {
		this.boardElement = boardElement;
	}
	
	@Override
	public String toString() {
		return "(x,y)= (" + x + ", " + y + ")  Move to (x,y) = (" + moveX  + ", " + moveY + ")" +  " Fitness value: " + fitnessValue;
	}
	

	public void evaluate(Solver solver) {
		NextMove nextMove = solver.calculateDirectionScore(x, y, board.get(x).get(y), board);
		fitnessValue = nextMove.getScore();
		moveX = nextMove.getMoveX();
		moveY = nextMove.getMoveY();
	}
}