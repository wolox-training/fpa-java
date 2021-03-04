package wolox.training.config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.http.Fault;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Optional;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

@Slf4j
@Configuration
@DependsOn(value = {"webServerFactoryCustomizerConfig"})
@RefreshScope
@ConditionalOnProperty(value = "wiremock.enabled", havingValue = "true")
public class LocalWireMockConfig {

	public static final String RESOURCE_OPEN_LIBRARY = "/api/books?bibkeys=ISBN:0385472579&format=json&jscmd=data";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	public static final String APPLICATION_JSON_HEADER = "application/json;charset=UTF-8";
	private static final String CLASS_RESOURCE = "classpath:/__files/";

	@Value("${wiremock.server.port}")
	private int wireMockPort;
	@Value("${wiremock.server.matchPort:''}")
	private String wireMockPortEnabled;

	/**
	 * WireMock server instance.
	 */
	private WireMockServer wireMockServer;


	private WireMockServer wiremockServerLocal() {

		log.info("----------Wiremock Init----------");
		boolean wireMockEnabled = Optional.ofNullable(System.getProperty("server.port"))
				.map(serverPort -> serverPort.equals(wireMockPortEnabled)).orElse(false);

		wireMockServer = new WireMockServer(options().port(wireMockPort).notifier(new Slf4jNotifier(false)));

		if (wireMockEnabled) {
			wireMockServer.start();
			log.info("----------Wiremock Started----------");
		}

		return wireMockServer;
	}

	/**
	 * Stops the wiremock server before destroy the bean. This approach complements the live
	 * reload.
	 */
	@PreDestroy
	public void preDestroy() {
		wireMockServer.stop();
	}

	@Bean
	public ApplicationRunner mockExternalService() {
		return args -> {
			wireMockServer = wiremockServerLocal();

			wireMockServer.stubFor(
					get(RESOURCE_OPEN_LIBRARY)
							.withQueryParam("bibkeys" , equalTo("ISBN:0385472579"))
							.withQueryParam("format",equalTo("json"))
							.withQueryParam("jscmd",equalTo("data"))
							.willReturn(aResponse()
									.withHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON_HEADER)
									.withBody(readFileToString(
											"json/open_library.json"))));
		};
	}
	private static String readFileToString(String path) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(CLASS_RESOURCE + path);
		return asString(resource);
	}

	private static String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
