package pieces;

public class Knight extends Piece {

	public Knight(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public boolean validMove(int x, int y) {
		return Math.abs(x) == 2 && Math.abs(y) == 1 || (Math.abs(x) == 1 && Math.abs(y) == 2);
	}
	@Override
	public String toString(){
		if(white) return "N";
		else return "n";
	}
}