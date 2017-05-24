package hr.fer.axilis;

import java.util.ArrayList;
import java.util.Collections;

public class Proba {

	public static void main(String[] args) {
		ArrayList<NextMove> list = new ArrayList<>();
		list.add(new NextMove(0, 0, 0.2));
		list.add(new NextMove(0, 0, 0.4));
		list.add(new NextMove(0, 0, 0.3));
		Collections.sort(list, 
                (o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
		for(NextMove nm : list){
			System.out.println(nm.getScore());
		}

	}

}
