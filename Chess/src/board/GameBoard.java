package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import chess.GameEngine;
import exceptions.IllegalMoveException;

public class GameBoard {
	
	private static char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	private static int[] rows = {1, 2, 3, 4, 5, 6, 7, 8};
	private static LinkedHashMap<String, Piece> board = new LinkedHashMap<>();
	private GameEngine ge = new GameEngine("..\\moves.txt");
	private List<String> moveSet;
	private boolean darkSide = false;
	
	static {
		//build the empty board
		for(int r=rows.length-1; r>=0; r--){
			for(int c=0; c<columns.length; c++){
				board.put(columns[c] + Integer.toString(rows[r]), null);
			}
		}
	}
	//Create a new board, boolean determines if it's the beginning of the game
	public GameBoard(boolean gameStart){
		if(gameStart){
			//place all the pieces
			board.put("A1", new Rook(false));
			board.put("B1", new Knight(false));
			board.put("C1", new Bishop(false));
			board.put("D1", new Queen(false));
			board.put("E1", new King(false));
			board.put("F1", new Bishop(false));
			board.put("G1", new Knight(false));
			board.put("H1", new Rook(false));
			
			board.put("A2", new Pawn(false));
			board.put("B2", new Pawn(false));
			board.put("C2", new Pawn(false));
			board.put("D2", new Pawn(false));
			board.put("E2", new Pawn(false));
			board.put("F2", new Pawn(false));
			board.put("G2", new Pawn(false));
			board.put("H2", new Pawn(false));

			board.put("A8", new Rook(true));
			board.put("B8", new Knight(true));
			board.put("C8", new Bishop(true));
			board.put("D8", new Queen(true));
			board.put("E8", new King(true));
			board.put("F8", new Bishop(true));
			board.put("G8", new Knight(true));
			board.put("H8", new Rook(true));
			
			board.put("A7", new Pawn(true));
			board.put("B7", new Pawn(true));
			board.put("C7", new Pawn(true));
			board.put("D7", new Pawn(true));
			board.put("E7", new Pawn(true));
			board.put("F7", new Pawn(true));
			board.put("G7", new Pawn(true));
			board.put("H7", new Pawn(true));
		}
	}
	//Find piece at a give location
	public void run(){
		ge.run();
		moveSet = ge.getMoves();
		for(String s : moveSet){
			s = s.toUpperCase();
			try {
				System.out.println("\n" + s.substring(0,2) + " to " + s.substring(3,5) + "\n");
				move(s.substring(0,2), s.substring(3,5));
				System.out.println(this);
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}
		}
//		getAvailableMoves();
	}
	public Piece getPieceAt(String position){
		return board.get(position);
	}
	//Move piece at a given origin to a given destination
	public void move(String orig, String dest) throws IllegalMoveException{
		if(validMove(orig, dest)){
			board.put(dest, getPieceAt(orig));
			board.put(orig, null);
			darkSide = !darkSide;
		}
		else throw new IllegalMoveException("Illegal Move");
	}
	//Find all possible moves for all pieces on the board
	private HashMap<String, ArrayList<String>> getAvailableMoves(){
		HashMap<String, ArrayList<String>> moveablePieces = new HashMap<>();
		Iterator<Map.Entry<String, Piece>> i = board.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, Piece> kv = (Map.Entry<String, Piece>)i.next();
			if(kv.getValue() != null){
				moveablePieces.put(kv.getKey(), new ArrayList<String>());
				Iterator<String> it = board.keySet().iterator();
				while(it.hasNext()){
					String nextKey = it.next();
					if(validMove(kv.getKey(), nextKey)){
						moveablePieces.get(kv.getKey()).add(nextKey);
						System.out.println(kv.getKey() + " to " + nextKey);
					}
				}
			}
		}
		return moveablePieces;
	}
	//Check if move violates any rules
	private boolean validMove(String orig, String dest){
		//Illegal Moves Not Yet Handled:
		//Conforms to en passant and castling rules
		//Move places King in check
//		if(getPieceAt(orig) == null) return false;
//		if(getPieceAt(orig).isEvil && !darkSide) return false;
//		else if(!getPieceAt(orig).isEvil && darkSide) return false;		
//		if(!coastIsClear(orig, dest)) return false;
//		if(getPieceAt(dest) != null && getPieceAt(orig).isEvil == getPieceAt(dest).isEvil) return false;
//		int rowDif = dest.charAt(1) - orig.charAt(1);
//		int colDif = dest.charAt(0) - orig.charAt(0);
//		if(getPieceAt(orig).getClass().getSimpleName().equals("Pawn") && getPieceAt(dest) != null){
//			if(!((Pawn)getPieceAt(orig)).validMove(colDif, rowDif, true)) return false;
//		}
//		if(!getPieceAt(orig).validMove(colDif, rowDif)) return false;
		if(getPieceAt(orig) == null) return false;
		if(turnCheck(orig, dest)){
			if(moveCheck(orig, dest)){
				if(getPieceAt(orig).validMove(dest.charAt(0) - orig.charAt(0), dest.charAt(1) - orig.charAt(1))){
					if(jump(orig, dest)) return true;
					else if(walk(orig, getPieceAt(orig).isEvil, dest)) return true;
					else return false;
				} else return false;
			} else return false;
		} else return false;
	}
	//Check piece's path to make sure there are no pieces in the way
	private boolean coastIsClear(String orig, String dest){
		int rowDif = dest.charAt(1) - orig.charAt(1);
		int colDif = dest.charAt(0) - orig.charAt(0);
		
		if(rowDif == 0){
			if(colDif == 0) return false;
			if(colDif > 0){
				for(char i=(char)(orig.charAt(0) + 1); i<dest.charAt(0); i+=1){
					String key = "";
					key += (char)i;
					key += orig.charAt(1);
					if(getPieceAt(key) != null) return false;
				}
			} else {
				for(char i=(char)(orig.charAt(0) - 1); i>dest.charAt(0); i-=1){
					String key = "";
					key += (char)i;
					key += orig.charAt(1);
					if(getPieceAt(key) != null) return false;
				}
			}
		} else{
			if(colDif == 0) {
				if(rowDif > 0){
					for(char i=(char)(orig.charAt(1) + 1); i<dest.charAt(1); i+=1){
						String key = "";
						key += orig.charAt(0);
						key += (char)i;
						if(getPieceAt(key) != null) return false;
					}
				} else {
					for(char i=(char)(orig.charAt(1) - 1); i>dest.charAt(1); i-=1){
						String key = "";
						key += orig.charAt(0);
						key += (char)i;
						if(getPieceAt(key) != null) return false;
					}
				}
			} else {
				if(Math.abs(rowDif) == Math.abs(colDif)){
					if(colDif > 0){
						if(rowDif > 0){
							char r = (char)(orig.charAt(1) + 1);
							for(char c=(char)(orig.charAt(0) + 1); c<dest.charAt(0); c+=1){
								String key = ""; 
								key += c;
								key += r;
								if(rowDif>0)r+=1;
								else r+=1;
								if(getPieceAt(key) != null) return false;
							}
						} else {
							char r = (char)(orig.charAt(1) - 1);
							for(char c=(char)(orig.charAt(0) + 1); c<dest.charAt(0); c+=1){
								String key = ""; 
								key += c;
								key += r;
								if(rowDif>0)r+=1;
								else r-=1;
								if(getPieceAt(key) != null) return false;
							}
						}
					} else {
						if(rowDif > 0){
							char r = (char)(orig.charAt(1) + 1);
							for(char c=(char)(orig.charAt(0) - 1); c>dest.charAt(0); c-=1){
								String key = ""; 
								key += c;
								key += r;
								if(rowDif>0)r+=1;
								else r+=1;
								if(getPieceAt(key) != null) return false;
							}
						} else {
							char r = (char)(orig.charAt(1) - 1);
							for(char c=(char)(orig.charAt(0) - 1); c>dest.charAt(0); c-=1){
								String key = ""; 
								key += c;
								key += r;
								if(rowDif>0)r+=1;
								else r-=1;
								if(getPieceAt(key) != null) return false;
							}
						}
					}
				} else return true;
			}
		}
		return true;
	}
	//Print the board to the console
	@Override
	public String toString(){
		String display = "";
		Iterator<Map.Entry<String, Piece>> i = board.entrySet().iterator();
		int count = 1;
		while(i.hasNext()){
			if(count > 8){ display += "\n\n"; count = 1; }
			Map.Entry<String, Piece> next = (Map.Entry<String, Piece>)i.next();
			if(next.getValue() != null) display += next.getValue() + "\t";
			else display += "-\t";
			count++;
		}
		return display;
	}

	private boolean turnCheck(String origin, String destination){
		if(getPieceAt(origin).isEvil && !darkSide) return false;
		else if(!getPieceAt(origin).isEvil && darkSide) return false;
		else return true;
	}
	private boolean moveCheck(String origin, String destination){
		int rowDif = destination.charAt(1) - origin.charAt(1);
		int colDif = destination.charAt(0) - origin.charAt(0);
		if(getPieceAt(origin).getClass().getSimpleName().equals("Pawn") && getPieceAt(destination) != null){
			if(!((Pawn)getPieceAt(origin)).validMove(colDif, rowDif, true)) return false;
		}
		if(!getPieceAt(origin).validMove(colDif, rowDif)) return false;
		else return true;
	}
	private boolean walk(String origin, boolean evil, String destination){
		if(origin.equals(destination)) return false;
		int xd = 0, yd = 0;
		if(destination.charAt(0) > origin.charAt(0)) xd = 1;
		else if(destination.charAt(0) < origin.charAt(0)) xd = -1;
		if(destination.charAt(1) > origin.charAt(1)) yd = 1;
		else if(destination.charAt(1) < origin.charAt(1)) yd = -1;
		String nextLocation = "";
		nextLocation += (char)(origin.charAt(0) + xd);
		nextLocation += (char)(origin.charAt(1) + yd);
		if(board.get(nextLocation) != null){
			if(nextLocation.equals(destination)){
				if(evil == board.get(nextLocation).isEvil) return false;
				else return true;
			}
			else return false;
		} else {
			if(nextLocation.equals(destination)) return true;
			else return walk(nextLocation, evil, destination);
		}
	}
	private boolean jump(String origin, String destination){
		if(getPieceAt(origin).getClass().getSimpleName().equals("Knight")){
			if(getPieceAt(destination) != null && getPieceAt(destination).isEvil == getPieceAt(origin).isEvil) return false;
			else return true;
		} else return false;
	}
}