package hr.fer.axilis;

public class NextMove {
	
	private int moveX;
	private int moveY;
	private double score;
	
	public NextMove(int moveX, int moveY, double score) {
		this.moveX = moveX;
		this.moveY = moveY;
		this.score = score;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}