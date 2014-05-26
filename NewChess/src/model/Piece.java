package model;

import exceptions.*;

public abstract class Piece {
	protected int pointValue;
	protected PlayerColor color;
	protected boolean moved;
	protected Board context;
	protected Square location;
	
	public Piece(PlayerColor color, Square location, Board context){
		this.color = color;
		this.moved = false;
		this.location = location;
	}
	
	protected abstract boolean isLegalMove(int x, int y);
	
	public boolean move(Square destination) throws InvalidMoveException {
		boolean success = false;
		if(destination.isOccupied() && destination.getOccupant().getColor() == this.getColor())
			throw new IllegalCaptureException();
		Move move = new Move(location, destination);
		int xD = move.TO.X - move.FROM.X; 
		int yD = move.TO.Y - move.FROM.Y;
		if(xD != 0) xD /= Math.abs(xD);
		if(yD != 0) yD /= Math.abs(yD);
		if(isLegalMove(xD, yD)){
			Square newLocation = location;
			Square nextSquare = newLocation;
			while(nextSquare != destination){
				nextSquare = new Square(newLocation.X + xD, newLocation.Y + yD);
				if(nextSquare.isOccupied()) throw new PathObscuredException();
			}
			context.movePiece(new Move(location, newLocation));
			success = true;
		}
		else throw new IllegalMoveException();
		return success;
	}
	
	public int getPointValue(){ return pointValue; }
	public PlayerColor getColor(){ return color; };
	public boolean hasMoved(){ return moved; };
	public void setMoved(){ moved = true; }
	public Square getLocation(){ return location; }
	public void setLocation(Square newLocation){ location = newLocation; }
	
	@Override
	public String toString(){
		String display;
		if(this.getColor() == PlayerColor.WHITE) display = this.getClass().getSimpleName().substring(0,1).toUpperCase();
		else display = this.getClass().getSimpleName().substring(0,1).toLowerCase();
		return display;
	}
}
