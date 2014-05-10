package chess;

import java.util.HashMap;
import java.util.List;

public class GameEngine {
	private FileIO fio;
	private List<String> moveSet;
	private String pcMoveRgx = "[kqbnrp][ld][a-h][1-8]";
	private String locMoveRgx = "([a-h][1-8]\\s[a-h][1-8][\\*\\s]?){1,2}";
	public static final HashMap<Character, String> COLOR_KEY = new HashMap<>();
	public static final HashMap<Character, String> PIECE_KEY = new HashMap<>();
	static {
		COLOR_KEY.put('l', "light");
		COLOR_KEY.put('d', "dark");
		PIECE_KEY.put('k', "King");
		PIECE_KEY.put('q', "Queen");
		PIECE_KEY.put('b', "Bishop");
		PIECE_KEY.put('n', "Knight");
		PIECE_KEY.put('r', "Rook");
		PIECE_KEY.put('p', "Pawn");
	}
	
	public GameEngine(String filePath){
		fio = new FileIO(filePath);
	}
	
	public void run(){
		moveSet = fio.getMoves();
		validateMoves();
		//System.out.println(parseMovesToText());
	}
	public List<String> getMoves(){
		return moveSet;
	}
	private void validateMoves(){
		for(int i=0; i<moveSet.size(); i++){
			if(!moveSet.get(i).matches(pcMoveRgx) && !moveSet.get(i).matches(locMoveRgx)) 
				moveSet.remove(moveSet.get(i));
		}
	}
	
//	private String parseMovesToText(){
//		String move = "";
//		for(String s : moveSet){
//			if(s.toLowerCase().matches(pcMoveRgx)){
//				move += "\nPlace " + COLOR_KEY.get(s.charAt(1)) + " " + PIECE_KEY.get(s.charAt(0)) + " on " + s.substring(2);
//			}
//			else{
//				if(s.length() < 7){
//					move += "\nMove the piece on " + s.substring(0,3) + " to " + s.substring(3,5);
//					if(s.contains("*")) move += " and capture the piece at " + s.substring(3,5);
//				} else {
//					if(s.contains("a1")){ move += "\nlight queen's side castle"; }
//					else if(s.contains("h1")){ move += "\nlight king's side castle"; }
//					else if(s.contains("a8")){ move += "\ndark king's side castle"; }
//					else if(s.contains("h8")){ move += "\ndark queen's side castle"; }
//					else move = "\n\nInvalid Move\n";
//				}
//			}
//		}
//		return move;
//	}
}
