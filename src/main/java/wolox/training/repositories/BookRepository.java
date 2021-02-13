package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import wolox.training.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	Optional<Book> findByAuthor(String author);

}
