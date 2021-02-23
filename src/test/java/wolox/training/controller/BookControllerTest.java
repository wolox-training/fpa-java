package wolox.training.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.JsonUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	private Book bookBuilder;

	LocalDateTime localDateTime = LocalDateTime.now();

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
		bookBuilder.setAuthor("author1" + localDateTime);
		bookBuilder.setIsbn("isbn1" + localDateTime);
		when(bookRepository.findById(any())).thenReturn(Optional.of(bookBuilder));
	}

	@Test
	public void whenCreateBook_thenFindAllUsers() throws Exception {
		when(bookRepository.findAll()).thenReturn(Arrays.asList(bookBuilder));
		mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].author", is(bookBuilder.getAuthor())));
	}

	@Test
	public void whenCreateBook_thenFindByAuthor() throws Exception {
		when(bookRepository.findByAuthor(bookBuilder.getAuthor())).thenReturn(Optional.of(bookBuilder));
		mockMvc.perform(
				get("/api/books/author/{author}", bookBuilder.getAuthor()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.isbn", is(bookBuilder.getIsbn())));
	}

	@Test
	public void whenCreateBook_thenStatusIs201() throws Exception {
		bookBuilder.setId(1l);
		when(bookRepository.save(any())).thenReturn(bookBuilder);
		mockMvc.perform(
				post("/api/books").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.asJsonString(bookBuilder)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.isbn", is(bookBuilder.getIsbn())));
	}

	@Test
	public void whenCreateBook_thenDeleteBookById() throws Exception {
		bookBuilder.setId(1l);
		mockMvc.perform(delete("/api/books/{id}", bookBuilder.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void whenCreateBook_thenUpdateBookById() throws Exception {
		bookBuilder.setId(1l);
		when(bookRepository.save(any())).thenReturn(bookBuilder);
		bookBuilder.setAuthor("marcelo");

		mockMvc.perform(put("/api/books/{id}", bookBuilder.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(bookBuilder))).andExpect(status().isOk())
				.andExpect(jsonPath("$.author", is("marcelo")));
	}

}
