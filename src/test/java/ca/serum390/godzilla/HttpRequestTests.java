package ca.serum390.godzilla;

import static ca.serum390.godzilla.api.routers.ApiRouter.ALL_GOOD_IN_THE_HOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.api.routers.ApiRouter;

@AutoConfigureWebClient
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ApiRouter router;

	@Autowired
	private WebTestClient client;

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
	@WithMockUser("demo")
	@Test
	void healthCheckTest() {
		assertThat(
			restTemplate.getForObject("http://localhost:" + port + "/api/healthcheck", String.class)
		).contains(ALL_GOOD_IN_THE_HOOD);
	}

	@Test
	void healthCheckNoAuthTest() {
		client.get()
			  .uri("http://localhost:" + port + "/api/healthcheck")
			  .exchange()
			  .expectStatus()
			  .isEqualTo(401);
	}
}
