package wolox.training.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.JsonUtil;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;
	private User user ;
	private LocalDateTime localDateTime = LocalDateTime.now();

	@BeforeEach
	public void init (){
		user = new User();
		user.setId(1l);
		user.setName("manuel");
		user.setUsername("USERNAME" + localDateTime);
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		when(userRepository.save(any())).thenReturn(user);
	}

	@Test
	public void whenCreateUser_thenFindAllUsers() throws Exception {
		when(userRepository.findAll()).thenReturn(Arrays.asList(user));
		mockMvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].username", is(user.getUsername())));
	}

	@Test
	public void whenCreateUser_thenFindByUsername() throws Exception {
		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
		mockMvc.perform(get("/api/users/username/{username}", user.getUsername()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.username", is(user.getUsername())));
	}

	@Test
	public void whenCreateUser_thenStatusIs201() throws Exception {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(user))).andExpect(status().isCreated())
					.andExpect(jsonPath("$.username", is(user.getUsername())));
	}
	@Test
	public void whenCreateUser_thenDeleteUserById() throws Exception {
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
			mockMvc.perform(delete("/api/users/{id}", user.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void whenCreateUser_thenUpdateUserById() throws Exception {
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		user.setName("marcelo");
		mockMvc.perform(put("/api/users/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("marcelo")));
	}
}
