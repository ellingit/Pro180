package pieces;

public class King extends Piece {

	public King(boolean isWhite) {
		super(isWhite);
	}
	public King() {

	}
	private boolean hasMoved = false;
	private boolean inCheck = false;
	@Override
	public boolean validMove(int x, int y) {
		return Math.abs(x) <= 1 && Math.abs(y) <= 1;
	}
}
