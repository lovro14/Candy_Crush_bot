package hr.fer.axilis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Solver {
	
	private Map<String, ArrayList<Integer>> moveIndices = new HashMap<>();
	private int startBoardIndex = 0; 
	private int endBoardIndex = 8; 
	
	public Solver(){
		initSolverIndices();
	}
	
	
	public void initSolverIndices(){
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(0);
		temp.add(-1);
		moveIndices.put("left", temp);
		temp = new ArrayList<>();
		temp.add(0);
		temp.add(1);
		moveIndices.put("right",temp);
		temp = new ArrayList<>();
		temp.add(-1);
		temp.add(0);
		moveIndices.put("up", temp);
		temp = new ArrayList<>();
		temp.add(1);
		temp.add(0);
		moveIndices.put("down", temp);
	}
	

	public NextMove calculateDirectionScore(int x, int y, String boardElement, ArrayList<ArrayList<String>> board) {
		
		ArrayList<NextMove> possibleMoves = new ArrayList<NextMove>(); 
		
		// iterate over every direction
		for (Map.Entry<String, ArrayList<Integer>> direction : moveIndices.entrySet()){

			int directionX = x + direction.getValue().get(0);
			int directionY = y + direction.getValue().get(1);
			if(directionX > endBoardIndex || directionX < startBoardIndex || directionY > endBoardIndex || directionY < startBoardIndex){
				continue;
			}
			// check if boardElement in that direction is same if not search
			if (!board.get(directionX).get(directionY).equals(boardElement)) {
				int score;
				int horizontalCount = 0; 
				int verticalCount = 0; 
				
				switch(direction.getKey()){
				
				case "left":
					horizontalCount = countMatchesLeft(board,boardElement, directionX, directionY);
					verticalCount = countMatchesUp(board,boardElement, directionX, directionY) + countMatchesDown(board,boardElement, directionX, directionY); 
					break;
					
				case "right":
					horizontalCount = countMatchesRight(board, boardElement, directionX, directionY); 
					verticalCount = countMatchesUp(board,boardElement, directionX, directionY) + countMatchesDown(board,boardElement, directionX, directionY);
					break;
				
				case "up":
					horizontalCount = countMatchesLeft(board, boardElement, directionX, directionY) + countMatchesRight(board, boardElement, directionX, directionY);
					verticalCount = countMatchesUp(board, boardElement, directionX, directionY);
					break;
				
				case "down":
					horizontalCount = countMatchesLeft(board, boardElement, directionX, directionY) + countMatchesRight(board, boardElement, directionX, directionY);
					verticalCount = countMatchesDown(board, boardElement, directionX, directionY);
					break;
				}
                // score cross of vertical and horizontal higher
				if (verticalCount >= 2 && horizontalCount >= 2) {
					score = verticalCount + horizontalCount; 
				}
				else {
					score = Math.max(verticalCount, horizontalCount); 
				}
				possibleMoves.add(new NextMove(directionX, directionY, (double)score)); 
			}
		}
		// return best move with highest score
		if (possibleMoves.size()>0){
			Collections.sort(possibleMoves, 
	                (o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
			return possibleMoves.get(0); 
		}
		else {
			return new NextMove(x, y, 0.0);
		}
	}


	private int countMatchesRight(ArrayList<ArrayList<String>> board, String boardElement, int directionX,
			int directionY) {
		int count = 0; 
		directionY++;
		while(directionY <= endBoardIndex) {		 
			if (board.get(directionX).get(directionY).equals(boardElement)) {
				count++; 
			} 
			else {
				break; 
			}
			directionY++;
		}
		return count;
	}


	private int countMatchesDown(ArrayList<ArrayList<String>> board, String boardElement, int directionX,
			int directionY) {
		int count = 0; 
		directionX++;
		while(directionX <= endBoardIndex) {
			 
			if (board.get(directionX).get(directionY).equals(boardElement)) {
				count++; 
			}
			else {
				break; 
			}
			directionX++;
		}
		return count;
	}


	private int countMatchesUp(ArrayList<ArrayList<String>> board, String boardElement, int directionX,
			int directionY) {
		int count = 0; 
		directionX--;
		while (directionX >= startBoardIndex) {		 
			if (board.get(directionX).get(directionY).equals(boardElement)) {
				count++; 
			}
			else {
				break; 
			}
			directionX--;
		}
		return count;
	}


	private int countMatchesLeft(ArrayList<ArrayList<String>> board, String boardElement, int directionX,
			int directionY) {		
		int count = 0;
		directionY--;
		while(directionY >= startBoardIndex) {			 
			if (board.get(directionX).get(directionY).equals(boardElement)) {
				count++; 
			} 
			else {
				break; 
			}
			directionY--;
		}
		return count;
	}
}