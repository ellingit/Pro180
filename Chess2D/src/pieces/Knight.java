package pieces;

public class Knight extends Piece {

	public Knight(boolean isEvil) {
		super(isEvil);
	}

	public Knight() {

	}

	@Override
	public boolean validMove(int x, int y) {
		if(Math.abs(x) != 2 && Math.abs(y) != 2) return false;
		if(Math.abs(x) != 1 && Math.abs(y) != 1) return false;
		if((Math.abs(x) == 2 && Math.abs(y) != 1) || (Math.abs(x) == 1 && Math.abs(y) != 2)) return false;
		else return true;
	}
	@Override
	public String toString(){
		if(isWhite) return this.getClass().getSimpleName().substring(1,2).toUpperCase();
		else return this.getClass().getSimpleName().substring(1,2).toLowerCase();
	}
}