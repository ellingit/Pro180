package pieces;

public abstract class Piece {
	
	protected boolean isEvil;
	
	public Piece(boolean isEvil){
		this.isEvil = isEvil;
	}
	
	@Override
	public String toString(){
		String display = "";
		if(isEvil) display += "D";
		else display += "L";
		display += this.getClass().getSimpleName().substring(0,1);
		return display;
	}
}
