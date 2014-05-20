package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceType;
import board.GameBoard;
import board.Location;
import exceptions.InvalidMoveException;
import fileIO.FileIO;

public class GameMaster {
	private boolean whiteTurn = true;
	private GameBoard board = new GameBoard();
	Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
	Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?)"
										+ "((?<origin2>[A-H][1-8])\\s(?<destination2>[A-H][1-8])[\\*\\s]?)?");
		
	//Constructors
	public GameMaster(String filePath){
		getMovesFromFile("..\\setup.txt");
		printBoard();
		getMovesFromFile(filePath);
	}
	
	//Public Methods
	public void move(GameBoard testBoard, Location origin, Location destination) throws InvalidMoveException {
		if(moveChecksKing(origin, destination)){
			throw new InvalidMoveException(origin + " to " + destination + " will result in check.");
		}
		if(validMove(testBoard, origin, destination, whiteTurn)){
			System.out.println(origin + " to " + destination);
			if(attemptingToCastle(testBoard, origin, destination)) castleWithChecks(testBoard, origin, destination);
			else board.movePiece(origin, destination);
			board.getPieceAt(destination).moved();
			printBoard();
			whiteTurn = !whiteTurn;
			if(isInCheck(board, false)) System.out.println("Check!");
			if(isInCheckMate(board)){
				String gameOver = "Checkmate!";
				if(whiteTurn) gameOver += " Black Wins.";
				else gameOver += " White Wins.";
				System.err.println(gameOver);
				System.exit(0);
			}
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
			move(board, origin, destination);
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
				if(count < Math.pow(board.getBoardSize(), 2)) System.out.print("\n|");
		}
		System.out.println();
	}
	//Return if enemy is in check
	private boolean isInCheck(GameBoard testBoard, boolean isWhiteTurn){
		GameBoard.boardIterator iterator = testBoard.new boardIterator();
		Piece piece;
		Location kingLocation = null;
		while(kingLocation == null && iterator.hasNext()){
			piece = iterator.next();
			if(piece != null && piece instanceof King && piece.getWhiteness() == isWhiteTurn){//color check isn't working in test cases
				kingLocation = iterator.getPieceLocation();
			}
		}
		boolean canAttack = false;
		iterator = testBoard.new boardIterator();
		while(!canAttack && iterator.hasNext()){
			iterator.next();
			canAttack = (validMove(testBoard, iterator.getPieceLocation(), kingLocation, !whiteTurn));
			if(canAttack) ((King)testBoard.getPieceAt(kingLocation)).check();
		} 
		return canAttack;
	}
	//Return if move is valid based on turn and move checks below
	private boolean validMove(GameBoard testBoard, Location origin, Location destination, boolean isWhiteTurn){
		boolean valid = false;
		if(valid = !isEmpty(testBoard, origin)){
			valid = testBoard.getPieceAt(origin).getWhiteness() == isWhiteTurn &&
					isAllowed(testBoard, origin, destination) && 
					(canJump(testBoard, origin) || isClear(testBoard, origin, destination, testBoard.getPieceAt(origin).getWhiteness()));
		}
		return valid;
	}
	//Return if requested move will leave or put the king in check
	private boolean moveChecksKing(Location origin, Location destination){
		GameBoard testBoard = new GameBoard(board);
		testBoard.movePiece(origin, destination);
		return isInCheck(testBoard, !whiteTurn);
	}
	//Return if the king is in checkmate
	private boolean isInCheckMate(GameBoard testBoard){
		GameBoard.boardIterator iterator = testBoard.new boardIterator();
		boolean stillInCheck = true;
		while(stillInCheck && iterator.hasNext()){
			Piece piece = iterator.next();
			for(Location location : getPossibleMoves(testBoard, iterator.getPieceLocation())){
				if(!moveChecksKing(iterator.getPieceLocation(), location)) stillInCheck = false;
			}
		}
		return stillInCheck;
	}
	//Return all possible moves for a piece at a given location
	private ArrayList<Location> getPossibleMoves(GameBoard testBoard, Location origin){
		ArrayList<Location> moves = new ArrayList<>();
		for(int i=0; i<testBoard.getBoardSize(); i++){
			for(int j=0; j<testBoard.getBoardSize(); j++){
				Location currentLocation = new Location(i,j);
				if(validMove(testBoard, origin, currentLocation, whiteTurn)) moves.add(currentLocation);
			}
		}
		return moves;
	}
	//Return if origin is null(doesn't contain a piece)
	private boolean isEmpty(GameBoard testBoard, Location xy){
		return testBoard.getPieceAt(xy) == null;
	}
	//Return if move to destination conforms to the rules for piece at origin
	private boolean isAllowed(GameBoard testBoard, Location origin, Location destination){
		Piece currentPiece = testBoard.getPieceAt(origin);
		if(!isEmpty(testBoard, destination) && currentPiece instanceof Pawn){
			return ((Pawn)currentPiece).validMove((destination.X - origin.X), (destination.Y - origin.Y), true);
		} else return testBoard.getPieceAt(origin).validMove((destination.X - origin.X), (destination.Y - origin.Y));
	}
	//Recursively check one square at a time in a piece's path to it's destination and return if it's clear
	private boolean isClear(GameBoard testBoard, Location org, Location dest, boolean whiteness){
		boolean clear = false;
		int xD = dest.X - org.X; 
		int yD = dest.Y - org.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		Location nextXY = new Location(org.X + xD, org.Y + yD);
		if(!(clear = nextXY.X == dest.X && nextXY.Y == dest.Y)) 
			if(isEmpty(testBoard, nextXY)) return isClear(testBoard, nextXY, dest, whiteness);
			else clear = false;
		else if(!isEmpty(testBoard, nextXY)) clear = whiteness != testBoard.getPieceAt(nextXY).getWhiteness();
		return clear;
	}
	//Return if piece is a Knight (isClear method unnecessary)
	private boolean canJump(GameBoard testBoard, Location origin){
		return testBoard.getPieceAt(origin) instanceof Knight;
	}
	//Return if proposed move is an attempted castle (King moves 2 spaces)
	private boolean attemptingToCastle(GameBoard testBoard, Location origin, Location destination){
		return !isEmpty(testBoard, origin) && testBoard.getPieceAt(origin) instanceof King &&
				Math.abs(destination.X - origin.X) == 2;
	}
	//Attempt to perform a proper castle, return whether successful
	private boolean castleWithChecks(GameBoard testBoard, Location origin, Location destination){
		boolean successfulCastle = false;
		Location rookLocation;
		if(!testBoard.getPieceAt(origin).hasMoved() && !((King)testBoard.getPieceAt(origin)).hasBeenInCheck()){
			if(destination.X - origin.X > 0){
				rookLocation = new Location(destination.X+1, destination.Y);
				if(!testBoard.getPieceAt(rookLocation).hasMoved()){
					testBoard.movePiece(origin, destination);
					testBoard.movePiece(rookLocation, new Location(destination.X-1, destination.Y));
					successfulCastle = true;
				}
			} else {
				rookLocation = new Location(destination.X-2, destination.Y);
				if(!testBoard.getPieceAt(rookLocation).hasMoved()){
					testBoard.movePiece(origin, destination);
					testBoard.movePiece(rookLocation, new Location(destination.X+1, destination.Y));
					successfulCastle = true;
				}
			}
		}
		return successfulCastle;
	}
}