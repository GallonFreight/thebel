
public enum Bells {
	
	// An enumeration of 12 bells, giving each a symbol and waveform
	
	// to make this sound better, I changed the pitches
	
	three('1',329.63),
	four('2',293.66),
	five('3',261.63),
	six('4',246.94),
	seven('5',220.00),
	eight('6',196.00);
	// to make it do major rather than maximus, I just commented out the rest. Hopefully this works!
//	nine('9',174.61),
//	ten('0',164.81),
//	eleven('E',146.83),
//	twelve('T',130.81);
	
	private final char number;
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final double SECONDS = 5;
    private byte[] wave = new byte[(int) (SECONDS * SAMPLE_RATE)];
    
	
    
    //Risset version is in preTesting package
	Bells(char number, double fundamental){
		// fundamental refers to fundamental note of each bell, not base of scale
		this.number = number;

        for (int i = 0; i < wave.length; i++) {
            double period = (double)SAMPLE_RATE / fundamental;
            double angle = 2.0 * Math.PI * i / period;
            wave[i] = (byte)(Math.sin(angle) * 127f); // * 127 to get from 0-1 to full range of byte
        }
		

	}
	
	public char getNumber(){
		return number;
	}
	
	public byte[] getWave(){
		return wave;
	}
}
