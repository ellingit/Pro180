package pieces;

public class Knight extends Piece {

	public Knight(boolean isEvil) {
		super(isEvil);
	}

	@Override
	public boolean validMove(int x, int y) {
		if((Math.abs(x) == 2 && Math.abs(y) != 1) || (Math.abs(x) == 1 && Math.abs(y) != 2)) return false;
		else return true;
	}

}