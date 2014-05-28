package pieces;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import board.Location;

public abstract class Piece{
	protected boolean white, hasMoved;
	protected ArrayList<Location> availableMoves;
	protected ImageIcon icon;
	
	public abstract boolean validMove(int x, int y);
	
	public Piece(){
		availableMoves = new ArrayList<>();
	}
	public Piece(boolean isWhite){
		this.white = isWhite;
		availableMoves = new ArrayList<>();
	}
	public boolean isWhite(){
		return white;
	}
	public void moved(){
		hasMoved = true;
	}
	public boolean hasMoved(){
		return hasMoved;
	}
	public ArrayList<Location> getAvailableMoves(){
		return availableMoves;
	}
	public void setAvailableMoves(ArrayList<Location> moves){
		availableMoves = moves;
	}
	public ImageIcon getIcon(){
		return icon;
	}
	
	@Override
	public String toString(){
		if(white) return this.getClass().getSimpleName().substring(0,1).toUpperCase();
		else return this.getClass().getSimpleName().substring(0,1).toLowerCase();
	}
}
