package board;

public class BoardTest {

	public static void main(String[] args) {
		//new game: use "..\\setup.txt"
		GameBoard gb = new GameBoard("..\\setup.txt");
		gb.run();
	}
}
