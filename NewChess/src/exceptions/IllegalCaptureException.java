package exceptions;

public class IllegalCaptureException extends InvalidMoveException {
	private static final long serialVersionUID = 1L;

	public IllegalCaptureException(){
		super();
		defaultMessage = "Cannot capture friendly piece";
	}
	@Override
	public String getMessage(){
		return super.getMessage() + defaultMessage;
	}
}
