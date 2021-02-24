package wolox.training.models;

import com.sun.istack.NotNull;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@ApiModel(description = "Books from the OpenLibrary")
@RequiredArgsConstructor
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ApiModelProperty("The Book genre: could be horror, comedy etc.")
	private String genre;

	@NotNull
	@Column(nullable = false)
	private String author;

	@NotNull
	@Column(nullable = false)
	private String image;

	@NotNull
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String subtitle;

	@NotNull
	@Column(nullable = false)
	private String publisher;

	@NotNull
	@Column(nullable = false)
	private String year;

	@NotNull
	@Column(nullable = false)
	private Integer pages;

	@NotNull
	@Column(nullable = false)
	private String isbn;

	@ManyToMany(mappedBy = "books")
	private List<User> users = new LinkedList<>();

}
