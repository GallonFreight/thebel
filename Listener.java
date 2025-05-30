import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
// I pretty much stopped working on this at this point
import javax.swing.Timer;

public class Listener implements ActionListener{
	
	public Listener(int clockrate){
		Timer timer = new Timer(clockrate, this);
	}
	
	public void actionPerformed(ActionEvent e) {
		 
	}

}
