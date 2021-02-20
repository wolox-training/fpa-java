package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wolox.training.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User userOneTest = new User(1l, "PACHECO", "Francisco Javier", LocalDate.now(), Arrays.asList());

	private User userTwoTest = new User();

	@Test
	public void whenCreateUser_thenUserIsPersisted() {
		//Act
		userRepository.save(userOneTest);
		User user = userRepository.findByUsername("PACHECO").orElse(new User());
		//Assert
		assertThat(user.getUsername().equals(userOneTest.getUsername())).isTrue();
		assertThat(user.getName().equals(userOneTest.getName())).isTrue();
		assertThat(user.getBooks().size() == (userOneTest.getBooks().size())).isTrue();
	}

	@Test
	public void whenCreateUserWithoutUsername_thenThrowException() {
		String message = "username field cannot be null";
		//Act - Assert
		assertThatThrownBy(() -> Preconditions.checkNotNull(userTwoTest.getUsername(), message))
				.isInstanceOf(NullPointerException.class).hasMessage(message).hasNoCause();
	}
}
