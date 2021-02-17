package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private UserRepository usersRepository;

	/**
	 * This method returns all users
	 * @return List<Iterable> the User
	 */
	@GetMapping
	public Iterable findAll() {
		return usersRepository.findAll();
	}

	/**
	 * This method returns all users by username
	 * @param username: author of the user (String)
	 * @return  {@link User}
	 */
	@GetMapping("/name/{name}")
	public User findByName(@PathVariable String username) {
		return usersRepository.findByUsername(username)
				.orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
	}

	/**
	 * This method is to create a user
	 * @param user:  user (Object)
	 * @return {@link Book}
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User users) {
		return usersRepository.save(users);
	}
	/**
	 * This method is to delete a user
	 * @param id: id of the user (Long)
	 * @return void
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		usersRepository.deleteById(id);
	}

	/**
	 * This method is to update a user
	 * @param user: user (Object)
	 * @param id: id of the user (Long)
	 * @return {@link User}
	 */
	@PutMapping("/{id}")
	public User updateBook(@RequestBody User users, @PathVariable Long id) {
		if (users.getId() != id) {
			throw new BookIdMismatchException(NotificationCode.MISMATCH);
		}
		usersRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
		return usersRepository.save(users);
	}
}
