package pieces;

public class King extends Piece {

	public King(boolean isWhite) {
		super(isWhite);
	}
	public King() {

	}
	
	private boolean everChecked = false;
	@Override
	public boolean validMove(int x, int y) {
		return (Math.abs(x) <= 1 && Math.abs(y) <= 1) || 		//standard move
			   (Math.abs(x) == 2 && !hasMoved && !everChecked); //castling
	}
	public void check(){
		everChecked = true;
	}
	public boolean hasBeenInCheck(){
		return everChecked;
	}
}
