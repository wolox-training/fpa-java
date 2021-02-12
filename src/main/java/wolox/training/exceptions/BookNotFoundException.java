package wolox.training.exceptions;

public class BookNotFoundException extends BookException {

	public BookNotFoundException(NotificationCode notificationCode) {
		super(notificationCode.getMessage(), notificationCode);

	}
}
