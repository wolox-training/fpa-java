package wolox.training.client.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Component;
import wolox.training.client.dto.BookInfoDto;
import wolox.training.client.feing.OpenLibraryFeingClient;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Component
public class OpenLibraryDelegate {

	private static final String FORMAT = "json";
	private static final String JSCMD = "data";
	private static final String ISBN_PARAM = "ISBN:";

	private final ObjectMapper objectMapper;
	private final BookRepository bookRepository;
	private final OpenLibraryFeingClient openLibraryFeingClient;

	public OpenLibraryDelegate(BookRepository bookRepository, ObjectMapper objectMapper,
			OpenLibraryFeingClient openLibraryFeingClient) {
		this.bookRepository = bookRepository;
		this.objectMapper = objectMapper;
		this.openLibraryFeingClient = openLibraryFeingClient;
	}

	public Optional<Book> findBookByIsbn(String isbn) {
		StringBuilder isbnParam = new StringBuilder();
		isbnParam.append(ISBN_PARAM);
		isbnParam.append(isbn);

		HashMap<String, Object> bookInfoResponse = openLibraryFeingClient
				.findAllBooksByIsbn(isbnParam.toString(), FORMAT, JSCMD);

		if (!bookInfoResponse.isEmpty()) {
			BookInfoDto bookInfoDto = objectMapper
					.convertValue(bookInfoResponse.get(isbnParam.toString()), BookInfoDto.class);

			return saveBookOpenLibrary(bookInfoDto, isbn);
		}
		throw new DataNotFoundException(NotificationCode.BOOK_DATA_NOT_FOUND);
	}

	private Optional<Book> saveBookOpenLibrary(BookInfoDto bookInfoDto, String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setAuthor(bookInfoDto.getAuthors().get(0).getName());
		book.setSubtitle(bookInfoDto.getSubtitle());
		book.setPages(Integer.parseInt(bookInfoDto.getNumberOfPages()));
		book.setPublisher(bookInfoDto.getPublishers().get(0).getName());
		book.setYear("1990");
		book.setImage("img");
		book.setGenre("M");
		book.setTitle(bookInfoDto.getTitle());
		if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new BookAlreadyOwnedException(NotificationCode.BOOK_ALREADY_OWNED);
		}
		return Optional.ofNullable(bookRepository.save(book));
	}
}
