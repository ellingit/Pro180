package control;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidMoveException;
import pieces.*;
import board.*;

public class GameControl {
	private GameBoard motherBoard = new GameBoard();
	private MoveIO moveio;
	private boolean gameOver = false;
	private boolean whiteTurn = true;
	
	public GameControl(String filepath){
		moveio = new MoveIO(filepath);
		while(moveio.hasNextPlacement()){
			placePiece(motherBoard, moveio.getPlacement());
		}
		printBoard(motherBoard);
	}
	
	public void play(){
		while(!gameOver && moveio.hasNextMove()){
			try {
//				if(isInCheck(motherBoard, locatePiece(motherBoard, "King", whiteTurn))){
//					String checkMessage = "";
//					if(whiteTurn) checkMessage += "White ";
//					else checkMessage += "Black ";
//					System.out.println(checkMessage + "is in Check!");
//				}
				makeMove(motherBoard, moveio.getMove());
				whiteTurn = !whiteTurn;
				printBoard(motherBoard);
				if(checkmate(motherBoard, whiteTurn)){
					System.err.println("Checkmate!");
					gameOver = true;
				}
			} catch(InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	private void placePiece(GameBoard context, String placeCommand){
		
		Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
		Matcher placeMatch = placePattern.matcher(placeCommand);
		
		if(placeMatch.matches()){
			char pieceType = placeMatch.group("type").charAt(0);
			boolean white = placeMatch.group("color").equals("L");
			Location location = new Location(placeMatch.group("location"));
			for(PieceType type : PieceType.values()){ 
				if(type.getKey() == pieceType){
					context.placePiece(location, type.makePiece(white));
				}
			}
		} else System.err.println("Invalid Piece Placement");
	}
		
	private boolean makeMove(GameBoard context, Move move) throws InvalidMoveException{
		boolean success = false;
		moveIterator mIterator = new moveIterator(context, move.FROM);
		boolean moveAvailable = false;
		while(mIterator.hasNext()){
			if(mIterator.next() == move.TO) moveAvailable = true;
		} 
		if(!isEmpty(context, move.FROM) && moveAvailable && !resultsInCheck(context, move, whiteTurn)){
			context.movePiece(move.FROM, move.TO);
			System.out.println(move.FROM + " to " + move.TO);
			success = true;
		} else throw new InvalidMoveException(move + " is not a valid move.");
		return success;
	}
	
	private boolean isValidMove(GameBoard context, Move move, boolean turnColor){
		boolean validMove = false;
		if(move != null && !isEmpty(context, move.FROM)){
			validMove = context.getPieceAt(move.FROM).getWhiteness() == turnColor
						&& pathIsClear(context, move, context.getPieceAt(move.FROM).getWhiteness())
						&& isLegalMove(context, move);
		}
		return validMove;
	}
	private boolean isEmpty(GameBoard context, Location location){
		return context.getPieceAt(location) == null;
	}
	private boolean pathIsClear(GameBoard context, Move move, boolean pieceIsWhite){
		boolean clear = false;
		int xD = move.TO.X - move.FROM.X; 
		int yD = move.TO.Y - move.FROM.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		Location nextXY = new Location(move.FROM.X + xD, move.FROM.Y + yD);
		if(!(clear = nextXY.X == move.TO.X && nextXY.Y == move.TO.Y)){ 
			if(isEmpty(context, nextXY) || isKnight(context, move)) return pathIsClear(context, new Move(nextXY, move.TO), pieceIsWhite);
			else clear = false;
		} else if(!isEmpty(context, nextXY)){
			clear = pieceIsWhite != context.getPieceAt(nextXY).getWhiteness();
		}
		System.out.println(clear);
		return clear;
	}
	private boolean isKnight(GameBoard context, Move move){
		return context.getPieceAt(move.FROM) instanceof Knight;
	}
	private boolean isLegalMove(GameBoard context, Move move){
		boolean legal = false;
		int xD = move.TO.X - move.FROM.X;
		int yD = move.TO.Y - move.FROM.Y;
		if(context.getPieceAt(move.FROM) instanceof Pawn && !isEmpty(context, move.TO)){
			legal = ((Pawn)context.getPieceAt(move.FROM)).validMove(xD, yD, true);
		} else legal = context.getPieceAt(move.FROM).validMove(xD, yD);
		return legal;
	}
	
	private boolean resultsInCheck(GameBoard context, Move move, boolean testingWhite){
		GameBoard testBoard = new GameBoard(context);
		testBoard.movePiece(move.FROM, move.TO);
		boolean inCheck = isInCheck(testBoard, locatePiece(context, "King", testingWhite));
		testBoard.movePiece(move.TO, move.FROM);
		return inCheck;
	}
	private Location locatePiece(GameBoard context, String pieceType, boolean testingWhite){
		GameBoard.boardIterator iterator = context.new boardIterator();
		Location pieceLocation = null;
		while(iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null && piece.getClass().getSimpleName().equals(pieceType) && piece.getWhiteness() == testingWhite)
				pieceLocation = iterator.getPieceLocation();
		}
		return pieceLocation;
	}
	private boolean isInCheck(GameBoard context, Location testLocation){
		GameBoard.boardIterator bIterator = context.new boardIterator();
		boolean inCheck = false;
		while(!inCheck && bIterator.hasNext()){
			Piece piece = bIterator.next();
			if(piece != null){
				GameBoard.boardIterator bIterator2 = context.new boardIterator();
				while(!inCheck && bIterator2.hasNext()){
					Move testMove = new Move(bIterator.getPieceLocation(), testLocation);
					if(isValidMove(context, testMove, piece.getWhiteness())) inCheck = true;
				}
//				moveIterator mIterator = new moveIterator(context, bIterator.getPieceLocation());
//				while(!inCheck && mIterator.hasNext()){//TODO: This line is throwing it into an endless loop
//					if(mIterator.next() == testLocation) inCheck = true;
//				}
			}
		}
		return inCheck;
	}
	
	
	private boolean checkmate(GameBoard context, boolean testingWhite){
		boolean mate = true;
		GameBoard.boardIterator bIterator = context.new boardIterator();
		while(bIterator.hasNext() && bIterator.next() != null){
			Location location = bIterator.getPieceLocation();
			moveIterator mIterator = new moveIterator(context, location);
			while(mate && mIterator.hasNext()){
				mate = resultsInCheck(context, new Move(location, mIterator.next()), context.getPieceAt(location).getWhiteness());
			}
		}
		return mate;
	}
	
	private void printBoard(GameBoard context){
		GameBoard.boardIterator iterator = context.new boardIterator();
		int count = 1;
		System.out.print("|");
		while(iterator.hasNext()){
			Piece p = iterator.next();
			if(p != null) System.out.print(" " + p.toString() + " |");
			else System.out.print(" - |");
			if(++count % context.getBoardSize()-1 == 0) 
				if(count < Math.pow(context.getBoardSize(), 2)) System.out.print("\n|");
		}
		System.out.println();
	}

	private class moveIterator implements Iterator<Location>{
		private Location startLocation, currentLocation;
		private GameBoard context;
		private GameBoard.boardIterator bIterator;
		
		moveIterator(GameBoard context, Location location){
			startLocation = location;
			this.context = context;
			bIterator = context.new boardIterator();
		}
		
		@Override
		public boolean hasNext() {
			return bIterator.hasNext();
		}

		@Override
		public Location next() {
			Location nextValidMove = null;
			Move nextMove = null;
			bIterator.next();
			nextMove = new Move(startLocation, bIterator.getPieceLocation());
			return nextValidMove;
		}
		public Location getCurrent(){
			return currentLocation;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
