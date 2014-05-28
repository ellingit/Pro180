package control;

import java.util.ArrayList;
import java.util.Scanner;

import pieces.PieceType;
import board.Location;

public class Player {
	private boolean white;
	private Scanner scanLee = new Scanner(System.in);
	
	public Player(boolean white){
		this.white = white;
	}
	
	public Location promptMenuSelection(ArrayList<Location> moveablePieces, String message){
		for(int i = 0; i < moveablePieces.size(); i++){
			System.out.println((i+1) + ") " + moveablePieces.get(i));
		}
		System.out.print("Select location of the piece you'd like to move: ");
		int selection = -1;
		while(selection <= 0 || selection > moveablePieces.size()){
			try {
				if(selection != -1){
					System.out.println("Invalid Selection");
					System.out.print("Try Again: ");
				}
				selection = Integer.parseInt(scanLee.nextLine());
			} catch (NumberFormatException nfe) { }
		}
		return moveablePieces.get(selection-1);
	}
	public PieceType choosePieceType(){
		for(int i = 0; i < PieceType.values().length; i++){
			System.out.println((i+1) + ") " + PieceType.values()[i]);
		}
		System.out.print("Choose a piece type to promote to: ");
		int selection = -1;
		while(selection <= 0 || selection >= PieceType.values().length){
			try {
				selection = Integer.parseInt(scanLee.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid Selection");
				System.out.print("Try Again: ");
			}
		}
		return PieceType.values()[selection-1];
	}
	
	public boolean isWhite(){
		return white;
	}
}