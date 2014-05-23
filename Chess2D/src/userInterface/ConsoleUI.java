package userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import board.Location;
import control.Move;

public class ConsoleUI implements UI {
	Scanner scant = new Scanner(System.in);
	
	public Move promptForPiece(HashMap<Location, ArrayList<Location>> possibilities, boolean isWhiteTurn){
		int count = 1;
		for(Location start : possibilities.keySet()){
			System.out.print(count + ") " + start + "\n");
			count++;
		}
		System.out.print("Select a position to display possible moves: ");
		return null;
	}
}