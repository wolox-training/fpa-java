package wolox.training.repositories;

import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.Book;
import wolox.training.models.Users;

public interface UsersReisitory extends CrudRepository<Users, Long> {

	Optional<Users> findByName(String name);

}
