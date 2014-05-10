package board;

import java.util.*;

import pieces.*;
import ui.ConsoleUI;
import chess.GameEngine;
import exceptions.IllegalMoveException;

public class GameBoard {
	
	private static char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	private static int[] rows = {1, 2, 3, 4, 5, 6, 7, 8};
	private static LinkedHashMap<String, Piece> board = new LinkedHashMap<>();
	private LinkedHashMap<String, ArrayList<String>> moveablePieces;
	private GameEngine ge = new GameEngine("..\\foolsMate.txt");
	private List<String> moveSet;
	private boolean darkSide = false;
	private ConsoleUI cui = new ConsoleUI();
	
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
	//Find piece at a given location
	public void run(){
		ge.run();
		moveSet = ge.getMoves();
		for(String s : moveSet){
//			s = s.toUpperCase();
			try {
				if(s.matches(GameEngine.locMoveRgx)){
					System.out.println("\n" + s.substring(0,2) + " to " + s.substring(3,5) + "\n");
					move(s.substring(0,2), s.substring(3,5));
					System.out.println(this);
				} else if(s.matches(GameEngine.pcMoveRgx)){
					String position = s.substring(2);
					switch(s.charAt(0)){
					case 'k':
						if(s.charAt(1) == 'l') board.put(position, new King(false));
						else board.put(position, new King(true));
						break;
					case 'q':
						if(s.charAt(1) == 'l') board.put(position, new Queen(false));
						else board.put(position, new Queen(true));
						break;
					case 'b':
						if(s.charAt(1) == 'l') board.put(position, new Bishop(false));
						else board.put(position, new Bishop(true));
						break;
					case 'n':
						if(s.charAt(1) == 'l') board.put(position, new Knight(false));
						else board.put(position, new Knight(true));
						break;
					case 'r':
						if(s.charAt(1) == 'l') board.put(position, new Rook(false));
						else board.put(position, new Rook(true));
						break;
					case 'p':
						if(s.charAt(1) == 'l') board.put(position, new Pawn(false));
						else board.put(position, new Pawn(true));
						break;
					default:
						System.err.println("Invalid Piece Placement");
						break;
					}
//					System.out.println("\n" + GameEngine.COLOR_KEY.get(s.charAt(1)) + " " 
//											+ GameEngine.PIECE_KEY.get(s.charAt(0)) + " placed on " + s.substring(2));
				}
//				System.out.println(this);
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println(this);
		play();		
	}
	public Piece getPieceAt(String position){
		return board.get(position);
	}
	//Move piece at a given origin to a given destination
	public void move(String orig, String dest) throws IllegalMoveException{
		if(validatedMove(orig, dest)){
			board.put(dest, getPieceAt(orig));
			board.put(orig, null);
			darkSide = !darkSide;
			if(getPieceAt(dest).getClass().getSimpleName().equals("Pawn")) ((Pawn) getPieceAt(dest)).moved();
		}
		else throw new IllegalMoveException("Illegal Move");
	}
	//Game On!
	private void play(){
		while(true){
			getAvailableMoves();
			ArrayList<String> pieceOptions = showPiecesWithMoves();
			int selection = cui.PromptForSelection(pieceOptions);
			String o = pieceOptions.get(selection-1);
			ArrayList<String> moveOptions = moveablePieces.get(o);
			selection = cui.PromptForSelection(moveOptions);
			String d = moveOptions.get(selection-1);
			try { move(o, d); System.out.println(this);} 
			catch (IllegalMoveException e) { System.err.println(e.getMessage()); }
		}
	}
	//Find all possible moves for all pieces on the board
	private void getAvailableMoves(){
		moveablePieces = new LinkedHashMap<>();
		Iterator<Map.Entry<String, Piece>> i = board.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, Piece> kv = (Map.Entry<String, Piece>)i.next();
			if(kv.getValue() != null){
				moveablePieces.put(kv.getKey(), new ArrayList<String>());
				Iterator<String> it = board.keySet().iterator();
				while(it.hasNext()){
					String nextKey = it.next();
					if(validatedMove(kv.getKey(), nextKey)){
						moveablePieces.get(kv.getKey()).add(nextKey);
//						System.out.println(kv.getKey() + " to " + nextKey);
					}
				}
			}
		}
	}
	//Check if move violates any rules
	private boolean validatedMove(String orig, String dest){
		//Illegal Moves Not Yet Handled:
		//Conforms to en passant and castling rules
		//Move places King in check
		if(getPieceAt(orig) == null) return false;
		if(turnCheck(orig, dest)){
			if(moveCheck(orig, dest)){
				if(jump(orig, dest)) return true;
				else if(walk(orig, getPieceAt(orig).isEvil, dest)) return true;
				else return false;
			} else return false;
		} else return false;
	}
	//Make sure the move isn't out of turn
	private boolean turnCheck(String origin, String destination){
		if(getPieceAt(origin).isEvil && !darkSide) return false;
		else if(!getPieceAt(origin).isEvil && darkSide) return false;
		else return true;
	}
	//Check move against piece's rules
	private boolean moveCheck(String origin, String destination){
		int rowDif = destination.charAt(1) - origin.charAt(1);
		int colDif = destination.charAt(0) - origin.charAt(0);
		if(getPieceAt(origin).getClass().getSimpleName().equals("Pawn") && getPieceAt(destination) != null){
			if(!((Pawn)getPieceAt(origin)).validMove(colDif, rowDif, true)) return false;
			else return true;
		}
		if(!getPieceAt(origin).validMove(colDif, rowDif)) return false;
		else return true;
	}
	//Walk the line
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
	//Knights are too cool to walk
	private boolean jump(String origin, String destination){
		if(getPieceAt(origin).getClass().getSimpleName().equals("Knight")){
			if(getPieceAt(destination) != null && getPieceAt(destination).isEvil == getPieceAt(origin).isEvil) return false;
			else return true;
		} else return false;
	}
	private ArrayList<String> showPiecesWithMoves(){
//		getAvailableMoves();
		ArrayList<String> pieces = new ArrayList<>();
		Iterator<Map.Entry<String, ArrayList<String>>> i = moveablePieces.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, ArrayList<String>> kv = (Map.Entry<String, ArrayList<String>>)i.next();
			if(!kv.getValue().isEmpty())
				pieces.add(kv.getKey());
		}
		return pieces;
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