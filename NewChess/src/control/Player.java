package control;

import java.util.Scanner;

import exceptions.InvalidMoveException;
import model.Board;
import model.Move;
import model.PlayerColor;
import model.Square;

public class Player {
	public final PlayerColor COLOR;
	private Board context;
	private Move move;
	private Scanner scanLee = new Scanner(System.in);
	
	public Player(PlayerColor color, Board context){
		COLOR = color;
		this.context = context;
	}
	
	public Move getMove(){ return move; }
	
	public void takeTurn(){
		//choose piece (origin)
		System.out.print("Choose a piece by location: ");
		Square piece = new Square(scanLee.nextLine());
		//choose destination
		System.out.print("Choose a destination: ");
		Square location = new Square(scanLee.nextLine());
		//piece@origin.move(destination)
		try {
			context.getSquare(piece.X, piece.Y).getOccupant().move(context.getSquare(location.X, location.Y));
		} catch (InvalidMoveException e) {
			System.err.println(e.getMessage());
		}
	}
}
