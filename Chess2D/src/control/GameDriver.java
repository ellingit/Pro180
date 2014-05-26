package control;

public class GameDriver {

	public static void main(String[] args) {
//		new GameMaster("..\\kasparov_vs_topalov.txt");
//		new GameMaster("..\\foolsGame.txt");
		new GameControl("..\\whiteCheckmate.txt").play();
	}

}
