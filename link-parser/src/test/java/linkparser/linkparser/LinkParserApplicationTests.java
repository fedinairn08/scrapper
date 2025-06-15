package linkparser.linkparser;

import linkparser.linkparser.model.GitHubResult;
import linkparser.linkparser.model.StackOverflowResult;
import linkparser.linkparser.service.LinkParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LinkParserApplicationTests {

	@Autowired
	private LinkParser linkParser;

	@Test
	public void parseValidGithubLink() throws URISyntaxException {
		URI uri = new URI("https://github.com/fedinairn08/scrapper");

		GitHubResult expected = new GitHubResult(uri, "fedinairn08", "scrapper");
		GitHubResult actual = (GitHubResult) linkParser.parseUrl(uri);

		assertNotNull(actual);
		assertEquals(expected.user(), actual.user());
		assertEquals(expected.repo(), actual.repo());
    }

	@Test
	public void parseValidStackOverflowLink() throws URISyntaxException {
		URI uri = new URI("https://stackoverflow.com/questions/927358/how-do-i-undo-the-most-recent-local-commits-in-git");

		StackOverflowResult expected = new StackOverflowResult(uri, 927358L);
		StackOverflowResult actual = (StackOverflowResult) linkParser.parseUrl(uri);

		assertNotNull(actual);
		assertEquals(expected.questionId(), actual.questionId());
	}

	@Test
	public void parseInvalidGithubLink() throws URISyntaxException {
		URI uri = new URI("https://github.com/delete");

		assertNull(linkParser.parseUrl(uri));
	}

	@Test
	public void parseInvalidStackOverflowLink() throws URISyntaxException {
		URI uri = new URI("https://stackoverflow.com/");

		assertNull(linkParser.parseUrl(uri));
	}
}
