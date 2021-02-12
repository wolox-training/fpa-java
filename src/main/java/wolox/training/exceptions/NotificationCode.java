package wolox.training.exceptions;

public enum NotificationCode {
	DATA_NOT_FOUND("Data not found", "IN_ERR_NF"),
	MISMATCH("Book Id Mismatch","IN_ERR_MM");


	private String apiCode;
	private String message;

	NotificationCode(String message, String apiCode) {
		this.apiCode = apiCode;
		this.message = message;
	}

	public String getApiCode() {
		return apiCode;
	}

	public String getMessage() {
		return message;
	}
}
