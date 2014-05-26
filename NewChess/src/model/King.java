package model;

public class King extends Piece {

	public King(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 500;
	}

	@Override
	protected boolean isLegalMove(int x, int y) {
		return (Math.abs(x) <= 1 && Math.abs(y) <= 1);
		// (Math.abs(x) == 2 && !hasMoved) <--Castling
	}

}
