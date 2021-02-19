package wolox.training.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTest {

	private static final String ISBN = "12356";
	private static final Long ID_USER = 1l;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	private Book bookBuilder() {
		Book bookBuilder = new Book();
		bookBuilder.setId(ID_USER);
		bookBuilder.setGenre("M");
		bookBuilder.setImage("xxxx");
		bookBuilder.setTitle("cien anios de soledad");
		bookBuilder.setPublisher("NA");
		bookBuilder.setYear("1990");
		bookBuilder.setIsbn(ISBN);
		bookBuilder.setPages(1);
		bookBuilder.setSubtitle("NA");
		return bookBuilder;
	}

	@Test
	public void createBooks() throws Exception {
		Book book = bookBuilder();
		book.setAuthor("author4");
		book.setIsbn("554545");
		mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.asJsonString(book)))
				.andExpect(status().isCreated());
	}

	@Test
	public void findAll() throws Exception {
		Book book = bookBuilder();
		book.setAuthor("author1");
		bookRepository.save(book);

		mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].author", is("author1")));
	}

	@Test
	public void findByAuthor() throws Exception {
		Book book = bookBuilder();
		book.setAuthor("author2");
		bookRepository.save(book);

		mockMvc.perform(get("/api/books/author/{author}", "author2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.isbn", is(ISBN)));
	}
}
