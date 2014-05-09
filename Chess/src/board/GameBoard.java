package board;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import pieces.*;

public class GameBoard {
	
	private static char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	private static int[] rows = {1, 2, 3, 4, 5, 6, 7, 8};
	private static LinkedHashMap<String, Piece> board = new LinkedHashMap<>();
	
	static {
		for(int r=rows.length-1; r>=0; r--){
			for(int c=0; c<columns.length; c++){
				board.put(columns[c] + Integer.toString(rows[r]), null);
			}
		}
	}
	
	public GameBoard(boolean gameStart){
		if(gameStart){
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
	
	public Piece getPieceAt(String position){
		return board.get(position);
	}
	public void moveTo(Piece p, String dest){
		board.put(dest, p);
	}
	
	@Override
	public String toString(){
		String display = "";
		Iterator<Map.Entry<String, Piece>> i = board.entrySet().iterator();
		int count = 1;
		while(i.hasNext()){
			if(count > 8){ display += "\n\n"; count = 1; }
			Map.Entry<String, Piece> next = (Map.Entry<String, Piece>)i.next();
			if(next.getValue() != null) display += next.getValue() + "\t";
			else display += "[]\t";
			count++;
		}
		return display;
	}
}