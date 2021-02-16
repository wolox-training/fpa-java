package wolox.training.exceptions;

public class DataNotFoundException extends BookException {

	public DataNotFoundException(NotificationCode notificationCode) {
		super(notificationCode.getMessage(), notificationCode);

	}
}
