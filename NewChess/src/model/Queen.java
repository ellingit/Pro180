package model;

public class Queen extends Piece {

	public Queen(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 9;
	}

	@Override
	protected boolean isLegalMove(int x, int y) {
		return (Math.abs(x) == Math.abs(y)) || (x == 0) ^ (y == 0);
	}

}
