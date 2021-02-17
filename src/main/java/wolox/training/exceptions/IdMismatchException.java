package wolox.training.exceptions;

public class IdMismatchException extends BookException {

	public IdMismatchException(NotificationCode notificationCode) {
		super(notificationCode.getMessage(), notificationCode);

	}
}
