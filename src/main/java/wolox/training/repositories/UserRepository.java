package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE (u.birthdate BETWEEN :startDate  AND :endDate) and (lower(u.name) like lower(concat('%', :name,'%')))")
	List<User> findByBirthdateBetweenAndNameContainingIgnoreCase(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("name") String name);

}
