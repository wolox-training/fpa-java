package wolox.training.external.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class Isbn {

	private String title;
	private String subtitle;
	private List<PublisherDto> publishers;
	private  String publish_date;
	private String number_of_pages;
	private List<AuthorDto> authors;
}
