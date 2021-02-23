package wolox.training.external.delegate;

import org.springframework.stereotype.Component;
import wolox.training.external.dto.BookInfoDto;
import wolox.training.external.feing.OpenLibraryFeingClient;

@Component
public class OpenLibreryDelegate {

	private static final String FORMAT = "json";
	private static final String JSCMD = "data";
	private final OpenLibraryFeingClient openLibraryFeingClient;

	public OpenLibreryDelegate(OpenLibraryFeingClient openLibraryFeingClient) {
		this.openLibraryFeingClient = openLibraryFeingClient;
	}

	public BookInfoDto findBookByIsbn(String isbn) {
		return openLibraryFeingClient.findAllBooksByIsbn(isbn,FORMAT,JSCMD);
	}
}
