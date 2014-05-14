package control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pieces.*;
import userInterface.ConsoleUI;
import board.GameBoard;
import board.Location;
import exceptions.InvalidMoveException;
import fileIO.FileIO;

public class GameMaster {
	private boolean whiteTurn = true;
	private GameBoard board = new GameBoard();
	private ConsoleUI conUI = new ConsoleUI();
		
	//Constructor
	public GameMaster(){
		getMovesFromFile("..\\setup.txt");
		conUI.printBoard(board);
		getMovesFromFile("..\\moves.txt");
	}
	
	//Public Methods
	public void move(Location origin, Location destination) throws InvalidMoveException {
		if(validMove(origin, destination)) board.movePiece(destination, origin);
		else throw new InvalidMoveException("That move is not allowed.");
	}
	
	//Private Methods
	//File Loader
	private void getMovesFromFile(String filePath){
		String[] moves = new FileIO(filePath).getDataFromFile();
		Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
		Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?){1,2}");
		
		for(String command : moves){
			Matcher placeMatch = placePattern.matcher(command);
			Matcher moveMatch = movePattern.matcher(command);
			if(placeMatch.matches()){
				//place the piece
				boolean white;
				char pieceType = placeMatch.group("type").charAt(0);
				if(placeMatch.group("color").equals("L")) white = true;
				else white = false;
				Location location = new Location(placeMatch.group("location"));
				for(PieceType type : PieceType.values()){ 
					if(type.getKey() == pieceType){
						board.placePiece(location, type.makePiece(white));
					}
				}
			} else if (moveMatch.matches()){
				//move the piece
				Location origin = null, destination = null;
				try {
					origin = new Location(moveMatch.group("origin"));
					destination = new Location(moveMatch.group("destination"));
					move(origin, destination);
					conUI.printBoard(board);
					try{
						origin = new Location(moveMatch.group("origin"));
						destination = new Location(moveMatch.group("destination"));
						move(origin, destination);
						conUI.printBoard(board);
					} catch (IllegalStateException ex){
						System.out.println(ex.getMessage());
					}
				} catch (InvalidMoveException e) {
					System.out.println(origin + " to " + destination + " is not a valid move.");
				}
			}
			
		}
	}
	//Move Checkers
	//Move Checks
	private boolean validMove(Location origin, Location destination){
		if(isEmpty(origin)) return false;
		if(board.getPieceAt(origin).getWhiteness() == whiteTurn){
			if(isAllowed(origin, destination)){
				if(canJump(origin)) return true;
				else if(isClear(origin, destination, board.getPieceAt(origin).getWhiteness())) return true;
			}
		}
		return false;
	}
	private boolean isEmpty(Location xy){
		if(board.getPieceAt(xy) == null) return true;
		else return false;
	}
	private boolean isAllowed(Location origin, Location destination){ //TODO: This method seems pretty broken...rewrite?
		Piece currentPiece = board.getPieceAt(origin);
		//TODO: Consider overloading this method's return type instead of parameters...might be easier to check
		if(currentPiece.getClass().getSimpleName().equals("Pawn") && !isEmpty(destination)){
			if(!((Pawn)currentPiece).validMove((destination.Y - origin.Y), (destination.X - origin.X), true)) return false;
			else return true;
		}//TODO: This may be where the piece movement is failing...moving sideways instead of forward
		if(board.getPieceAt(origin).validMove((destination.Y - origin.Y), (destination.X - origin.X))) return true;
		else return false;
	}
	private boolean isClear(Location org, Location dest, boolean whiteness){
		int xD = 0, yD = 0;
		if(dest.X > org.X) xD = 1;
		else if(dest.X < org.X) xD = -1;
		if(dest.Y > org.Y) yD = 1;
		else if(dest.Y < org.Y) yD = -1;
		Location nextXY = new Location(org.X + xD, org.Y + yD);
		if(nextXY.X == dest.X && nextXY.Y == dest.Y){
			if(!isEmpty(nextXY)){
				if(whiteness == board.getPieceAt(nextXY).getWhiteness()) return false;
				else return true;
			} else return true;
		} else {
			if(!isEmpty(nextXY)) return false;
			else return isClear(nextXY, dest, whiteness);
		}
	}
	private boolean canJump(Location origin){
		if(board.getPieceAt(origin).getClass().getSimpleName().equals("Knight")) return true;
		else return false;
	}
}