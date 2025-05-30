import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class MainClass {
	
	public static final byte[] rest = new byte[(int) (Bells.SECONDS * Bells.SAMPLE_RATE)];
	
	private static final int restLength = 30; // ms
	
	private static final int bellLength = 300;  // ms

	private static Bells myBell1 = Bells.eleven, myBell2 = Bells.twelve;
	
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
	
	public static void main(String[] args) throws LineUnavailableException {
		
		Listener listener = new Listener(10);
		
		// setting up audio output
		final AudioFormat af =
			new AudioFormat(Bells.SAMPLE_RATE, 8, 1, true, true);
	    SourceDataLine line = AudioSystem.getSourceDataLine(af);
	    line.open(af, Bells.SAMPLE_RATE);
	        
		
		Bells[] change = Bells.values();		
		
		int[] places;
		
		for(int[] row:up){			
			for(Bells bell:change){
				
				

//				if (bell != myBell1 && bell != myBell2){
//					play(line, bell, bellLength); // making the bell noise
//				} else line.write(rest, 0, bellLength);
//        		line.write(rest, 0, restLength);
				        		            
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Charm!");
		
		for(int[] row:charm){			
			for(Bells bell:change){

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
            
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Top!");
		
		for(int[] row:top){			
			for(Bells bell:change){

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Charm!");
		
		for(int[] row:charm){			
			for(Bells bell:change){

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Strange!");
		
		for(int[] row:strange){			
			for(Bells bell:change){

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Down!");
		
		for(int[] row:down){			
			for(Bells bell:change){
				

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Top!");
		
		for(int[] row:top){			
			for(Bells bell:change){
				

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		System.out.println("Strange!");
		
		for(int[] row:strange){			
			for(Bells bell:change){

				play(line, bell, bellLength); // making the bell noise
        		int count = line.write(rest, 0, restLength);
        		
				System.out.printf("%c", bell.getNumber()); // prints the current change
			}	
				
			System.out.println(); // make sure to do this so as to get changes on separate lines
			
			places = row;// loads the place notation of the permutation to occur
		
			change = permute(change, places);			
		}
		
		for(Bells bell:change){
			

			play(line, bell, bellLength); // making the bell noise
    		int count = line.write(rest, 0, restLength);
    		
			System.out.printf("%c", bell.getNumber()); // prints the current change
		}	
			

		line.drain();
        line.close();
				
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
	
	public static void rest(int ms){
		
	}
	
	
	public static void play(SourceDataLine line, Bells note, int ms) {
    	
        ms = Math.min(ms, (int)Bells.SECONDS * 1000);
        int length = Bells.SAMPLE_RATE * ms / 1000;
        int count = line.write(note.getWave(), 0, length); // will only accept bytes(signed)
    }
}
