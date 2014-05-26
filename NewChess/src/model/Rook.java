package model;

public class Rook extends Piece {

	public Rook(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 5;
	}

	@Override
	protected boolean isLegalMove(int x, int y) {
		return (x == 0) ^ (y == 0);
	}

}
