package wolox.training.repositories;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE (CAST(:startDate AS java.time.LocalDateTime) IS NULL OR (u.birthdate >= "
			+ ":startDate) AND (CAST(:endDate AS java.time.LocalDateTime) IS NULL OR(u.birthdate <= :endDate))) "
			+ "and  (:name is null or (lower(u.name) like concat('%', lower(CAST(:name as java.lang.String)),'%')))")
	Page<User> findByBirthdateBetweenAndNameContainingIgnoreCase(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, String name, Pageable pageable);

}
