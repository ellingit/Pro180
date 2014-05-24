package userInterface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
	public MouseController(){
		
	}
	@Override
	public void mouseClicked(MouseEvent e){
		e.getComponent().setBackground(Color.RED);
	}
	@Override
	public void mouseEntered(MouseEvent e){
		e.getComponent().setBackground(Color.LIGHT_GRAY);
	}
	@Override
	public void mouseExited(MouseEvent e){
		e.getComponent().setBackground(Color.WHITE);
	}
}
