package pieces;

public class Pawn extends Piece {

	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	public Pawn() {
		
	}

	private boolean hasMoved = false;

	@Override	
	public boolean validMove(int x, int y){ 
		if(!isWhite) y *= -1;
		return (y == 1 && x == 0) || (y == 2 && !hasMoved);
	}
	public boolean validMove(int x, int y, boolean capturing){
		if(!isWhite) y *= -1;
		return y == 1 && Math.abs(x) == 1;
	}
	public void moved(){
		hasMoved = true;
	}
}
