package model;

public class Square {
	private static final char COLUMN_OFFSET = 'A';
	private static final char ROW_OFFSET = '1';
	public final int X, Y;
	private Piece occupant;
	
	public Square(String squareString){
		X = squareString.charAt(0) - COLUMN_OFFSET;
		Y = squareString.charAt(1) - ROW_OFFSET;
	}
	public Square(int x, int y){
		X = x; Y = y;
	}
	
	public Piece getOccupant(){ return occupant; }
	public void setOccupant(Piece newOccupant){ occupant = newOccupant; }
	public void removeOccupant(){ occupant = null; }
	public boolean isOccupied(){ return occupant != null; }
	
	@Override
	public boolean equals(Object square){
		return ((Square)square).X == this.X && ((Square)square).Y == this.Y;
	}
	@Override
	public int hashCode() {
		return this.X*73 + this.Y*71;
	}
	@Override
	public String toString(){
		String display = "";
		display += (char)(X + COLUMN_OFFSET);
		display += (char)(Y + ROW_OFFSET);
		return display;
	}	
}
