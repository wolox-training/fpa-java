package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import wolox.training.utils.ValidationPreconditionUtil;

@Entity
@ApiModel(description = "Books from the OpenLibrary")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ApiModelProperty("The Book genre: could be horror, comedy etc.")
	private String genre;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String subtitle;

	@Column(nullable = false)
	private String publisher;

	@Column(nullable = false)
	private String year;

	@Column(nullable = false)
	private Integer pages;

	@Column(nullable = false)
	private String isbn;
	@ManyToMany(mappedBy = "books")
	private List<User> users = new LinkedList<>();

	public Book() {
	}

	public Book(Long id, String genre, String author, String image, String title, String subtitle, String publisher,
			String year, Integer pages, String isbn, List<User> users) {
		this.id = id;
		this.genre = genre;
		this.author = author;
		this.image = image;
		this.title = title;
		this.subtitle = subtitle;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;
		this.isbn = isbn;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(id,"id");
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(author,"author");
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(image,"image");
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(title,"title");
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(subtitle,"subtitle");
		this.subtitle = subtitle;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(publisher,"publisher");
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(year,"year");
		this.year = year;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(pages,"pages");
		this.pages = pages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		ValidationPreconditionUtil.validateFieldCheckNotNull(isbn,"isbn");
		this.isbn = isbn;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


}
