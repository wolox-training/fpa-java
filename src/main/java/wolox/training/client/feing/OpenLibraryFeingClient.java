package wolox.training.client.feing;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.placeholder.name}", url = "${feign.placeholder.rootUrl}")
public interface OpenLibraryFeingClient {

	@GetMapping(value = "${feign.placeholder.resources.book}", produces = APPLICATION_JSON_VALUE)
	HashMap<String,Object> findAllBooksByIsbn(@RequestParam("bibkeys") String isbn, @RequestParam String format,
			@RequestParam String jscmd);

}
