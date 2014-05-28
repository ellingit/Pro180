package pieces;

import javax.swing.ImageIcon;

public class Pawn extends Piece {

	public Pawn(boolean isWhite) {
		super(isWhite);
		icon = (isWhite) ? new ImageIcon("..\\pieceIcons\\wp.png") : new ImageIcon("..\\pieceIcons\\bp.png");
	}

	public Pawn() {
		
	}

	@Override	
	public boolean validMove(int x, int y){ 
		if(!white) y *= -1;
		return (y == 1 && x == 0) || (y == 2 && !hasMoved && x==0);
	}
	public boolean validMove(int x, int y, boolean capturing){
		if(!white) y *= -1;
		return y == 1 && Math.abs(x) == 1;
	}
}
