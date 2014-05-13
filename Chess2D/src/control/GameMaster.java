package control;

import java.util.HashMap;

import pieces.*;
import board.GameBoard;
import exceptions.InvalidMoveException;

public class GameMaster {
	private boolean whiteTurn = true;
	private GameBoard board = new GameBoard();
	
	public static final HashMap<Character, String> COLOR_KEY = new HashMap<>();
	public static final HashMap<Character, String> PIECE_KEY = new HashMap<>();
	
	//Constructor
	public GameMaster(){
		COLOR_KEY.put('L', "light");
		COLOR_KEY.put('D', "dark");
		PIECE_KEY.put('K', "King");
		PIECE_KEY.put('Q', "Queen");
		PIECE_KEY.put('B', "Bishop");
		PIECE_KEY.put('N', "Knight");
		PIECE_KEY.put('R', "Rook");
		PIECE_KEY.put('P', "Pawn");
	}
	
	//Public Methods
	public void move(String origin, String destination) throws InvalidMoveException {
		if(validMove(origin, destination)) board.movePieceTo(destination, board.getPieceAt(origin));
		else throw new InvalidMoveException("That move is not allowed.");
	}
	
	//Private Methods
	private boolean validMove(String origin, String destination){
		if(isEmpty(origin)) return false;
		if(board.getPieceAt(origin).isWhite == whiteTurn){
			if(isAllowed(origin, destination)){
				if(canJump(origin)) return true;
				else if(isClear(origin, destination, board.getPieceAt(origin).isWhite)) return true;
			}
		}
		return false;
	}
	private boolean isEmpty(String location){
		if(board.getPieceAt(location) == null) return true;
		else return false;
	}
	private boolean isAllowed(String origin, String destination){
		Piece currentPiece = board.getPieceAt(origin);
		int[] coords = calculateCoordinates(origin, destination);
		if(currentPiece.getClass().getSimpleName().equals("Pawn") && !isEmpty(destination)){
			if(!((Pawn)currentPiece).validMove((coords[2] - coords[0]), (coords[3] - coords[1]), true)) return false;
			else return true;
		}
		if(board.getPieceAt(origin).validMove((coords[2] - coords[0]), (coords[3] - coords[1]))) return true;
		else return false;
	}
	private boolean isClear(String org, String dest, boolean whiteness){
		int[] coords = calculateCoordinates(org, dest);
		int xD = 0, yD = 0;
		if(coords[2] > coords[0]) xD = 1;
		else if(coords[2] < coords[0]) xD = -1;
		if(coords[3] > coords[1]) yD = 1;
		else if(coords[3] < coords[1]) yD = -1;
		String nextLocation = "";
		nextLocation += (char)(org.charAt(0) + xD);
		nextLocation += (char)(org.charAt(1) + yD);
		if(isEmpty(nextLocation)){
			if(nextLocation.equals(dest)){
				if(whiteness == board.getPieceAt(nextLocation).isWhite) return false;
				else return true;
			}
			else return false;
		} else {
			if(nextLocation.equals(dest)) return true;
			else return isClear(nextLocation, dest, whiteness);
		}
	}
	private boolean canJump(String origin){
		if(board.getPieceAt(origin).getClass().getSimpleName().equals("Knight")) return true;
		else return false;
	}
	private int[] calculateCoordinates(String org, String dest){//refactor this to interpret one coord instead of both
		int[] coords = new int[4];
		coords[0] = org.charAt(0) - 'A';
		coords[1] = org.charAt(1) - 1;
		coords[2] = dest.charAt(0) - 'A';
		coords[3] = dest.charAt(1) - 1;
		return coords;
	}
}