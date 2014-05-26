package control;

import model.*;

public class Game {
	private Board gameBoard;
	private Player whitePlayer, blackPlayer;
	private boolean gameOver;
	
	{//Initial board setup
		gameBoard = new Board();
		gameBoard.placePiece(new Rook(PlayerColor.BLACK, new Square("A8"), gameBoard));
		gameBoard.placePiece(new Knight(PlayerColor.BLACK, new Square("B8"), gameBoard));
		gameBoard.placePiece(new Bishop(PlayerColor.BLACK, new Square("C8"), gameBoard));
		gameBoard.placePiece(new King(PlayerColor.BLACK, new Square("D8"), gameBoard));
		gameBoard.placePiece(new Queen(PlayerColor.BLACK, new Square("E8"), gameBoard));
		gameBoard.placePiece(new Bishop(PlayerColor.BLACK, new Square("F8"), gameBoard));
		gameBoard.placePiece(new Knight(PlayerColor.BLACK, new Square("G8"), gameBoard));
		gameBoard.placePiece(new Rook(PlayerColor.BLACK, new Square("H8"), gameBoard));
		
		gameBoard.placePiece(new Rook(PlayerColor.WHITE, new Square("A1"), gameBoard));
		gameBoard.placePiece(new Knight(PlayerColor.WHITE, new Square("B1"), gameBoard));
		gameBoard.placePiece(new Bishop(PlayerColor.WHITE, new Square("C1"), gameBoard));
		gameBoard.placePiece(new King(PlayerColor.WHITE, new Square("D1"), gameBoard));
		gameBoard.placePiece(new Queen(PlayerColor.WHITE, new Square("E1"), gameBoard));
		gameBoard.placePiece(new Bishop(PlayerColor.WHITE, new Square("F1"), gameBoard));
		gameBoard.placePiece(new Knight(PlayerColor.WHITE, new Square("G1"), gameBoard));
		gameBoard.placePiece(new Rook(PlayerColor.WHITE, new Square("H1"), gameBoard));
		
		for(int i = 0; i < Board.BOARD_SIZE; i++){
			gameBoard.placePiece(new Pawn(PlayerColor.BLACK, new Square(i, 6), gameBoard));
			gameBoard.placePiece(new Pawn(PlayerColor.WHITE, new Square(i, 1), gameBoard));
		}
		gameOver = false;
	}
	
	public Game(){
		System.out.println(gameBoard);
		whitePlayer = new Player(PlayerColor.WHITE, gameBoard);
		blackPlayer = new Player(PlayerColor.BLACK, gameBoard);
		begin();
	}
	
	private void begin(){
		while(!gameOver){
			whitePlayer.takeTurn();
			System.out.println(gameBoard);
			blackPlayer.takeTurn();
			System.out.println(gameBoard);
		}
	}
}