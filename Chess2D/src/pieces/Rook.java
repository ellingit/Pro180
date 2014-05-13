package pieces;

public class Rook extends Piece {

	public Rook(boolean isEvil) {
		super(isEvil);
	}

	private boolean hasMoved = false;

	@Override
	public boolean validMove(int x, int y) {
		if(x != 0 && y != 0) return false;
		else return true;
	}
}