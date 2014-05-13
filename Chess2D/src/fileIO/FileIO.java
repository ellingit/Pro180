package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
	private String filePath;
	
	public FileIO(String filePath){
		this.filePath = filePath;
	}
	
	public String[] getDataFromFile(){
		ArrayList<String> moves = new ArrayList<>();
		File file = new File(filePath);
		FileReader fileRead;
		try {
			fileRead = new FileReader(file);
			BufferedReader buffer = new BufferedReader(fileRead);
			String nextLine;
			while((nextLine = buffer.readLine()) != null){
				moves.add(nextLine);
			}
			fileRead.close();
			buffer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String[])moves.toArray();
	}	
//	private void parseMoves(){
//		//TODO: Fix this method
//		Pattern placePattern = Pattern.compile("(?<type>[KQBNRP])(?<color>[LD])(?<location>[A-H][1-8])");
//		Pattern movePattern = Pattern.compile("((?<origin>[A-H][1-8])\\s(?<destination>[A-H][1-8])[\\*\\s]?){1,2}");
//		String[] moves = moveExtractor.getDataFromFile();
//		for(String move : moves){
//			if(placePattern.matcher(move).matches()){
//				//place the piece
//				boolean white;
//				if(placePattern.matcher(move).group("color").equals("L")) white = true;
//				else white = false;
//				String pieceType = PIECE_KEY.get(placePattern.matcher(move).group("type"));
//				String location = placePattern.matcher(move).group("location");
//			} else if (movePattern.matcher(move).matches()){
//				//move the piece
//				String origin = movePattern.matcher(move).group("origin");
//				String destination = movePattern.matcher(move).group("destination");
//			}
//		}
//	}
}
