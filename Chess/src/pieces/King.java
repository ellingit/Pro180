package pieces;

public class King extends Piece {

	public King(boolean isEvil) {
		super(isEvil);
	}
	private boolean hasMoved = false;
	private boolean inCheck = false;
	@Override
	public boolean validMove(int x, int y) {
		if(Math.abs(x) > 1 || Math.abs(y) > 1) return false;
		else return true;
	}
	
}
