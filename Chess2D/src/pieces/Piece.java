package pieces;

public abstract class Piece{
	protected boolean isWhite;
	
	public abstract boolean validMove(int x, int y);
	
	public Piece(){
		
	}
	public Piece(boolean isWhite){
		this.isWhite = isWhite;
	}
	public boolean getWhiteness(){
		return isWhite;
	}
	@Override
	public String toString(){
		if(isWhite) return this.getClass().getSimpleName().substring(0,1).toUpperCase();
		else return this.getClass().getSimpleName().substring(0,1).toLowerCase();
	}
}
