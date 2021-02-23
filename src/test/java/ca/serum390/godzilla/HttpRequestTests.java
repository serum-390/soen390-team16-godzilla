package ca.serum390.godzilla;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ca.serum390.godzilla.api.routers.ApiRouter.ALL_GOOD_IN_THE_HOOD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import ca.serum390.godzilla.api.routers.ApiRouter;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ApiRouter router;

	/**
	 * Test that the Spring Boot context can load
	 *
	 * @throws Exception
	 */
	@Test
	void contextLoads() throws Exception {
		assertNotNull(router);
	}

	/**
	 * Pings the root url for the app, expecting it
	 */
	@Test
	void homePageLoads() {
		assertThat(
			restTemplate.getForObject("http://localhost:" + port + "/api/healthcheck", String.class)
		).contains(ALL_GOOD_IN_THE_HOOD);
	}
}
