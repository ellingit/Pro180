package chess;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	
	private File f;
	
	public FileIO(String filePath){
		f = new File(filePath);
	}
	
	public List<String> getMoves(){
		List<String> moveSet = new ArrayList<>();
		try { moveSet = Files.readAllLines(f.toPath(), StandardCharsets.US_ASCII); } 
		catch (NoSuchFileException e) { System.err.println("File does not exist."); }
		catch (IOException e) { System.err.println("An unexpected error occurred. Try again."); }
		return moveSet;
	}
}