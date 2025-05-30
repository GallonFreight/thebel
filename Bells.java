
public enum Bells {
	
	// An enumeration of 12 bells, giving each a symbol and waveform
	
	one('1',392.00),
	two('2',349.23),
	three('3',329.63),
	four('4',293.66),
	five('5',261.63),
	six('6',246.94),
	seven('7',220.00),
	eight('8',196.00),
	nine('9',174.61),
	ten('0',164.81),
	eleven('E',146.83),
	twelve('T',130.81);
	
	private final char number;
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final double SECONDS = 5;
    private byte[] wave = new byte[(int) (SECONDS * SAMPLE_RATE)];
    
	
    
    //Risset version is in preTesting package
	Bells(char number, double fundamental){
		this.number = number;

        for (int i = 0; i < wave.length; i++) {
            double period = (double)SAMPLE_RATE / fundamental;
            double angle = 2.0 * Math.PI * i / period;
            // add in exponential decay so it doesn't have such a big change when next note cuts in
            // hopefully this will avoid clicking
            wave[i] = (byte)(Math.sin(angle) * Math.exp(-angle/220.0) * 127f); // * 127 to get from 0-1 to full range of byte
        }
		

	}
	
	public char getNumber(){
		return number;
	}
	
	public byte[] getWave(){
		return wave;
	}
}
