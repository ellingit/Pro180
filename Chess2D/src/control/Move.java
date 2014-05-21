package control;

import board.Location;

public class Move {
	public final Location FROM, TO;
	
	Move(Location from, Location to){
		FROM = from; TO = to;
	}
	
	@Override
	public String toString(){
		return FROM + " to " + TO;
	}
}