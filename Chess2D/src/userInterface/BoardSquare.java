package userInterface;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import board.GameBoard;
import board.Location;

public class BoardSquare extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public final Color color;
	private MouseController mc = new MouseController();
	private GameBoard context;
	private Location boardLocation;
	
	public BoardSquare(Color color, GameBoard context, Location boardLocation){
		this.addMouseListener(mc);
		this.addMouseMotionListener(mc);
		this.color = color;
		this.context = context;
		this.boardLocation = boardLocation;
	}
	
	public Location getBoardLocation(){ return boardLocation; }
	public MouseController getMouseController(){ return mc; }
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(context.getPieceAt(boardLocation) != null){
			ImageIcon icon = context.getPieceAt(boardLocation).getIcon();
			icon.paintIcon(this, g, (this.getHeight() - icon.getIconHeight())/2, (this.getWidth() - icon.getIconWidth())/2);
		}
	}
}