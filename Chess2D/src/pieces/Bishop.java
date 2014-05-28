package pieces;

import javax.swing.ImageIcon;

public class Bishop extends Piece {

	public Bishop(boolean isWhite) {
		super(isWhite);
		icon = (isWhite) ? new ImageIcon("..\\pieceIcons\\wb.png") : new ImageIcon("..\\pieceIcons\\bb.png");
	}

	public Bishop() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return Math.abs(x) == Math.abs(y);
	}
}
