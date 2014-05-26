package model;

public enum PieceType {
	KING('K', 500){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new King(color, location, context);
		}
	},
	QUEEN('Q', 9){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new Queen(color, location, context);
		}
	},
	BISHOP('B', 3){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new Bishop(color, location, context);
		}
	},
	KNIGHT('N', 3){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new Knight(color, location, context);
		}
	},
	ROOK('R', 5){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new Rook(color, location, context);
		}
	},
	PAWN('P', 1){ 
		public Piece makePiece(PlayerColor color, Square location, Board context){
			return new Pawn(color, location, context);
		}
	};
	private char key;
	private int value;
	private PieceType(char key, int value){
		this.key = key;
		this.value = value;
	};
	public char getKey(){ return key; }
	public int getValue(){ return value; }
	public abstract Piece makePiece(PlayerColor color, Square location, Board context);
}
