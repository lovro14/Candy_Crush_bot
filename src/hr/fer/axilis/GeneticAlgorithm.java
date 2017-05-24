package hr.fer.axilis;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeneticAlgorithm {

	public static void main(String[] args) throws Exception {
		String registerToken;
		String gameToken;

		JavaWebAPI webAPI = new JavaWebAPI("xxxxxxxxx");

		/* Register token POST
		   JSONObject registerParameters = getRegisterJSON();
		   JSONObject registerResponse = webAPI.sendPost("/register",registerParameters);
		   registerToken = (String) registerResponse.get("token");
		 */

		registerToken = "xxxxxxxxxx";
		
		// New game POST
		JSONObject startGameParameters = getStartGameJSON(registerToken); 
		JSONObject startGameResponse = webAPI.sendPost("/game/new", startGameParameters);
		gameToken = startGameResponse.getString("gameToken");
		
		// GameToken and Board
		JSONArray board = (JSONArray) startGameResponse.get("board");
		ArrayList<ArrayList<String>> stringBoard = JSONArray_to_ArrayList(board);
		
		// Genetic algorithm parameters
		int populationSize = 48; 
		int maxIter = 10000;

		Solver solver = new Solver();
		int counter = 0;
		while(true) {
			Population population = new Population(populationSize, maxIter, stringBoard, solver);
			Chromosome bestMove = population.run();

			// Send POST game/swap for best swap
			JSONObject gameSwapParameters = getGameSwapJSON(gameToken, bestMove.getX(), bestMove.getY(), bestMove.getMoveX(), bestMove.getMoveY());
			JSONObject gameSwapResponse = webAPI.sendPost("/game/swap", gameSwapParameters);
			if (gameSwapResponse.get("gameOver").toString().equals("true")){
				System.out.println("Game Over with Turn: " + counter +" Score = " + gameSwapResponse.get("score") +" TotalScore = "+ gameSwapResponse.get("totalScore"));
				break;
			}
			JSONArray newBoard = (JSONArray) gameSwapResponse.get("board");
			stringBoard = JSONArray_to_ArrayList(newBoard);
			counter++;
			System.out.println("Turn: " + counter +" Score = " + gameSwapResponse.get("score") +" TotalScore = "+ gameSwapResponse.get("totalScore"));
		}
	}

	private static JSONObject getGameSwapJSON(String gameToken, int row1, int col1, int row2, int col2) {
		JSONObject gameSwapJSON = new JSONObject();
		try {
			gameSwapJSON.put("token", gameToken);
			gameSwapJSON.put("row1", row1);
			gameSwapJSON.put("col1", col1);
			gameSwapJSON.put("row2", row2);
			gameSwapJSON.put("col2", col2);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return gameSwapJSON;
	}

	private static JSONObject getStartGameJSON(String token) {
		JSONObject startGameJSON = new JSONObject();
		try {
			startGameJSON.put("token", token);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return startGameJSON;
	}

	private static JSONObject getRegisterJSON() {
		JSONObject registerJSON = new JSONObject();
		try {
			registerJSON.put("name", "xxx");
			registerJSON.put("email", "xxx@xxx.com");
			registerJSON.put("university", "xxx");
			registerJSON.put("module", "xxx");
			registerJSON.put("semester", "x");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return registerJSON;
	}

	private static ArrayList<ArrayList<String>> JSONArray_to_ArrayList(JSONArray board) throws JSONException {
		ArrayList<ArrayList<String>> stringBoard = new ArrayList<>();
		for(int i=0; i<board.length(); i++) {
			JSONArray temp = (JSONArray) board.get(i);
			ArrayList<String> tempString = new ArrayList<>();
			for(int j=0; j<temp.length(); j++) {
				tempString.add((String) temp.get(j));
			}
			stringBoard.add(tempString);
		}
		return stringBoard;
	}
}