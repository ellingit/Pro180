package exceptions;

public class InvalidMoveException extends Exception {
	private static final long serialVersionUID = 1L;
	protected static String defaultMessage = "Invalid Move";

	public InvalidMoveException(){
		super(defaultMessage);
	}
}
