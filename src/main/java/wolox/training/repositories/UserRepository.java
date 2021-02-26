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

	@Query("SELECT u FROM User u WHERE (CAST(:dtCancelInitial AS java.time.LocalDateTime) IS NULL OR (u.birthdate >= "
			+ ":dtCancelInitial AND u.birthdate <= :dtCancelFinal))  "
			+ "and  (:name is null or (lower(u.name) like concat('%', lower(CAST(:name as java.lang.String)),'%')))")

	List<User> findByBirthdateBetweenAndNameContainingIgnoreCase(@Param("dtCancelInitial") LocalDate startDate,
			@Param("dtCancelFinal") LocalDate endDate,  String name);

}
