package pieces;

public class King extends Piece {

	public King(boolean isWhite) {
		super(isWhite);
	}
	public King() {

	}
	
	@Override
	public boolean validMove(int x, int y) {
		return (Math.abs(x) <= 1 && Math.abs(y) <= 1);// || 		//standard move
//			   (Math.abs(x) == 2 && !hasMoved); //castling
	}
}
