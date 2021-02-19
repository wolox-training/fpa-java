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
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void whenCreateUser_thenFindAllUsers() throws Exception {
		User user = new User();
		user.setName("manuel");
		user.setUsername("USERNAME1");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		userRepository.save(user);
		mockMvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].username", is("USERNAME1")));
	}

	@Test
	public void whenCreateUser_thenFindByUsername() throws Exception {
		User user = new User();
		user.setName("manuel");
		user.setUsername("USERNAME2");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		userRepository.save(user);

		mockMvc.perform(get("/api/users/username/{username}", "USERNAME2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.username", is("USERNAME2")));
	}

	@Test
	public void whenCreateUser_thenStatusIs201() throws Exception {
		User user = new User();
		user.setName("manuel");
		user.setId(5l);
		user.setUsername("USERNAME3");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(user))).andExpect(status().isCreated());
	}
	@Test
	public void whenCreateUser_thenDeleteUserById() throws Exception {
		User user = new User();
		user.setName("manuel");
		user.setUsername("USERNAME4");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		User userSave = userRepository.save(user);
		mockMvc.perform(delete("/api/users/{id}", userSave.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	public void whenCreateUser_thenUpdateUserById() throws Exception {
		User user = new User();
		user.setName("manuel");
		user.setUsername("USERNAME4");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		User userSave = userRepository.save(user);
		userSave.setName("marcelo");

		mockMvc.perform(put("/api/users/{id}", userSave.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("marcelo")));
	}
}
