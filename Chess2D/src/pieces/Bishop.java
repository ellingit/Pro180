package pieces;

public class Bishop extends Piece {

	public Bishop(boolean isWhite) {
		super(isWhite);
	}

	public Bishop() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return Math.abs(x) == Math.abs(y);
	}
}
