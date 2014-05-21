package control;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import board.Location;
import fileIO.FileIO;

public class MoveIO {
	LinkedList<Move> moveCommands;
	Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?)"
										+ "((?<origin2>[A-H][1-8])\\s(?<destination2>[A-H][1-8])[\\*\\s]?)?");
	
	public MoveIO(String filePath){
		loadMovesFromFile(filePath);
		loadMoveFromStream();
	}
	
	public void sendMove(Move move){
		System.out.print(move);
	}
	public Move getMove(){
		return moveCommands.pop();
	}
	
	private void loadMovesFromFile(String filePath){
		String[] moves = new FileIO(filePath).getDataFromFile();
		moveCommands = new LinkedList<>();
		for(String command : moves){
			Matcher moveMatch = movePattern.matcher(command);
			if(moveMatch.matches()){
				moveCommands.offer(getNextMoveCommand(command, moveMatch));
			}
		}
	}
	private void loadMoveFromStream(){
		Scanner scant = new Scanner(System.in);
		String input = scant.nextLine();
		Matcher moveMatch = movePattern.matcher(input);
		moveCommands.offer(getNextMoveCommand(input, moveMatch));
	}
	private Move getNextMoveCommand(String command, Matcher moveMatch){
		Location origin = null, destination = null;
		origin = new Location(moveMatch.group("origin"));
		destination = new Location(moveMatch.group("destination"));
		return new Move(origin, destination);
	}
}
