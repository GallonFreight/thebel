import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class TestRun {
	
	public static final byte[] rest = new byte[(int) (Bells.SECONDS * Bells.SAMPLE_RATE)];

    public static void main(String[] args) throws LineUnavailableException {
        final AudioFormat af =
            new AudioFormat(Bells.SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(af);
        line.open(af, Bells.SAMPLE_RATE);
        line.start();
        for  (Bells bell:Bells.values()) {
            play(line, bell, 500);
            int count = line.write(rest, 0, 20);
        }
        line.drain();
        line.close();
    }

    private static void play(SourceDataLine line, Bells note, int ms) {
    	
        ms = Math.min(ms, (int)Bells.SECONDS * 1000);
        int length = Bells.SAMPLE_RATE * ms / 1000;
        int count = line.write(note.getWave(), 0, length); // will only accept bytes(signed)
        System.out.printf("Playing bell: %s\n", note.toString());
    }
}

