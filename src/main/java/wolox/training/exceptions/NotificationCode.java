package wolox.training.exceptions;

public enum NotificationCode {
	BOOK_DATA_NOT_FOUND("Book Data not found", "IN_ERR_NF_BK"),
	USER_DATA_NOT_FOUND("User Data not found", "IN_ERR_NF_US"),
	BOOK_MISMATCH("Book Id Mismatch","IN_ERR_MM_BK"),
	USER_MISMATCH("User Id Mismatch","IN_ERR_MM_US"),
	BOOK_ALREADY_OWNED("book is already associated with this user","IN_ERR_AO");



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
