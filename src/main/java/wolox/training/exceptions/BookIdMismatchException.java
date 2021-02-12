package wolox.training.exceptions;

public class BookIdMismatchException extends BookException {

	public BookIdMismatchException(NotificationCode notificationCode) {
		super(notificationCode.getMessage(), notificationCode);

	}
}
