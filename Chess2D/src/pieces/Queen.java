package pieces;

public class Queen extends Piece {

	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public Queen() {

	}

	@Override
	public boolean validMove(int x, int y) {
		if(Math.abs(x) != Math.abs(y) && x != 0 && y != 0) return false;
		else return true;
	}
}
