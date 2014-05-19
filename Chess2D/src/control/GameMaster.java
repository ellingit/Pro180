package control;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pieces.*;
import board.GameBoard;
import board.Location;
import exceptions.InvalidMoveException;
import fileIO.FileIO;

public class GameMaster {
	private boolean whiteTurn = true;
	private GameBoard board = new GameBoard();
	Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
	Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?){1,2}");
		
	//Constructors
	public GameMaster(String filePath){
		getMovesFromFile("..\\setup.txt");
		printBoard();
		getMovesFromFile(filePath);
	}
	
	//Public Methods
	public void move(Location origin, Location destination) throws InvalidMoveException {
		if(validMove(origin, destination)){
			System.out.println(origin + " to " + destination);
			board.movePiece(destination, origin);
			if(isInCheck()) System.out.println("Check!");
			whiteTurn = !whiteTurn;
		}
		else throw new InvalidMoveException(origin + " to " + destination + " is not a valid move.");
	}
	
	//Private Methods
	//Retrieve set of moves from a text file
	private void getMovesFromFile(String filePath){
		String[] moves = new FileIO(filePath).getDataFromFile();
		for(String command : moves){
			Matcher placeMatch = placePattern.matcher(command);
			Matcher moveMatch = movePattern.matcher(command);
			if(placeMatch.matches()){
				placePiece(command, placeMatch);				
			} else if(moveMatch.matches()){
				movePiece(command, moveMatch);
			}
		}
	}
	//Place piece
	//TODO: Make it invalid to place a piece once the board has been set up
	private void placePiece(String command, Matcher placeMatch){
		boolean white;
		char pieceType = placeMatch.group("type").charAt(0);
		white = placeMatch.group("color").equals("L");
		Location location = new Location(placeMatch.group("location"));
		for(PieceType type : PieceType.values()){ 
			if(type.getKey() == pieceType){
				board.placePiece(location, type.makePiece(white));
			}
		}
	}
	//Move piece
	private void movePiece(String command, Matcher moveMatch){
		Location origin = null, destination = null;
		try {
			origin = new Location(moveMatch.group("origin"));
			destination = new Location(moveMatch.group("destination"));
			move(origin, destination);
			printBoard();
			//TODO: Fix the magic number 5
			if(command.length() > 5 && moveMatch.matches()){
				castle(command, moveMatch);
			}
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}
	//Perform a castling move(will not yet validate)
	private void castle(String command, Matcher moveMatch){
		//TODO: Castling has to allow the rook to jump
		Location origin = null, destination = null;
		try{
			origin = new Location(moveMatch.group("origin"));
			destination = new Location(moveMatch.group("destination"));
			move(origin, destination);
			printBoard();
		} catch (IllegalStateException ex){
			System.out.println(ex.getMessage());
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}
	//Print the board to the console
	private void printBoard(){
		Iterator<Piece> ip = board.iterator();
		int count = 1;
		System.out.print("|");
		while(ip.hasNext()){
			Piece p = ip.next();
			if(p != null) System.out.print(" " + p.toString() + " |");
			else System.out.print(" - |");
			if(++count % board.getBoardSize()-1 == 0) 
				if(count<63) System.out.print("\n|");
		}
		System.out.println();
	}
	//Return if enemy is in check
	private boolean isInCheck(){
		GameBoard.boardIterator ip = board.new boardIterator();
		Piece piece;
		Location kingLocation = null;
		while(kingLocation == null && ip.hasNext()){
			piece = ip.next();
			if(piece != null && piece instanceof King && piece.getWhiteness() != whiteTurn){
				kingLocation = ip.getPieceLocation();
			}
		}
		boolean canAttack = false;
		ip = board.new boardIterator();
		while(!canAttack && ip.hasNext()){
			ip.next();
			canAttack = (validMove(ip.getPieceLocation(), kingLocation));
		} 
		return canAttack;
	}
	//Move Checks
	//Return if move is valid based on turn and move checks below
	private boolean validMove(Location origin, Location destination){
		boolean valid = false;
		if(valid = !isEmpty(origin)){
			valid = board.getPieceAt(origin).getWhiteness() == whiteTurn && 
					isAllowed(origin, destination) && 
					(canJump(origin) || isClear(origin, destination, board.getPieceAt(origin).getWhiteness()));
		}
		return valid;
	}	
	//Return if origin is null(doesn't contain a piece)
	private boolean isEmpty(Location xy){
		return board.getPieceAt(xy) == null;
	}
	//Return if move to destination conforms to the rules for piece at origin
	private boolean isAllowed(Location origin, Location destination){
		Piece currentPiece = board.getPieceAt(origin);
		if(!isEmpty(destination) && currentPiece instanceof Pawn){
			return ((Pawn)currentPiece).validMove((destination.X - origin.X), (destination.Y - origin.Y), true);
		}
		else return board.getPieceAt(origin).validMove((destination.X - origin.X), (destination.Y - origin.Y));
	}
	//Overloaded method for potential use in verifying checkmate and for AI use
	/*private boolean isAllowed(Piece piece, Location origin, Location destination){
		return piece.validMove((destination.X - origin.X), (destination.Y - origin.Y));
	}*/
	//Recursively check one square at a time in a piece's path to it's destination and return if it's clear
	private boolean isClear(Location org, Location dest, boolean whiteness){
		boolean clear = false;
		int xD = dest.X - org.X; 
		int yD = dest.Y - org.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		Location nextXY = new Location(org.X + xD, org.Y + yD);
		if(!(clear = nextXY.X == dest.X && nextXY.Y == dest.Y)) 
			if(isEmpty(nextXY)) return isClear(nextXY, dest, whiteness);
			else clear = false;
		else if(!isEmpty(nextXY)) clear = whiteness != board.getPieceAt(nextXY).getWhiteness();
		return clear;
		//TODO: Fix this
//		boolean clear = true;
//		int xD = dest.X - org.X; 
//		int yD = dest.Y - org.Y;
//		if(xD != 0) xD /= Math.abs(xD);
//		if(yD != 0) yD /= Math.abs(yD);
//		Location nextXY;
//		do {
//			nextXY = new Location(org.X + xD, org.Y + yD);
//			clear = !isEmpty(nextXY);
//		} while(clear && nextXY.X != dest.X || nextXY.Y != dest.Y);
//		if(!isEmpty(nextXY)) clear = whiteness != board.getPieceAt(nextXY).getWhiteness();
//		return clear;
	}
	//Return if piece is a Knight (isClear method unnecessary)
	private boolean canJump(Location origin){
		return board.getPieceAt(origin) instanceof Knight;
	}
}