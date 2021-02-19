package wolox.training.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	private Book bookBuilder;

	@BeforeEach
	public void init() {
		bookBuilder = new Book();
		bookBuilder.setGenre("M");
		bookBuilder.setImage("xxxx");
		bookBuilder.setTitle("avengers");
		bookBuilder.setPublisher("NA");
		bookBuilder.setYear("1990");
		bookBuilder.setPages(1);
		bookBuilder.setSubtitle("NA");
	}

	@Test
	public void whenCreateBook_thenFindAllUsers() throws Exception {
		bookBuilder.setAuthor("author1");
		bookBuilder.setIsbn("isbn1");
		bookRepository.save(bookBuilder);

		mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].author", is("author1")));
	}

	@Test
	public void whenCreateBook_thenFindByAuthor() throws Exception {

		bookBuilder.setAuthor("author2");
		bookBuilder.setIsbn("isbn2");
		bookRepository.save(bookBuilder);

		mockMvc.perform(get("/api/books/author/{author}", "author2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.isbn", is("isbn2")));
	}

	@Test
	public void whenCreateBook_thenStatusIs201() throws Exception {
		bookBuilder.setAuthor("author4");
		bookBuilder.setIsbn("isbn3");
		bookBuilder.setId(1l);
		mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.asJsonString(bookBuilder)))
				.andExpect(status().isCreated());
	}

	@Test
	public void whenCreateBook_thenDeleteBookById() throws Exception {
		bookBuilder.setAuthor("author4");
		bookBuilder.setIsbn("isbn4");

		Book book = bookRepository.save(bookBuilder);
		mockMvc.perform(delete("/api/books/{id}", book.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void whenCreateBook_thenUpdateBookById() throws Exception {
		bookBuilder.setAuthor("author4");
		bookBuilder.setIsbn("isbn4");

		Book bookSave = bookRepository.save(bookBuilder);
		bookSave.setAuthor("marcelo");

		mockMvc.perform(put("/api/books/{id}", bookSave.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(bookSave))).andExpect(status().isOk())
				.andExpect(jsonPath("$.author", is("marcelo")));
	}

}
