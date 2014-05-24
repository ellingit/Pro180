package userInterface;

import javax.swing.JPanel;

public class BoardSquare extends JPanel {
	MouseController mc = new MouseController();
	
	public BoardSquare(){
		this.addMouseListener(mc);
		this.addMouseMotionListener(mc);
	}
}