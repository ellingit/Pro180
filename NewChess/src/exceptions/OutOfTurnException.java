package exceptions;

public class OutOfTurnException extends InvalidMoveException {
	private static final long serialVersionUID = 1L;

	public OutOfTurnException(){
		super();
		defaultMessage = "Move is Out of Turn";
	}
	@Override
	public String getMessage(){
		return super.getMessage() + defaultMessage;
	}
}
