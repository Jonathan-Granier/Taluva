package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

public class TimerOpenGL extends Observable implements ActionListener {

	Timer timer;
	
	public TimerOpenGL(){
		timer = new Timer(1,this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers();
	}

	
	
}
