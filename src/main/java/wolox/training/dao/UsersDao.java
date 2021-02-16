package wolox.training.dao;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wolox.training.exceptions.DataNotFoundException;
import wolox.training.exceptions.NotificationCode;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;

@Component
public class UsersDao {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BookRepository bookRepository;

	@Transactional
	public Users save(Users usersCurrent) {
		Users usersSave = usersRepository.save(usersCurrent);
		usersSave.setBooks(saveBook(usersCurrent.getBooks(), usersSave));
		return usersSave;
	}

	public Optional<Users> findByName(String name) {
		return usersRepository.findByName(name);
	}

	private List<Book> saveBook(List<Book> books, Users usersSave) {
		if (!books.isEmpty()) {
			books.forEach(book -> {
				book.setUsers(usersSave);
			});
			return (List<Book>) bookRepository.saveAll(books);
		} else {
			return books;
		}
	}

	public void deleteById(Long id) {
		usersRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NotificationCode.DATA_NOT_FOUND));
		usersRepository.deleteById(id);
	}

	public Optional<Users> findById(Long id) {
		return usersRepository.findById(id);
	}
}
