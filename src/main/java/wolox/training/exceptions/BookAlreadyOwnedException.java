package wolox.training.exceptions;

public class BookAlreadyOwnedException extends BookException {

	public BookAlreadyOwnedException(NotificationCode notificationCode) {
		super(notificationCode.getMessage(), notificationCode);

	}

}
