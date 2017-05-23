package hr.fer.axilis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Population {
	
	private List<Chromosome> population;
	private final int TOURNOMENT_SIZE = 3;
	private final double CROSSOVER_RATIO = 0.3;
	//private long duration
	private Random rand = new Random();
	private ArrayList<ArrayList<BoardElement>> board;
	private int maxIter;
	private int populationSize;
	
	public Population(int populationSize, int maxIter,ArrayList<ArrayList<BoardElement>> board){
		this.populationSize = populationSize;
		this.maxIter = maxIter;
		this.board = board;
		createPopulation();
		evaluatePopulation();
	}
	
	
	public List<Chromosome> getPopulation() {
		return population;
	}

	public void setPopulation(List<Chromosome> population) {
		this.population = population;
	}

	public ArrayList<ArrayList<BoardElement>> getBoard() {
		return board;
	}

	private void createPopulation() {
		population = new ArrayList<>();
		int currentPopulationSize = 0;
		List<String> choosenIndexes = new ArrayList<>();
		while(currentPopulationSize < populationSize){
			int x = rand.nextInt(6);
			int y = rand.nextInt(6);
			String indexes = String.valueOf(x)+" "+String.valueOf(y);
			if(!choosenIndexes.contains(indexes)){
				currentPopulationSize++;
				choosenIndexes.add(indexes);
				population.add(new Chromosome(x,y,board.get(x).get(y),board));
			}
		}
		
	}
	
	public void  evaluatePopulation(){
		for (Chromosome chromosome : population){
			chromosome.evaluate();
		}
	}
	
	public Chromosome findBestChromosome(){
		double max = 0.0;
		Chromosome maxChromosome= null;
		for(Chromosome chromosome : population){
			if (chromosome.getFitnessValue()>max){
				max = chromosome.getFitnessValue();
				maxChromosome = chromosome;
			}
		}
		return maxChromosome;
	}
	
	public Chromosome run(){
		
		ArrayList<Chromosome> newPopulation = new ArrayList<>();
		int counter = 0;
		while(true){
			newPopulation.add(findBestChromosome());
			while (newPopulation.size()<population.size()){
				List<Chromosome> tournomentWinners = tournomentElimination();
				Chromosome childChromosom = crossChromosomes(tournomentWinners);
				childChromosom.mutation();
				childChromosom.evaluate();
				newPopulation.add(childChromosom);
			}		
			
			// svi su evaluirani
			this.setPopulation(newPopulation);
			System.out.println("Iteracija: " + counter + " Najbolja jedinka u populaciji: " + findBestChromosome()); 
			newPopulation.clear();
			counter++;
		
			if (counter >= maxIter) {
				Chromosome best = findBestChromosome(); 
				return best;  
			}
		}
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
			// vrati jednog od winera
			int randId = rand.nextInt(2);
			return tournomentWinners.get(randId); 
		}
	}


	private  List<Chromosome> tournomentElimination() {
		List<Chromosome> randomChromosoms = new ArrayList<>();
		Set<Integer> indexSet = null;
		while(true){
			indexSet = new HashSet<>();
			for(int i = 0; i < TOURNOMENT_SIZE; i++){
				int n = 0 + (int)(Math.random() * ((population.size()-1 - 0) + 1));
				indexSet.add(n);
			}
			if(indexSet.size()==TOURNOMENT_SIZE){
				break;
			}
		}
		for(Integer n : indexSet){
			randomChromosoms.add(population.get(n));
		}
		
		int minindex = 0;
		double min = Double.MAX_VALUE;
		
		for(int i = 0; i < randomChromosoms.size(); i++){
			if(randomChromosoms.get(i).getFitnessValue()<min){
				min = randomChromosoms.get(i).getFitnessValue();
				minindex = i;
			}
		}
		randomChromosoms.remove(minindex);
		return randomChromosoms;
	}

	
}
