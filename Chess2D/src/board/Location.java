package board;

public class Location {
	public final int X, Y;
	
	public Location(String locString){
		X = locString.charAt(0) - 'A';
		Y = locString.charAt(1) - '1';
	}
	public Location(int x, int y){
		X = x; Y = y;
	}
	
	@Override
	public String toString(){
		String display = "";
		display += (char)(X + 'A');
		display += (char)(Y + '1');
		return display;
	}
}
