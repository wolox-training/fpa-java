package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
