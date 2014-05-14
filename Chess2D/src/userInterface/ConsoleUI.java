package userInterface;

import pieces.Piece;
import board.GameBoard;

public class ConsoleUI implements UI {
	public void printBoard(GameBoard board){
		System.out.println();
		Piece[][] boardContents = board.getBoard();
		for(int i=boardContents.length-1; i>=0; i--){
			for(Piece square : boardContents[i]){
				if(square == null) System.out.print("-\t");
				else System.out.print(square + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}
