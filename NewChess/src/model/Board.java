package model;

public class Board {
	public static final int BOARD_SIZE = 8;
	private Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];
	
	public Board(){
		for(int row = squares.length-1; row >= 0; row--){
			for(int column = 0; column < squares[row].length; column++){
				squares[row][column] = new Square(column, row);
			}
		}
	}
	
	public void movePiece(Move move){
		getSquare(move.FROM.X, move.FROM.Y).getOccupant().setLocation(move.TO);
		getSquare(move.TO.X, move.TO.Y).setOccupant(getSquare(move.FROM.X, move.FROM.Y).getOccupant());
		getSquare(move.FROM.X, move.FROM.Y).removeOccupant();
	}
	public void placePiece(Piece piece){
		squares[piece.getLocation().Y][piece.getLocation().X].setOccupant(piece);
	}
	public Square getSquare(int x, int y){ 
		return squares[y][x]; 
	}
	
	@Override
	public String toString(){
		String boardDisplay = "\tA\tB\tC\tD\tE\tF\tG\tH\n\n";
		for(int row = squares.length-1; row >= 0; row--){
			boardDisplay += (row + 1) + "\t";
			for(int column = 0; column < squares[row].length; column++){
				if(squares[row][column].isOccupied()) boardDisplay += squares[row][column].getOccupant() + "\t";
				else boardDisplay += "-\t";
			}
			boardDisplay += "\n\n";
		}
		return boardDisplay;
	}
}
