package exceptions;

public class PathObscuredException extends InvalidMoveException {
	private static final long serialVersionUID = 1L;

	public PathObscuredException(){
		super();
		defaultMessage = "Path is Blocked";
	}
	@Override
	public String getMessage(){
		return super.getMessage() + defaultMessage;
	}
}
