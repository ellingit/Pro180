package userInterface;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pieces.Piece;
import control.GameControl;
import board.GameBoard;
import board.Location;

public class GUI extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	
	private GridLayout boardLayout = new GridLayout(8, 8);
	private JPanel board;
	private BoardSquare[][] allSquares;
	private GameControl controller;
	private GameBoard context;
	
	public GUI(){
		controller = new GameControl("..\\setup.txt");
		context = controller.getContext();
		controller.setMoves(context);
		init();		
	}
	private void init(){
		this.setSize(700, 700);
		this.setResizable(false);
		board = new JPanel(boardLayout);
		allSquares = new BoardSquare[8][8];
		for(int y = allSquares.length-1; y >= 0; y--){
			for(int x = 0; x < allSquares[y].length; x++){
				BoardSquare bs = null;
				if(y % 2 == 0){
					if(x % 2 == 0) bs = new BoardSquare(Color.WHITE, context, new Location(x,y));
					else bs = new BoardSquare(Color.DARK_GRAY, context, new Location(x,y));
				}
				else {
					if(x % 2 != 0) bs = new BoardSquare(Color.WHITE, context, new Location(x,y));
					else bs = new BoardSquare(Color.DARK_GRAY, context, new Location(x,y));
				}
				bs.setSize(70, 70);
				bs.setBackground(bs.color);
				bs.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				bs.getMouseController().addObserver(this);
				allSquares[y][x] = bs;
				board.add(bs);
			}
		}
		this.add(board);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void highlightSquare(Location location){
		if(allSquares[location.Y][location.X].color == Color.DARK_GRAY){
			allSquares[location.Y][location.X].setBackground(Color.getHSBColor(0.6F,0.5F,0.95F));
		} else allSquares[location.Y][location.X].setBackground(Color.getHSBColor(0.6F,0.5F,0.65F));
		repaint();
	}
	private void clearHighlights(){
		for(BoardSquare[] row : allSquares){
			for(BoardSquare square : row){
				square.setBackground(square.color);
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && context.getPieceAt(((BoardSquare)arg).getBoardLocation()) != null){
			Piece focus = context.getPieceAt(((BoardSquare)arg).getBoardLocation());
			for(Location possibility : focus.getAvailableMoves()){
				highlightSquare(possibility);
			}
		} else clearHighlights();
	}
}
