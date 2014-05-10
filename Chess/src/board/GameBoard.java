package board;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pieces.*;
import exceptions.*;
import chess.GameEngine;

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
	//Check if move violates any rules
	private boolean validMove(String orig, String dest){
		//Illegal Moves Not Yet Handled:
		//Conforms to en passant and castling rules
		//Move places King in check
		if(getPieceAt(orig) == null) return false;
		if(getPieceAt(orig).isEvil && !darkSide) return false;
		else if(!getPieceAt(orig).isEvil && darkSide) return false;		
		if(!coastIsClear(orig, dest)) return false;
		if(getPieceAt(dest) != null && getPieceAt(orig).isEvil == getPieceAt(dest).isEvil) return false;
		int rowDif = dest.charAt(1) - orig.charAt(1);
		int colDif = dest.charAt(0) - orig.charAt(0);
		if(getPieceAt(orig).getClass().getSimpleName().equals("Pawn") && getPieceAt(dest) != null){
			if(!((Pawn)getPieceAt(orig)).validMove(colDif, rowDif, true)) return false;
		}
		if(!getPieceAt(orig).validMove(colDif, rowDif)) return false;
		return true;
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
						char r = orig.charAt(1);
						for(char c=(char)(orig.charAt(0) + 1); c<dest.charAt(0); c+=1){
							String key = ""; key += c + r;
							if(rowDif>0)r+=1;
							else r-=1;
							if(getPieceAt(key) != null) return false;
							
						}
					} else {
						char r = orig.charAt(1);
						for(char c=(char)(orig.charAt(0) - 1); c>dest.charAt(0); c-=1){
							String key = ""; key += c + r;
							if(rowDif>0)r+=1;
							else r-=1;
							if(getPieceAt(key) != null) return false;
							
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
}