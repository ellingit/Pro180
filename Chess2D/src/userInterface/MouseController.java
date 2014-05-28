package userInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

public class MouseController extends Observable implements MouseListener, MouseMotionListener {
	public MouseController(){
		
	}
	@Override
	public void mouseClicked(MouseEvent e){
		setChanged();
		notifyObservers(((BoardSquare)e.getComponent()).getBoardLocation());
	}
	@Override
	public void mouseEntered(MouseEvent e){
		setChanged();
		notifyObservers(e.getComponent());		
	}
	@Override
	public void mouseExited(MouseEvent e){
		setChanged();
		notifyObservers(null);
	}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
