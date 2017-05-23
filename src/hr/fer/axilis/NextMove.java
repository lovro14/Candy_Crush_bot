package hr.fer.axilis;

public class NextMove {
	
	private int startX; 
	private int startY; 
	private int nextX;
	private int nextY;
	private double score;
	
	public NextMove(int startX, int startY, int nextX, int nextY, double score) {
		this.startX = startX; 
		this.startY = startY; 
		this.nextX = nextX;
		this.nextY = nextY;
		this.score = score;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getNextX() {
		return nextX;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	

}
