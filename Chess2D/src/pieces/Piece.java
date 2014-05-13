package pieces;

public abstract class Piece {
	public final boolean isWhite;
	
	public abstract boolean validMove(int x, int y);
	
	public Piece(boolean isWhite){
		this.isWhite = isWhite;
	}
}
