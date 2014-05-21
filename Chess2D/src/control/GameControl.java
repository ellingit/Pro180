package control;

import java.util.ArrayList;
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
		while(!gameOver){
			try {
				makeMove(motherBoard, getNextMove());
				whiteTurn = !whiteTurn;
				if(isInCheck(motherBoard, whiteTurn)) System.out.println("Check!");
				printBoard(motherBoard);
			} catch(InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			} catch(NullPointerException ex) {
				//TODO: this is constantly waiting for more moves...
				//no more moves
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
	private Move getNextMove(){
		return moveio.getMove();
	}
	
	private boolean makeMove(GameBoard context, Move move) throws InvalidMoveException{
		boolean success = false;
		if(isValidMove(context, move, whiteTurn) &&	!resultsInCheck(context, move, whiteTurn)){
			context.movePiece(move.FROM, move.TO);
			System.out.println(move.FROM + " to " + move.TO);
			success = true;
		} else throw new InvalidMoveException(move + " is not a valid move.");
		return success;
	}
	
	private boolean isValidMove(GameBoard context, Move move, boolean turnColor){
		boolean validMove = false;
		if(!isEmpty(context, move.FROM)){
			validMove = context.getPieceAt(move.FROM).getWhiteness() == turnColor &&
						pathIsClear(context, move, context.getPieceAt(move.FROM).getWhiteness()) &&
						isLegalMove(context, move);
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
		if(!(clear = nextXY.X == move.TO.X && nextXY.Y == move.TO.Y)) 
			if(isEmpty(context, nextXY)) return pathIsClear(context, new Move(nextXY, move.TO), pieceIsWhite);
			else clear = false;
		else if(!isEmpty(context, nextXY)) clear = pieceIsWhite != context.getPieceAt(nextXY).getWhiteness();
		if(context.getPieceAt(move.FROM) instanceof Knight) clear = true;
		return clear;
	}
	private boolean isLegalMove(GameBoard context, Move move){
		int xD = move.TO.X - move.FROM.X;
		int yD = move.TO.Y - move.FROM.Y;
		return context.getPieceAt(move.FROM).validMove(xD, yD);
	}
	
	private boolean resultsInCheck(GameBoard context, Move move, boolean testingWhite){
		GameBoard testBoard = new GameBoard(context);
		testBoard.movePiece(move.FROM, move.TO);
		boolean inCheck = isInCheck(context, testingWhite);
		testBoard.movePiece(move.TO, move.FROM);
		return inCheck;
	}
	private boolean isInCheck(GameBoard context, boolean testingWhite){
		GameBoard.boardIterator iterator = context.new boardIterator();
		boolean inCheck = false;
		Location kingLocation = null;
		while(iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null && piece instanceof King && piece.getWhiteness() == testingWhite)
				kingLocation = iterator.getPieceLocation();
		}
		iterator = context.new boardIterator();
		while(iterator.hasNext()){
			Piece piece = iterator.next();
			ArrayList<Location> moves = new ArrayList<>();
			if(!inCheck && piece != null){
				moves = getAllMovesFrom(context, iterator.getPieceLocation());
				inCheck = moves.contains(kingLocation);
			}
		}
		return inCheck;
	}
	private boolean checkmate(GameBoard context, boolean testingWhite){
		boolean mate = true;
		GameBoard.boardIterator iterator = context.new boardIterator();
		while(mate && iterator.hasNext())
			Piece piece = iterator.next();
			ArrayList<Location> moves = getAllMovesFrom(context, iterator.getPieceLocation());
			for(Location location : moves){
				if(!resultsInCheck(context, new Move(iterator.getPieceLocation(), location), piece.getWhiteness()))
					mate = false;
			}
		return mate;
	}

	private ArrayList<Location> getAllMovesFrom(GameBoard context, Location from){
		ArrayList<Location> moves = new ArrayList<>();
		GameBoard.boardIterator iterator = context.new boardIterator();
		while(iterator.hasNext()){
			iterator.next();
			Move test = new Move(from, iterator.getPieceLocation());
			if(isValidMove(context, test, context.getPieceAt(from).getWhiteness())) 
				moves.add(iterator.getPieceLocation());
		}
		return moves;
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
}
