package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthdate;


	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "SHARE", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, updatable = false)}, inverseJoinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false, updatable = false)})
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

	public List<Book> addBook(Book book) {
		return Collections.singletonList(book);
	}

	public List<Book> deleteBook(List<Book> books, String isbn) {
		books.removeIf(book -> book.getIsbn().equals(isbn));
		return books;
	}
}
