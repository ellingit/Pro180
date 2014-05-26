package model;

public class Move {
	public final Square FROM;
	public final Square TO;
	
	public Move(Square from, Square to){
		FROM = from; TO = to;
	}
	
	@Override
	public String toString(){
		return FROM + " to " + TO;
	}
}
