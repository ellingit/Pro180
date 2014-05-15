package pieces;

public class Knight extends Piece {

	public Knight(boolean isEvil) {
		super(isEvil);
	}

	@Override
	public boolean validMove(int x, int y) {
		if((Math.abs(x) == 2 && Math.abs(y) == 1) || (Math.abs(x) == 1 && Math.abs(y) == 2)) return true;
		else return false;
	}
	@Override
	public String toString(){
		if(isEvil) return this.getClass().getSimpleName().substring(1,2);
		else return this.getClass().getSimpleName().substring(1,2).toUpperCase();
	}
}