package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {
	private Scanner scanLee = new Scanner(System.in);
	private boolean validInput;
	private String input;
	
	public int PromptForSelection(ArrayList<String> options){
		int selection = 0;
		validInput = false;
		do{
			for(int i=0; i<options.size(); i++){
				System.out.println((i+1) + ") " + options.get(i));
			}
			System.out.println("0) Exit");
			System.out.print("\nChoose a piece: ");
			input = scanLee.nextLine();
			try{
				selection = Integer.parseInt(input);
				validInput = true;
			}
			catch(NumberFormatException e){System.out.println("Choose the number of a piece from the list.\n");}
		}
		while(!validInput || selection < 0 || selection > options.size());
		return selection;
	}
}
