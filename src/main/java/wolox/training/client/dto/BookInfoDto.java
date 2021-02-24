package wolox.training.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookInfoDto {
	private String isbn;
	private String title;
	private String subtitle;
	private List<PublisherDto> publishers;
	private  String publishDate;
	private String numberOfPages;
	private List<AuthorDto> authors;

}
