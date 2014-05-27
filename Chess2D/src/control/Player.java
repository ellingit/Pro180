package control;

import java.util.ArrayList;
import java.util.Scanner;

import board.Location;

public class Player {
	private boolean white;
	private Scanner scanLee = new Scanner(System.in);
	
	public Player(boolean white){
		this.white = white;
	}
	
	public Location choosePiece(ArrayList<Location> moveablePieces){
		for(int i = 0; i < moveablePieces.size(); i++){
			System.out.println((i+1) + ") " + moveablePieces.get(i));
		}
		System.out.print("Select location of the piece you'd like to move: ");
		int selection = -1;
		while(scanLee.hasNextLine() && (selection < 0 || selection > moveablePieces.size())){
			try {
				selection = Integer.parseInt(scanLee.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Selection");
				System.out.print("Try Again: ");
			}
		}
		return moveablePieces.get(selection-1);
	}
	public Location chooseMove(ArrayList<Location> possibleMoves){
		for(int i = 0; i < possibleMoves.size(); i++){
			System.out.println((i+1) + ") " + possibleMoves.get(i));
		}
		System.out.print("Select location you'd like to move to: ");
		int selection = -1;
		while(scanLee.hasNextLine() && (selection < 0 || selection > possibleMoves.size())){
			try {
				selection = Integer.parseInt(scanLee.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Selection");
				System.out.print("Try Again: ");
			}
		}
		return possibleMoves.get(selection-1);
	}
	
	public boolean isWhite(){
		return white;
	}
}