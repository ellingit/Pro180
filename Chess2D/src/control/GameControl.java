package control;

import java.util.ArrayList;

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
	}
	
	public void play(){
		while(!gameOver){
			try {
				makeMove(getNextMove());
				whiteTurn = !whiteTurn;
			} catch(InvalidMoveException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	private Move getNextMove(){
		return moveio.getMove();
	}
	private boolean makeMove(Move move) throws InvalidMoveException{
		boolean success = false;
		if(isValidMove(motherBoard, move, whiteTurn) &&
				!resultsInCheck(motherBoard, move, whiteTurn)){
			motherBoard.movePiece(move.FROM, move.TO);
			success = true;
		} else throw new InvalidMoveException(move + " is not a valid move.");
		return success;
	}
	private boolean isValidMove(GameBoard context, Move move, boolean turnColor){
		boolean validMove = false;
		if(!isEmpty(move.FROM)){
			validMove = context.getPieceAt(move.FROM).getWhiteness() == turnColor &&
						pathIsClear(context, move, context.getPieceAt(move.FROM).getWhiteness()) &&
						isLegalMove(context, move);
		}
		return validMove;
	}
	private boolean isEmpty(Location location){
		return motherBoard.getPieceAt(location) == null;
	}
	private boolean pathIsClear(GameBoard context, Move move, boolean pieceIsWhite){
		boolean clear = false;
		int xD = move.TO.X - move.FROM.X; 
		int yD = move.TO.Y - move.FROM.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		Location nextXY;
		do { nextXY = new Location(move.FROM.X + xD, move.FROM.Y + yD); } 
		while(move.FROM != move.TO && isEmpty(nextXY));
		if(!isEmpty(nextXY)) clear = (pieceIsWhite != context.getPieceAt(nextXY).getWhiteness());
		return clear;
	}
	private boolean isLegalMove(GameBoard context, Move move){
		int xD = move.TO.X - move.FROM.X;
		int yD = move.TO.Y - move.FROM.Y;
		return context.getPieceAt(move.FROM).validMove(xD, yD);
	}
	
	private boolean resultsInCheck(GameBoard context, Move move, boolean testingWhite){
		GameBoard testBoard = new GameBoard(motherBoard);
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
			if(piece != null){
				moves = getAllMovesFrom(context, iterator.getPieceLocation());
				inCheck = moves.contains(kingLocation);
			}
		}
		return inCheck;
	}
//	private boolean checkmate(GameBoard context, boolean testingWhite){
//		return false;
//	}
	
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
}
