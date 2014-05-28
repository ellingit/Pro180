package userInterface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

public class MouseController extends Observable implements MouseListener, MouseMotionListener {
	public MouseController(){
		
	}
	@Override
	public void mouseClicked(MouseEvent e){
		e.getComponent().setBackground(Color.RED);
	}
	@Override
	public void mouseEntered(MouseEvent e){
//		e.getComponent().setBackground(Color.LIGHT_GRAY);
		setChanged();
		notifyObservers(e.getComponent());		
	}
	@Override
	public void mouseExited(MouseEvent e){
		e.getComponent().setBackground(((BoardSquare)e.getComponent()).color);
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
