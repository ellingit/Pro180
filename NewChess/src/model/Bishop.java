package model;

public class Bishop extends Piece {

	public Bishop(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 3;
	}

	@Override
	protected boolean isLegalMove(int x, int y) {
		return Math.abs(x) == Math.abs(y);
	}

}
