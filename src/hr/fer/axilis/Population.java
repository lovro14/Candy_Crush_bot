package hr.fer.axilis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Population {
	
	private final int TOURNOMENT_SIZE = 3;
	private final double CROSSOVER_RATIO = 0.5;
	private Random rand = new Random();
	private ArrayList<ArrayList<String>> board;
	private int maxIter;
	private int populationSize;
	private Solver solver;
	
	public Population(int populationSize, int maxIter,ArrayList<ArrayList<String>> board, Solver solver){
		this.populationSize = populationSize;
		this.maxIter = maxIter;
		this.board = board;
		this.solver = solver;
	}

	public ArrayList<ArrayList<String>> getBoard() {
		return board;
	}

	private ArrayList<Chromosome> createPopulation() {
		ArrayList<Chromosome> population = new ArrayList<>();
		int currentPopulationSize = 0;
		List<String> choosenIndices = new ArrayList<>();
		while(currentPopulationSize < populationSize){
			int x = rand.nextInt(9);
			int y = rand.nextInt(9);
			String indexes = String.valueOf(x) + " " + String.valueOf(y);
			if(!choosenIndices.contains(indexes)){
				currentPopulationSize++;
				choosenIndices.add(indexes);
				population.add(new Chromosome(x, y, board.get(x).get(y), board));
			}
		}
		return population;
	}
	
	public void  evaluatePopulation(ArrayList<Chromosome> population){
		for (int i=0; i<population.size(); i++) {
			population.get(i).evaluate(solver);
		}
	}

	public Chromosome run(){
		ArrayList<Chromosome> population = new ArrayList<>(); 
		population = createPopulation();
		evaluatePopulation(population);
		
		ArrayList<Chromosome> newPopulation; 
		int counter = 0;

		while(true){
			Chromosome bestCh = findBestChromosome(population);
			newPopulation = new ArrayList<>();
			
			while(newPopulation.size()<populationSize-1){
				List<Chromosome> tournamentWinners = tournamentElimination(population);
				Chromosome crossChromosome = crossChromosomes(tournamentWinners);
				crossChromosome.mutation();
				crossChromosome.evaluate(solver);
				newPopulation.add(crossChromosome);
			}

			// elitism and keep best chromosome in next generation
			newPopulation.add(bestCh); 

			population = new ArrayList<>(); 
			population.addAll(newPopulation); 
			counter++;
			
			if(counter >= maxIter){
				return findBestChromosome(population);
			}
		}		

	}
	

	private Chromosome findBestChromosome(ArrayList<Chromosome> population) {
		double max = -1.0; 
		int maxIndex = 0; 
		for(int i=0; i<populationSize; i++) {
			if(population.get(i).getFitnessValue() > max ) {
				max = population.get(i).getFitnessValue();
				maxIndex = i; 
			}
		}
		Chromosome ch = new Chromosome(population.get(maxIndex)); 	
		return ch;
	}


	private Chromosome crossChromosomes(List<Chromosome> tournomentWinners) {
		ArrayList<Chromosome> temp = new ArrayList<Chromosome>(); 
		temp.addAll(tournomentWinners); 
		Chromosome c1 = temp.get(0);
		Chromosome c2 = temp.get(1);
		if(rand.nextDouble()<CROSSOVER_RATIO){
			if(rand.nextDouble()<0.5) {
				c1.setY(c2.getY());
				return c1; 
			}
			else {
				c2.setX(c1.getX());
				return c2;
			}
		}	
		else {
			int randId = rand.nextInt(2);
			return tournomentWinners.get(randId); 
		}
	}


	private  List<Chromosome> tournamentElimination(ArrayList<Chromosome> population) {
		List<Chromosome> randomChromosoms = new ArrayList<>();
		Set<Integer> indexSet = null;
		while(true){
			indexSet = new HashSet<>();
			for(int i = 0; i < TOURNOMENT_SIZE; i++){
				int n = rand.nextInt(populationSize-1);
				indexSet.add(n);
			}
			if(indexSet.size()==TOURNOMENT_SIZE){
				break;
			}
		}
		for(Integer n : indexSet){
			randomChromosoms.add(population.get(n));
		}
		int minIndex = 0;
		double min = Double.MAX_VALUE;
		
		for(int i = 0; i < randomChromosoms.size(); i++){
			if(randomChromosoms.get(i).getFitnessValue()<min){
				min = randomChromosoms.get(i).getFitnessValue();
				minIndex = i;
			}
		}
		randomChromosoms.remove(minIndex);
		ArrayList<Chromosome> lis = new ArrayList<>();
		lis.addAll(randomChromosoms);
		return lis;
	}
}