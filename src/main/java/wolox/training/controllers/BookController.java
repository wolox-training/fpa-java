package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.client.delegate.OpenLibraryDelegate;
import wolox.training.client.dto.BookInfoDto;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private OpenLibraryDelegate openLibraryDelegate;


	/**
	 * This method returns all books
	 *
	 * @param name:  default response value(String)
	 * @param model: object to pass by attribute to html(Object)
	 *
	 * @return String returns default message
	 */
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	/**
	 * This method returns all books
	 *
	 * @return List<Iterable> the Book
	 */
	@GetMapping
	public Iterable findAll() {
		return bookRepository.findAll();
	}

	/**
	 * This method returns all books by author
	 *
	 * @param author: author of the book (String)
	 *
	 * @return {@link Book}
	 */

	@ApiOperation(value = "Giving and author the book", response = Book.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved book"),
			@ApiResponse(code = 401, message = "You are not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The Resource you were trying to reach is not found")})
	@GetMapping("/author/{author}")
	public Book findByAuthor(@PathVariable String author) {
		return bookRepository.findByAuthor(author)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.BOOK_DATA_NOT_FOUND));
	}

	/**
	 * This method is to create a book
	 *
	 * @param book: author of the book (Object)
	 *
	 * @return {@link Book}
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new BookAlreadyOwnedException(NotificationCode.BOOK_ALREADY_OWNED);
		}
		return bookRepository.save(book);
	}

	/**
	 * This method is to delete a book
	 *
	 * @param id: id of the book (Long)
	 *
	 * @return void
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.BOOK_DATA_NOT_FOUND));
		bookRepository.deleteById(id);
	}

	/**
	 * This method is to update a book
	 *
	 * @param book: author of the book (Object)
	 * @param id:   id of the book (Long)
	 *
	 * @return {@link Book}
	 */
	@PutMapping("/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book.getId() != id) {
			throw new IdMismatchException(NotificationCode.BOOK_MISMATCH);
		}
		bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.BOOK_DATA_NOT_FOUND));
		return bookRepository.save(book);
	}

	/**
	 * This method is to find a book by isbn
	 *
	 * @param isbn: author of the book (Object)
	 *
	 * @return {@link BookInfoDto}
	 */

	@GetMapping("/isbn/{isbn}")
	public ResponseEntity<Book> findBookByIsbn(@PathVariable String isbn) {
		return bookRepository.findByIsbn(isbn).map(book -> new ResponseEntity<>(book, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(openLibraryDelegate.findBookByIsbn(isbn), HttpStatus.CREATED));
	}

	/**
	 * This method is to find a book by publisher and genre and year
	 *
	 * @param publisher: publisher of the book (Object)
	 * @param genre:     genre of the book (Object)
	 * @param year:      year of the book (Object)
	 *
	 * @return {@link List<Book>}
	 */

	@GetMapping("/parameters")
	public List<Book> findByPublisherAndGenreAndYear(@RequestParam(required = false) String publisher,
			@RequestParam(required = false) String genre, @RequestParam(required = false) String year) {
		return bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);
	}

	/**
	 * This method is to find a book by all parameters
	 *
	 * @param publisher: publisher of the book (String)
	 * @param genre:     genre of the book (String)
	 * @param year:      year of the book (String)
	 * @param author:    author of the book   (String)
	 * @param image:     image of the book   (String)
	 * @param title:     title of the book   (String)
	 * @param subtitle:  subtitle of the book   (String)
	 * @param page:    	 page of the book   (Integer)
	 * @param author:    isbn of the book   (String)
	 *
	 * @return {@link List<Book>}
	 */
	@GetMapping("/all-parameters")
	public List<Book> findAll(@RequestParam(required = false) String publisher,
			@RequestParam(required = false) String genre, @RequestParam(required = false) String year,
			@RequestParam(required = false) String author, @RequestParam(required = false) String image,
			@RequestParam(required = false) String title, @RequestParam(required = false) String subtitle,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) String isbn) {
		return bookRepository.findAll(publisher, genre, year, author, image, title, subtitle, page, isbn);
	}

}
