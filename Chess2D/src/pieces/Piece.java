package pieces;

public abstract class Piece{
	protected boolean isWhite, hasMoved;
	
	public abstract boolean validMove(int x, int y);
	
	public Piece(){
		
	}
	public Piece(boolean isWhite){
		this.isWhite = isWhite;
	}
	public boolean getWhiteness(){
		return isWhite;
	}
	public void moved(){
		hasMoved = true;
	}
	public boolean hasMoved(){
		return hasMoved;
	}
	@Override
	public String toString(){
		if(isWhite) return this.getClass().getSimpleName().substring(0,1).toUpperCase();
		else return this.getClass().getSimpleName().substring(0,1).toLowerCase();
	}
}
