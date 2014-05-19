package pieces;

public class Rook extends Piece {

	public Rook(boolean isWhite) {
		super(isWhite);
	}

	public Rook() {

	}

	private boolean hasMoved = false;

	@Override
	public boolean validMove(int x, int y) {
		return (x == 0) ^ (y == 0);
	}
}
