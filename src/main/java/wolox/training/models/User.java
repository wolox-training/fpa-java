package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.NotificationCode;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate birthdate;


	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Book> books = new LinkedList<>();

	public User() {
	}

	public User(Long id, String username, String name, LocalDate birthdate, List<Book> books) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.birthdate = birthdate;
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Book> addBook(Book bookCurrent) {
		if (books.stream().filter(book -> book.getIsbn().equals(bookCurrent.getIsbn())).findFirst().isPresent()) {
			throw new BookAlreadyOwnedException(NotificationCode.BOOK_ALREADY_OWNED);
		}
		books.add(bookCurrent);
		return books;

	}

	public List<Book> deleteBook(String isbn) {
		books.removeIf(book -> book.getIsbn().equals(isbn));
		return books;
	}
}
