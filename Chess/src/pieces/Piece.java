package pieces;

public abstract class Piece {
	
	public final boolean isEvil;
	
	public Piece(boolean isEvil){
		this.isEvil = isEvil;
	}
	
	public abstract boolean validMove(int x, int y);
	
	@Override
	public String toString(){
		String display = "";
		display += this.getClass().getSimpleName().substring(0,1).toLowerCase();
		if(!isEvil) return display.toUpperCase();		
		return display;
	}
}
