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
		while(!gameOver && moveio.hasNextMove()){
			setMoves(motherBoard);
			try {
				if(isInCheck(motherBoard, whiteTurn)){
					String checkMessage = "";
					if(whiteTurn) checkMessage += "White ";
					else checkMessage += "Black ";
					System.out.println(checkMessage + "is in Check!");
				}
				makeMove(motherBoard, getNextMove());
				whiteTurn = !whiteTurn;
				printBoard(motherBoard);
				setMoves(motherBoard);
				if(checkmate(motherBoard, whiteTurn)){
					System.err.println("Checkmate!");
					System.exit(0);
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
	private Move getNextMove(){
		return moveio.getMove();
	}
	
	private boolean makeMove(GameBoard context, Move move) throws InvalidMoveException{
		boolean success = false;
		if(!isEmpty(context, move.FROM) && context.getPieceAt(move.FROM) != null
				&& context.getPieceAt(move.FROM).getAvailableMoves().contains(move.TO) 
				&& !resultsInCheck(context, move, whiteTurn)){
			context.movePiece(move.FROM, move.TO);
			System.out.println(move.FROM + " to " + move.TO);
			success = true;
		} else throw new InvalidMoveException(move + " is not a valid move.");
		return success;
	}
	
	private boolean isValidMove(GameBoard context, Move move, boolean turnColor){
		boolean validMove = false;
		if(!isEmpty(context, move.FROM)){
			validMove = context.getPieceAt(move.FROM).isWhite() == turnColor
						&& pathIsClear(context, move, context.getPieceAt(move.FROM).isWhite())
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
			clear = pieceIsWhite != context.getPieceAt(nextXY).isWhite();
		}
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
		boolean inCheck = isInCheck(testBoard, testingWhite);
		testBoard.movePiece(move.TO, move.FROM);
		return inCheck;
	}
	private boolean isInCheck(GameBoard context, boolean testingWhite){
		GameBoard.boardIterator iterator = context.new boardIterator();
		boolean inCheck = false;
		Location kingLocation = null;
		while(iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null && piece instanceof King && piece.isWhite() == testingWhite)
				kingLocation = iterator.getPieceLocation();
		}
		iterator = context.new boardIterator();
		while(!inCheck && iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null){
				ArrayList<Location> availableMoves = piece.getAvailableMoves();
				if(availableMoves != null && !availableMoves.isEmpty())
					inCheck = availableMoves.contains(kingLocation);
			}
		}
		return inCheck;
	}
	private boolean checkmate(GameBoard context, boolean testingWhite){
		boolean mate = true;
		GameBoard.boardIterator iterator = context.new boardIterator();
		while(mate && iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null && piece.isWhite() == testingWhite){
				for(Location location : piece.getAvailableMoves()){
					Move move = new Move(iterator.getPieceLocation(), location);
					if(!resultsInCheck(context, move, testingWhite)){
						mate = false;
					}
				}
			}
		}
		return mate;
	}
	
	private void setAllMovesFor(GameBoard context, Location from){
		ArrayList<Location> moves = new ArrayList<>();
		if(context.getPieceAt(from) != null){ 
			GameBoard.boardIterator iterator = context.new boardIterator();
			while(iterator.hasNext()){
				iterator.next();
				Move test = new Move(from, iterator.getPieceLocation());
				if(isValidMove(context, test, context.getPieceAt(from).isWhite())){
					moves.add(iterator.getPieceLocation());
				}
			}
		}
		context.getPieceAt(from).setAvailableMoves(moves);
	}
	private void setMoves(GameBoard context){
		GameBoard.boardIterator iterator = context.new boardIterator();
		while(iterator.hasNext()){
			Piece piece = iterator.next();
			if(piece != null){
				setAllMovesFor(context, iterator.getPieceLocation());
			}
		}
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
