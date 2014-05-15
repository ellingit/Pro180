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
	//TODO: Find a better way to implement this
	public Location locateKing(boolean isWhite){
		Location kingLocation = null;
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[i].length; j++){
				if(board[i][j].getClass().getSimpleName().equals("King") && board[i][j].getWhiteness() == isWhite)
					kingLocation = new Location(i,j);
			}
		}
		return kingLocation;
	}
	public Piece[][] getBoard(){
		Piece[][] displayBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
		for(int i=0; i<BOARD_SIZE; i++){
			for(int j=0; j<board[i].length; j++){
				displayBoard[i][j] = board[i][j];
			}
		}
		return displayBoard;
	}
	class boardIterator{
		private int row, column;
		public boardIterator(){
			row = 0; column = 0;
		}
		public Piece next(){
			int r = row; 
			if(row == board.length-1) row++;
			return board[r][column++];
		}
		public Piece nextPiece(){
			for(Piece[] row : board){
				for(Piece p : row){
					if(p != null) return p;
				}
			}
			return null;
		}
		public Location nextEmptySquare(){
			for(int i=0; i<BOARD_SIZE; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j] == null) return new Location(i,j);
				}
			}
			return null;
		}
		public boolean hasNext(){
			if(row == board.length-1 && column == board[row].length-1) return false;
			else return true;
		}
	}
}