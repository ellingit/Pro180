package model;

import exceptions.*;

public class Knight extends Piece {

	public Knight(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 3;
	}
	
	@Override
	public boolean move(Square destination) throws InvalidMoveException{
		boolean success = false;
		Move move = new Move(location, destination);
		int xD = move.TO.X - move.FROM.X; 
		int yD = move.TO.Y - move.FROM.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		if(!isLegalMove(xD, yD)) throw new IllegalMoveException();
		if(!destination.isOccupied() || destination.getOccupant().getColor() != this.getColor()){
			context.movePiece(new Move(location, destination));
			success = true;
		} else throw new IllegalCaptureException();
		return success;
	}
	@Override
	protected boolean isLegalMove(int x, int y) {
		return Math.abs(x) == 2 && Math.abs(y) == 1 || (Math.abs(x) == 1 && Math.abs(y) == 2);
	}
	@Override
	public String toString(){
		String display;
		if(this.getColor() == PlayerColor.WHITE) display = this.getClass().getSimpleName().substring(1,2).toUpperCase();
		else display = this.getClass().getSimpleName().substring(1,2).toLowerCase();
		return display;
	}
}
