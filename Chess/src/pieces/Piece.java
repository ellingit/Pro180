package pieces;

public abstract class Piece {
	
	public final boolean isWhite;
	
	public Piece(boolean isWhite){
		this.isWhite = isWhite;
	}
	
	public abstract boolean validMove(int x, int y);
	
	@Override
	public String toString(){
		String display = "";
		display += this.getClass().getSimpleName().substring(0,1).toLowerCase();
		if(!isWhite) return display.toUpperCase();		
		return display;
	}
}
