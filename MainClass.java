import javax.sound.sampled.LineUnavailableException;


public class MainClass {
	
	// an attempt at enumerating the quarks can be found in preTesting
	private static int[][] up = { 
		{3,12},
		{1,2},
		{},
		{1,12},
		{},
		{1,4},
		{5,12},
		{},
		{5,12},
		{3,4},
		{5,12},
		{},
		{5,12},
		{1,4},
		{},
		{1,12},
		{},
		{1,2},
		{3,12},
		{}		
	};
	
	private static int[][] down = {
		{1, 10},
		{11, 12},
		{},
		{1, 12},
		{},
		{9, 12},
		{1, 8},
		{},
		{1, 8},
		{9, 10},
		{1, 8},
		{},
		{1, 8},
		{9, 12},
		{},
		{1, 12},
		{},
		{11, 12},
		{1, 10},
		{}
	};
	
	private static int[][] charm = {
		{7, 12},
		{},
		{7, 12},
		{},
		{1, 6, 7, 12},
		{},
		{1, 6, 7, 8},
		{},
		{1, 6, 7, 12},
		{},
		{7, 12},
		{},
		{7, 12},
		{}
	};

	private static int[][] strange = {
		{5, 8},
		{},
		{5, 8},
		{},
		{5, 8},
		{},
		{1, 6, 7, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 12},
		{},
		{1, 6, 7, 12},
		{},
		{5, 8},
		{},
		{5, 8},
		{},
		{5, 8},
		{}
	};

	private static int[][] top = {
		{5, 12},
		{1, 4},
		{},
		{5, 12},
		{},
		{1, 2},
		{},
		{1, 4, 5, 12},
		{},
		{1, 4},
		{5, 12},
		{1, 4},
		{5, 12},
		{1, 4},
		{},
		{1, 4, 5, 12},
		{},
		{1, 2},
		{},
		{5, 12},
		{},
		{1, 4},
		{5, 12},
		{}
	};

	private static int[][][] composition =
		{up, charm, top, charm, strange, down, top, strange};
	
	public static void main(String[] args) throws LineUnavailableException {
		Belfry belfry = new Belfry(10, 205, 20, Bells.five, Bells.six);
		// 1st arg is tickrate
		
		int startAfterLead = 0; // so the first however many quarks are not rung
		
		Bells[] change = Bells.values();
				
		
//		for(int[][] quark:composition){
		for(int i = 0; i < composition.length; i++){
			if(i == startAfterLead){
				for(int j = 0; j < 3; j++) // to ring HBH(B) before the method begins
					belfry.loadChange(change);
				System.out.println("Go!");
			}
			for(int[] row:composition[i]){
				if(i >= startAfterLead) belfry.loadChange(change); // plays the change
				change = permute(change, row); // creates the next change from place notation
			}
			System.out.println(); // separates methods by a blank line
		}

		belfry.loadChange(change);// to make sure it rings round at the end
		
		belfry.die();
	}
	
	
	public static Bells[] permute(Bells[] initial, int[] places){
		// interprets a permutation from place notation('places') and applies it
		// by swapping pairs of bells 1/2, 3/4, 5/6 ... until it finds that the current entry in places says not to
		
		int currentPlace = 0; 
		Bells[] result = initial;
		for(int i = 0; i < result.length-1; i++){ 	
			
			if(currentPlace < places.length && i == places[currentPlace]-1){// ensures that currentPlace corresponds to an actual value in the place notation
																			// checks that current swapping index isn't in the place notation
																			// have to add '-1' so that place number corresponds to index
				currentPlace++;
			} else {
				result = swap(result, i);
				i++;				// it has already swapped the bell in i+1 place
									// doesn't cause problems with the bell staying still because we know that current bell can't swap with bell below it
									// so must swap with bell above
			}			
		}
		return result;
		
		
	}
	

	// TODO(UNIMPORTANT) see if you can implement this more efficiently
	public static Bells[] swap(Bells[] initial, int index){
		// returns an array with the items at index and index+1 swapped over
		Bells [] swapped = new Bells[initial.length];
		// done by iteration here because otherwise swapped is just treated like a pointer to initial
		// then bad things happen
		for(int i = 0; i < swapped.length; i++)
			swapped[i] = initial[i];
			
		swapped[index] = initial[index+1];
		swapped[index+1] = initial[index];
		
		
		return swapped;
	}

}
