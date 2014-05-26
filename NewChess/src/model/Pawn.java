package model;

import exceptions.*;

public class Pawn extends Piece {

	public Pawn(PlayerColor color, Square location, Board context) {
		super(color, location, context);
		pointValue = 1;
	}
	
	@Override
	public boolean move(Square destination) throws InvalidMoveException {
		boolean success = false;
		Move move = new Move(location, destination);
		int xD = move.TO.X - move.FROM.X; 
		int yD = move.TO.Y - move.FROM.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		if(!destination.isOccupied() && isLegalMove(xD, yD)){
			Square newLocation = location;
			Square nextSquare = newLocation;
			while(nextSquare != destination){
				nextSquare = new Square(newLocation.X + xD, newLocation.Y + yD);
				if(nextSquare.isOccupied()) throw new PathObscuredException();
			}
			context.movePiece(new Move(location, newLocation));
			success = true;
		} else if(destination.isOccupied() && destination.getOccupant().getColor() == this.getColor() && isLegalCapture(xD, yD)){
			context.movePiece(new Move(location, destination));
			success = true;
		} else throw new IllegalMoveException();
		return success;
	}
	@Override
	protected boolean isLegalMove(int x, int y) {
		if(this.color == PlayerColor.BLACK) y *= -1;
		return (y == 1 && x == 0) || (y == 2 && !moved && x==0);
	}
	private boolean isLegalCapture(int x, int y){
		return y == 1 && Math.abs(x) == 1;
	}
}
