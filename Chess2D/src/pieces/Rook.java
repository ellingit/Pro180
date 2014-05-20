package pieces;

public class Rook extends Piece {

	public Rook(boolean isWhite) {
		super(isWhite);
	}
	public Rook() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return (x == 0) ^ (y == 0);
	}
}
