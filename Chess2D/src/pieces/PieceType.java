package pieces;

public enum PieceType {
	KING('K'){ 
		public Piece makePiece(boolean isWhite){
			return new King(isWhite);
		}
	},
	QUEEN('Q'){ 
		public Piece makePiece(boolean isWhite){
			return new Queen(isWhite);
		}
	},
	BISHOP('B'){ 
		public Piece makePiece(boolean isWhite){
			return new Bishop(isWhite);
		}
	},
	KNIGHT('N'){ 
		public Piece makePiece(boolean isWhite){
			return new Knight(isWhite);
		}
	},
	ROOK('R'){ 
		public Piece makePiece(boolean isWhite){
			return new Rook(isWhite);
		}
	},
	PAWN('P'){ 
		public Piece makePiece(boolean isWhite){
			return new Pawn(isWhite);
		}
	};
	private char key;
	private PieceType(char key){
		this.key = key;
	};
	public char getKey(){ return key; }
	public abstract Piece makePiece(boolean isWhite);
}
