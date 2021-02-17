package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping
	public Iterable findAll() {
		return bookRepository.findAll();
	}

	@GetMapping("/author/{author}")
	public Book findByAuthor(@PathVariable String author) {
		return bookRepository.findByAuthor(author)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		if(bookRepository.findByAuthor(book.getIsbn()).isPresent()){
			throw new BookAlreadyOwnedException(NotificationCode.BOOK_ALREADY_OWNED);
		}
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
		bookRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (book.getId() != id) {
			throw new BookIdMismatchException(NotificationCode.MISMATCH);
		}
		bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
		return bookRepository.save(book);
	}

}
