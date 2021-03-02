package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	Optional<Book> findByAuthor(String author);

	Optional<Book> findByIsbn(String author);

	@Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher) and (:genre is null or b"
			+ ".genre = :genre) and (:year is null or b.year = :year)")
	Page<Book> findByPublisherAndGenreAndYear(@Param(value = "publisher") String publisher,
			@Param(value = "genre") String genre, @Param(value = "year") String year, Pageable pageable);

	@Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher) and (:genre is null or b"
			+ ".genre = :genre) and (:year is null or b.year = :year) and (:author is null or b.author = :author) and"
			+ "(:image is null or b.image = :image) and (:title is null or b.title = :title) and (:subtitle is null "
			+ "or b.subtitle =:subtitle) and  (:pages is null or b.pages = :pages) and (:isbn is null or b.isbn = "
			+ ":isbn)")
	Page<Book> findAll(@Param(value = "publisher") String publisher, @Param(value = "genre") String genre,
			@Param(value = "year") String year, @Param("author") String author, @Param("image") String image,
			@Param("title") String title, @Param("subtitle") String subtitle, @Param("pages") Integer page,
			@Param("isbn") String isbn, Pageable pageable);
}
