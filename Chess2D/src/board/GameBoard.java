package board;

import pieces.*;

public class GameBoard {
	private static final int BOARD_SIZE = 8;
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	public Piece getPieceAt(String location){
		return board[location.charAt(0) - 'A'][location.charAt(1) - 1];
	}
	public void movePieceTo(String location, Piece piece){
		board[location.charAt(0) - 'A'][location.charAt(1) - 1] = piece;
	}
	public Piece[][] getBoard(){
		return board;
	}
}
