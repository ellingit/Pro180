package pieces;

public class Bishop extends Piece {

	public Bishop(boolean isEvil) {
		super(isEvil);
	}

	public Bishop() {

	}

	@Override
	public boolean validMove(int x, int y) {
		if(Math.abs(x) != Math.abs(y)) return false;
		else return true;
	}
}
