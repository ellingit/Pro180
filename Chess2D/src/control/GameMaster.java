package control;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pieces.*;
import userInterface.ConsoleUI;
import board.GameBoard;
import board.GameBoard.boardIterator;
import board.Location;
import exceptions.InvalidMoveException;
import fileIO.FileIO;

public class GameMaster {
	private boolean whiteTurn = true;
	private GameBoard board = new GameBoard();
//	private ConsoleUI conUI = new ConsoleUI();
	Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
	Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?){1,2}");
//	private HashMap<Location, ArrayList<Location>> allMoves = new HashMap<>();
		
	//Constructors
	public GameMaster(String filePath){
		getMovesFromFile("..\\setup.txt");
		printTest();
//		conUI.printBoard(board);
		getMovesFromFile(filePath);
	}
	
	//Public Methods
	public void move(Location origin, Location destination) throws InvalidMoveException {
		if(validMove(origin, destination)){
			System.out.println(origin + " to " + destination);
			board.movePiece(destination, origin);
			whiteTurn = !whiteTurn;
		}
		else throw new InvalidMoveException(origin + " to " + destination + " is not a valid move.");
	}
	
	//Private Methods
	//File Loader
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
	private void movePiece(String command, Matcher moveMatch){
		Location origin = null, destination = null;
		try {
			origin = new Location(moveMatch.group("origin"));
			destination = new Location(moveMatch.group("destination"));
			move(origin, destination);
			printTest();
			if(command.length() > 5 && moveMatch.matches()){
				castle(command, moveMatch);
			}
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}
	private void castle(String command, Matcher moveMatch){
		//TODO: Castling has to allow the rook to jump
		Location origin = null, destination = null;
		try{
			origin = new Location(moveMatch.group("origin"));
			destination = new Location(moveMatch.group("destination"));
			move(origin, destination);
			printTest();
		} catch (IllegalStateException ex){
			System.out.println(ex.getMessage());
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void printTest(){
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
	
	/*
	private void findAllMoves(){
		for(int i=0; i<board.getBoard().length; i++){
			for(int j=0; j<board.getBoard()[i].length; j++){
				Location currentLocation = new Location(i, j);
				if(board.getPieceAt(currentLocation) != null){
					ArrayList<Location> moves = getDestinations(currentLocation);
					if(moves.size() > 0) allMoves.put(currentLocation, moves);
				}
			}
		}
	}
	private ArrayList<Location> getDestinations(Location origin){
		ArrayList<Location> moves = new ArrayList<>();
		for(int i=0; i<board.getBoard().length; i++){
			for(int j=0; j<board.getBoard()[i].length; j++){
				Location currentLocation = new Location(i,j);
				if(validMove(origin, currentLocation)) moves.add(currentLocation);
			}
		}
		return moves;
	}
	*/
	
	private boolean isInCheck(){
		GameBoard.boardIterator ip = (boardIterator)board.iterator();
		while(ip.hasNext()){
			Piece p = ip.next();
			if(p.getClass().getSimpleName().equals("King") && p.getWhiteness() != whiteTurn){
				Location kingLocation = ip.getPieceLocation();
				//call isAllowed(new Queen(whiteTurn), kingLocation, --dest--) for all squares
				//and isAllowed(new Knight(whiteTurn), kingLocation, --dest--) for all squares
				//if any valid moves are found, the king is in check
				//TODO: find all accessible squares for a queen or knight from this location
			}
		}
		return false;
	}
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
		return board.getPieceAt(xy) == null;
	}
	private boolean isAllowed(Location origin, Location destination){
		Piece currentPiece = board.getPieceAt(origin);
		if(currentPiece.getClass().getSimpleName().equals("Pawn") && !isEmpty(destination)){
			return ((Pawn)currentPiece).validMove((destination.X - origin.X), (destination.Y - origin.Y), true);
		}
		else return board.getPieceAt(origin).validMove((destination.X - origin.X), (destination.Y - origin.Y));
	}
	private boolean isAllowed(Piece piece, Location origin, Location destination){
		return piece.validMove((destination.X - origin.X), (destination.Y - origin.Y));
	}
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
	}
	private boolean canJump(Location origin){
		return board.getPieceAt(origin).getClass().getSimpleName().equals("Knight");
	}
}