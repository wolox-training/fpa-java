package wolox.training.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private UserRepository usersRepository;

	/**
	 * This method returns all users
	 *
	 * @return List<Iterable> the User
	 */
	@GetMapping
	public Iterable findAll() {
		return usersRepository.findAll();
	}

	/**
	 * This method returns all users by username
	 *
	 * @param username: author of the user (String)
	 *
	 * @return {@link User}
	 */
	@GetMapping("/username/{username}")
	public User findByUsername(@PathVariable String username) {
		return usersRepository.findByUsername(username)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.USER_DATA_NOT_FOUND));
	}

	/**
	 * This method is to create a user
	 *
	 * @param user: user (Object)
	 *
	 * @return {@link User}
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {
		return usersRepository.save(user);
	}

	/**
	 * This method is to delete a user
	 *
	 * @param id: id of the user (Long)
	 *
	 * @return void
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		usersRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.USER_DATA_NOT_FOUND));
		usersRepository.deleteById(id);
	}

	/**
	 * This method is to update a user
	 *
	 * @param user: user (Object)
	 * @param id:   id of the user (Long)
	 *
	 * @return {@link User}
	 */
	@PutMapping("/{id}")
	public User update(@RequestBody User user, @PathVariable Long id) {
		if (user.getId() != id) {
			throw new IdMismatchException(NotificationCode.USER_MISMATCH);
		}
		usersRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.USER_DATA_NOT_FOUND));
		return usersRepository.save(user);
	}

	/**
	 * This method is to delete a books to user
	 *
	 * @param userId: userId of the user (Long)
	 *
	 * @return void
	 */
	@DeleteMapping("/id/{userId}")
	public User deleteBookToUser(@RequestBody List<Book> books, @PathVariable Long userId) {
		User user = usersRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.USER_DATA_NOT_FOUND));
		books.forEach(book -> {
			user.deleteBook(book.getIsbn());
		});
		return usersRepository.save(user);
	}

	/**
	 * This method is to create a books to user
	 *
	 * @param books:   books (List)
	 * @param userId:. id of the user (Long)
	 *
	 * @return {@link User}
	 */
	@PostMapping("/id/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public User addBookToUser(@RequestBody List<Book> books, @PathVariable Long userId) {
		User user = usersRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.USER_DATA_NOT_FOUND));
		books.forEach(book -> {
			user.addBook(book);
		});
		return usersRepository.save(user);
	}

	/**
	 * This method is to create a books to user
	 *
	 * @param startDate: Birthdate start (LocalDate)
	 * @param endDate:   Birthdate end (LocalDate)
	 * @param name:      name User (String)
	 *
	 * @return {@link List<User>}
	 */
	@GetMapping("/date")
	public Page<User> findByBirthdateBetweenAndNameContainingIgnoreCase(
			@RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) LocalDate startDate,
			@DateTimeFormat(pattern = DATE_FORMAT) @RequestParam(required = false) LocalDate endDate,
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") Integer numberParam,
			@RequestParam(defaultValue = "200") Integer pageNumParam) {

		return usersRepository.findByBirthdateBetweenAndNameContainingIgnoreCase(startDate, endDate, name,
				PageRequest.of(numberParam, pageNumParam));

	}
}
