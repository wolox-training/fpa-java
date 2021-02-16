package wolox.training.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookException;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.DataNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({DataNotFoundException.class, BookAlreadyOwnedException.class})
	protected ResponseEntity<Object> handleAllHandledExceptions(BookException exception, WebRequest request) {
		return handleExceptionInternal(exception, exception.getNotificationCode().getMessage(), new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({BookIdMismatchException.class, ConstraintViolationException.class,
			DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}


}
