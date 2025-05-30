import javax.sound.sampled.LineUnavailableException;
//This version of the beta was created to allow ringing just one bell on major

public class MainClass {
	
	private static int[][] imogen = {
		{},
		{3, 8},
		{},
		{1, 4},
		{},
		{1, 2, 5, 8},
		{},
		{1, 2, 3, 6},
		{},
		{1, 4},
		{},
		{1, 8},
		{5, 6},
		{1, 2},
		{5, 6},
		{7, 8},
		{5, 6},
		{1, 2},
		{5, 6},
		{1, 8},
		{},
		{1, 4},
		{},
		{1, 2, 3, 6},
		{},
		{1, 2, 5, 8},
		{},
		{1, 4},
		{},
		{3, 8},
		{}
	};

	private static int[][] plain = {
		{1, 2}
	};
	
	private static int[][] bob = {
		{1, 4}
	};
	
	private static int[][] single = {
		{1, 2, 3, 4}
	};
	
	private static int[][][] composition =
		{imogen, plain, imogen, plain, imogen, bob, imogen, plain, imogen, plain, imogen, bob, imogen, single, imogen, bob,
			imogen, plain, imogen, plain, imogen, plain, imogen, plain, imogen, plain, imogen, plain, imogen, plain, imogen, plain, imogen, plain,
			imogen, single, imogen, plain, imogen, plain, imogen, single};
	// remember to end this with a plain to play the last change
	
	public static void main(String[] args) throws LineUnavailableException {
		Belfry belfry = new Belfry(10, 180, 22, Bells.two);
		
		int startAfterLead = 0; // so the first however many quarks are not rung
		// just keeping this in because I can't be bothered to take it out of the main loop
		
		Bells[] change = Bells.values();
		
		for(int i = 0; i < composition.length; i++){
		// iterates through each lead end			
						
			if(i == startAfterLead){
				for(int j = 0; j < 3; j++) // to ring HBH(B) before the method begins
					belfry.loadChange(change);
				System.out.println("Go!");
			}
			for(int j = 0; j < composition[i].length; j++){
			// iterates through every change in the lead
				
				
				if(composition[i].length - j == 1){
					
					// automates conducting
					try{
						int[][] next = composition[i+1];
						if(next == plain){
							System.out.println("Lead End!");
						}else if(next == bob){
							System.out.println("Bob!");
						}else if(next == single){
							System.out.println("Single!");
						}
					}catch (ArrayIndexOutOfBoundsException e){
						System.out.println("That's All!");					
					}
				}
				if(i >= startAfterLead) belfry.loadChange(change); // plays the change
				change = permute(change, composition[i][j]); // creates the next change from place notation
			}
			
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
