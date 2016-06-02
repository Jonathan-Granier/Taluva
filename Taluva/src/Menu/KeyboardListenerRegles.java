package Menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListenerRegles implements KeyListener {
	private Regle_panel panel;
	public KeyboardListenerRegles(Regle_panel panel){
		this.panel = panel;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT :
			panel.precedent();
			break;
		case KeyEvent.VK_RIGHT :
			panel.suivant();
			break;
		case KeyEvent.VK_ESCAPE :
			panel.retour();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
