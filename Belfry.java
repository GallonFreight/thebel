import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;



public class Belfry implements ActionListener {
	// plays bell sounds of other people's bells at the appropriate times
	// plays user bell sounds when appropriate keys are pressed
	// must be able to cut over the sound of one bell with another if needed
	
	// keeps sending the same sound to the buffer until it is told to 'cuebite', either with another bell or a pause
	// for benefit of user, pauses must be able to be cuebitten in the same way
	// must be able to have pauses of 2 different lengths
	
	Timer timer;
	public final int tickLength;// of the timer
	private int userTicksLeft; // how many ticks left before the current bell, or pause must stop
	private int autoTicksLeft; // how many ticks left before the user's bell stops playing of its own accord
	private final int bellTicks;
	private final int restTicks;
	private boolean waiting;
	private boolean autoNoisePlaying; // says whether one of the non-user bells' waveforms is being played
	
	private SourceDataLine line;
	
	public JFrame frame;
	
	public final Bells myBell1, myBell2;
	
	private boolean handstroke;
	
	public Belfry(int tickRate, int bellLength, int restLength, Bells myBell1, Bells myBell2) throws LineUnavailableException{
		this.myBell1 = myBell1;
		this.myBell2 = myBell2;
		handstroke = true;
		
		final AudioFormat af =
				new AudioFormat(Bells.SAMPLE_RATE, 8, 1, true, true);
		line = AudioSystem.getSourceDataLine(af);
		line.open(af, Bells.SAMPLE_RATE);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(275, 180);
		frame.setVisible(true);
		frame.addKeyListener(new Adapter());
		
		tickLength = tickRate;
		bellTicks = (int)(bellLength/tickLength);
		restTicks = (int)(restLength/tickLength);
		waiting = false;
		autoNoisePlaying = false;
		
		timer = new Timer(this.tickLength, this);
		timer.start();
	}
	
	public void die(){
		line.drain();
        line.close();
        
        timer.stop();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		autoTicksLeft --;
		userTicksLeft --;
		
		if (userTicksLeft == 0) stopPlayback();
		
		if (autoTicksLeft == 0){
			// when autoTicksLeft counts down to 0, stop playing any currently playing non-user bell
			// and progress to the next part of the change
			if(autoNoisePlaying){
				stopPlayback();
				autoNoisePlaying = false; 
			}
			continueChange();
		}
		// when userticksleft = 0, stop playback (will only happen if a bell has not cuebitten it)
		// when autoticksleft = 0, stop playback, set userTicksLeft to -1 to stop it cutting in and continueChange()
	}
	
	private synchronized void continueChange(){//tells loadChange stop waiting
		waiting = false;
		notifyAll();
	}
	
	public synchronized void loadChange(Bells[] change){
		
		if(handstroke){
			autoTicksLeft =  bellTicks;
			waiting = true;
			
			while(waiting){// allows rest to finish playing
				try{
					wait(); // good practice to have this inside the loop; might get notified because of something else
				}catch (InterruptedException e) {}				
			}			
		}
				
		for(Bells bell: change){
			

			autoTicksLeft = restTicks;
			waiting = true;
			
			while(waiting){// allows rest to finish playing
				try{
					wait(); // good practice to have this inside the loop; might get notified because of something else
				}catch (InterruptedException e) {}				
			}
			
			if((bell != myBell1) && (bell != myBell2)){
				playBell(bell);
				userTicksLeft = -1; // in case a user bell has been interrupted
									// so that its countdown cycle is rushed to finish and does not stop playback of non-user bell
				autoNoisePlaying = true;
			}			
			
			autoTicksLeft = bellTicks; // leaves a bell-sized gap whether one is playing or not
			waiting = true;
			System.out.printf("%c", bell.getNumber()); // prints the current bell symbol
			
			while(waiting){// allows bell time to finish playing
				try{
					wait(); // good practice to have this inside the loop; might get notified because of something else
				}catch (InterruptedException e) {}				
			}
			
		}
		
		handstroke = !handstroke;
		
		System.out.println();
				
	}
	
	// AUDIO METHODS
	
	public void stopPlayback(){
		line.stop();
		line.flush();
	}
	
	public void playBell(Bells bell){
		stopPlayback();// means that bells can interrupt one another
		line.start();
		
		int ms = Math.min(bellTicks*tickLength, (int)Bells.SECONDS * 1000);
        int length = Bells.SAMPLE_RATE * ms / 1000;
        line.write(bell.getWave(), 0, length); // will only accept bytes(signed)
		// start the dataLine, and play the appropriate waveform
	}
	
	
	// INPUT HANDLING
	
	public void press(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_L){
			playBell(myBell1);
			userTicksLeft = bellTicks;
			autoNoisePlaying = false;
		}else if(key == KeyEvent.VK_S){
			playBell(myBell2);
			userTicksLeft = bellTicks;
			autoNoisePlaying = false;
		}
		
		// when corresponding key is pressed, play of given bell is initiated
		// userTicksLeft is set to bellTicks
	}

    private class Adapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            press(e);
        }

    }

}
