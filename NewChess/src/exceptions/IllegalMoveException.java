package exceptions;

public class IllegalMoveException extends InvalidMoveException {
	private static final long serialVersionUID = 1L;

	public IllegalMoveException(){
		super();
		defaultMessage = "Piece Cannot Move Like That";
	}
	@Override
	public String getMessage(){
		return super.getMessage() + defaultMessage;
	}
}
