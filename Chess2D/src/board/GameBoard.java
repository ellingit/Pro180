package board;

import pieces.*;

public class GameBoard {
	private static final int BOARD_SIZE = 8;
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	public Piece getPieceAt(Location xy){
		return board[xy.Y][xy.X];
	}
	public void placePiece(Location newXY, Piece piece){
		board[newXY.Y][newXY.X] = piece;
	}
	public void movePiece(Location newXY, Location prevXY){
		board[newXY.Y][newXY.X] = board[prevXY.Y][prevXY.X];
		board[prevXY.Y][prevXY.X] = null;
	}
	public Piece[][] getBoard(){
		return board;
	}
}
