package wolox.training.external.feing;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wolox.training.external.dto.BookInfoDto;

@FeignClient(name = "${feign.placeholder.name}", url = "${feign.placeholder.rootUrl}")
public interface OpenLibraryFeingClient {

	@GetMapping(value = "${feign.placeholder.resources.albums}", produces = APPLICATION_JSON_VALUE)
	BookInfoDto findAllBooksByIsbn(@RequestParam("bibkeys") String isbn, @RequestParam String format,
			@RequestParam String jscmd);


}
