package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;
import wolox.training.models.Users;

public interface UsersReisitory extends CrudRepository<Book, Long> {

	Optional<Users> findByName(String name);

}
