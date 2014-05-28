package pieces;

import javax.swing.ImageIcon;

public class Queen extends Piece {

	public Queen(boolean isWhite) {
		super(isWhite);
		icon = (isWhite) ? new ImageIcon("..\\pieceIcons\\wq.png") : new ImageIcon("..\\pieceIcons\\bq.png");
	}

	public Queen() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return (Math.abs(x) == Math.abs(y)) || (x == 0) ^ (y == 0);
	}
}
