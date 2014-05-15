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
		try(BufferedReader buffer = new BufferedReader(new FileReader(file))){
			String nextLine;
			while((nextLine = buffer.readLine()) != null){
				moves.add(nextLine.toUpperCase());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] movesAsArray = new String[moves.size()];
		return moves.toArray(movesAsArray);
	}
}
