package pieces;

public class Queen extends Piece {

	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public Queen() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return (Math.abs(x) == Math.abs(y)) || (x == 0) ^ (y == 0);
	}
}
