package pieces;

public class Pawn extends Piece {

	public Pawn(boolean isEvil) {
		super(isEvil);
	}

	private boolean hasMoved = false;

	@Override	
	public boolean validMove(int x, int y) {
		if(isEvil) y *= -1;
		if(y > 1 && hasMoved) return false;
		else if(y > 2 || y < 1 || x != 0) return false;
		else return true;
	}
	public boolean validMove(int x, int y, boolean capturing){
		if(isEvil) y *= -1;
		if(x != 1 || Math.abs(y) != 1) return false;
		else return true;
	}
	public void moved(){
		hasMoved = true;
	}
}
