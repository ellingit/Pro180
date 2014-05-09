package pieces;

public class Queen extends Piece {

	public Queen(boolean isEvil) {
		super(isEvil);
	}

	private boolean hasMoved = false;

	@Override
	public boolean validMove(int x, int y) {
		if(Math.abs(x) != Math.abs(y) && x != 0 && y != 0) return false;
		else return true;
	}
}
