
public enum Bells {

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
	private final double[][] components = {
		// intensity/dB, duration fraction, frequency multiplier
		{0, 1, 0.56},
		{-3.48, 0.9, 0.56},
		{0, 0.65, 0.92},
		{5.11, 0.55, 0.92},
		{8.53, 0.325, 1.19},
		{4.45, 0.35, 1.7},
		{3.29, 0.25, 2},
		{2.48, 0.2, 2.74},
		{2.48, 0.15, 3},
		{0, 0.1, 3.76},
		{2.48, 0.075, 4.07}
	};

	Bells(char number, double fundamental){
		this.number = number;
		double[] protowave = new double[wave.length];// analogue version of signal

		for(double[] component:components){
			double frequency = component[2] * fundamental;
			double period = (double)SAMPLE_RATE / frequency; // # of data in 1 period
			for(int i = 0; i < wave.length; i++){
				double angle = 2.0 * Math.PI * i / period;
				protowave[i] += Math.sin(angle) * Math.pow(10, (component[0]/2) - ((60 + component[0]/2) * i/* # data so far */)/(component[1]* wave.length /* total # data before decay */)/* brings amplitude down to -60dB in its duration time */ );
				// do I need to initialise all protowave values to 0 to start with or is it automatic?
			}
		}

		double max = 0;
		for(double displacement: protowave)
			max = Math.max(max, displacement); // finds maximum displacement

		for(double displacement: protowave)
			displacement = displacement/max; // normalises to [-1, 1]

		for(int i = 0; i < wave.length; i++)
			wave[i] = (byte) (protowave[i] * 127f); // digitises wave
	}

	public char getNumber(){
		return number;
	}

	public byte[] getWave(){
		return wave;
	}
}
