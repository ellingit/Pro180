package userInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
			context.getPieceAt(boardLocation).getIcon().paintIcon(this, g, this.getHeight()/4, this.getWidth()/4);
		}
	}
}