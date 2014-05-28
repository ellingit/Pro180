package pieces;

import javax.swing.ImageIcon;

public class Rook extends Piece {

	public Rook(boolean isWhite) {
		super(isWhite);
		icon = (isWhite) ? new ImageIcon("..\\pieceIcons\\wr.png") : new ImageIcon("..\\pieceIcons\\br.png");
	}
	public Rook() {

	}

	@Override
	public boolean validMove(int x, int y) {
		return (x == 0) ^ (y == 0);
	}
}
