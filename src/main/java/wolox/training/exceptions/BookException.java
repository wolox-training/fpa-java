package wolox.training.exceptions;

public class BookException extends RuntimeException {

	private final NotificationCode notificationCode;

	public BookException(String message, NotificationCode notificationCode) {

		super(message);
		this.notificationCode = notificationCode;

	}

	public BookException(String message, NotificationCode notificationCode, Throwable cause) {
		super(message, cause);
		this.notificationCode = notificationCode;

	}

	public NotificationCode getNotificationCode() {
		return notificationCode;
	}
}
