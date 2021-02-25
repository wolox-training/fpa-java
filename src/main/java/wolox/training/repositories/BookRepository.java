package wolox.training.repositories;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	Optional<Book> findByAuthor(String author);

	Optional<Book> findByIsbn(String author);

	@Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher) and (:genre is null or b"
			+ ".genre = :genre) and (:year is null or b.year = :year)")
	List<Book> findByPublisherAndGenreAndYear(@Param(value = "publisher") String publisher,
			@Param(value = "genre") String genre, @Param(value = "year") String year);
}
