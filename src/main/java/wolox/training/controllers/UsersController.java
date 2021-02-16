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
import wolox.training.dao.UsersDao;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Users;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private UsersDao usersDao;

	@GetMapping("/name/{name}")
	public Users findByName(@PathVariable String name) {
		return usersDao.findByName(name).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Users create(@RequestBody Users users) {
		return usersDao.save(users);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		usersDao.deleteById(id);
	}

	@PutMapping("/{id}")
	public Users updateBook(@RequestBody Users users, @PathVariable Long id) {
		if (users.getId() != id) {
			throw new BookIdMismatchException(NotificationCode.MISMATCH);
		}
		usersDao.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
		return usersDao.save(users);
	}
}
