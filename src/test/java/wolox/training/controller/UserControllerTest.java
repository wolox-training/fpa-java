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

	private static final String USERNAME = "PACHECO";
	private static  final Long ID_USER = 1l;

	private  User userBuilder(){
		User user = new User();
		user.setId(ID_USER);
		user.setUsername(USERNAME);
		user.setName("manuel");
		user.setBirthdate(LocalDate.of(2020,1,1));
		return user;
	}

	@Test
	public void findAll() throws Exception {
		userRepository.save(userBuilder());

		mockMvc.perform(get("/api/users")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
							.andExpect(jsonPath("$[0].username", is(USERNAME)));
	}

	@Test
	public void findByUsername() throws Exception {
		userRepository.save(userBuilder());

		mockMvc.perform(get("/api/users/username/{username}", USERNAME)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.username", is(USERNAME)));
	}

	@Test
	public  void createUser() throws Exception {
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.asJsonString(userBuilder())))
				.andExpect(status().isCreated());
	}
	@Test
	public void deleteUser() throws Exception {
		userRepository.save(userBuilder());

		mockMvc.perform(delete("/api/users/{id}", ID_USER)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void updateUser() throws Exception {
		User user = userRepository.save(userBuilder());
		user.setName("Marcelo");

		mockMvc.perform(put("/api/users/{id}",ID_USER)
				.contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.asJsonString(user)))
						.andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Marcelo")));;

	}


}
