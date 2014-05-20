package board;

import java.util.Iterator;

import pieces.*;

public class GameBoard implements Iterable<Piece> {
	private static final int BOARD_SIZE = 8;
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	public GameBoard(){
		
	}
	public GameBoard(GameBoard source){
		board = source.cloneBoard();
	}
	
	public Piece getPieceAt(Location xy){
		return board[xy.Y][xy.X];
	}
	public void placePiece(Location newXY, Piece piece){
		board[newXY.Y][newXY.X] = piece;
	}
	public void movePiece(Location prevXY, Location newXY){
		board[newXY.Y][newXY.X] = board[prevXY.Y][prevXY.X];
		board[prevXY.Y][prevXY.X] = null;
	}
	public int getBoardSize(){
		return BOARD_SIZE;
	}
	public Piece[][] cloneBoard(){
		Piece[][] clone = new Piece[BOARD_SIZE][BOARD_SIZE];
		for(int y=0; y<board.length; y++){
			for(int x=0; x<board[y].length; x++){
				clone[x][y] = board[x][y];
			}
		}
		return clone;
	}
	@Override
	public Iterator<Piece> iterator() {
		return this.new boardIterator();
	}
	
	public class boardIterator implements Iterator<Piece>{
		private int row, column;
		private Location currentLocation;
		
		public boardIterator(){
			row = board.length-1;
			column = 0;
		}
		
		@Override
		public boolean hasNext() {
			return row >= 0;
		}
		@Override
		public Piece next() {
			currentLocation = new Location(column, row);
			Piece p = board[row][column];
			incrementCounter();
			return p;
		}
		@Override
		public void remove() {
			board[row][column] = null;
		}
		public Location getPieceLocation(){
			return currentLocation;
		}
		private void incrementCounter(){
			if(board.length-1 == column++){
				column = 0;
				row--;
			}
		}
	}
}