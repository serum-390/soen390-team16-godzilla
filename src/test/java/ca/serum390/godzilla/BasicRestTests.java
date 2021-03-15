package ca.serum390.godzilla;

import static ca.serum390.godzilla.api.routers.ApiRouter.ALL_GOOD_IN_THE_HOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.api.routers.ApiRouter;

/**
 * Tests: /api/healthcheck, /login
 */
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BasicRestTests {

    @Autowired
	ApiRouter router;

	WebTestClient client;
    final String HEALTH_CHECK_URI = "/api/healthcheck";

    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider restDocProvider)  {
        this.client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocProvider))
                .build();
    }

	/**
	 * Test that the Spring Boot context can load
	 *w
	 * @throws Exception
	 */
	@Test
	void contextLoadsTest() throws Exception {
		assertNotNull(router);
	}

	/**
     * <b>Tests:</b> <code>GET /api/healthcheck</code>
     * <p>
	 * Pings the root url for the app, expecting it
	 */
	@Test
	@WithMockUser("test")
	void healthCheckTest() {
        client.get()
                .uri(HEALTH_CHECK_URI)
                .accept(TEXT_PLAIN)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document(HEALTH_CHECK_URI.replaceAll("^/", "")))
                .consumeWith(body -> assertThat(body.getResponseBodyContent())
                    .asString()
                    .isEqualTo(ALL_GOOD_IN_THE_HOOD));
	}

    /**
     * <b>Tests:</b> <code>GET /api/healthcheck</code>
     * <p>
     * Attempts to ping the API without being authenticated
     */
	@Test
	void noAuthTest() {
		client.get()
			  .uri("/")
			  .exchange()
			  .expectStatus()
			  .isEqualTo(401)
              .expectBody()
              .isEmpty();
	}

    /**
     * <b>Tests:</b> <code>POST /login</code>
     *
     * <p>
     *
     * Attempts to login with some test credentials and evaluates the session
     * cookie returned
     */
    @Test
    void loginTest() {

        final String cookiePath = "/";
        final String uuidPattern = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";
        final String sessionCookie = "SESSION";

        client.post()
                .uri("/login")
                .body(fromFormData("username", "test").with("password", "demo"))
                .exchange()
                .expectStatus()
                .isEqualTo(302)
                .expectCookie().httpOnly(sessionCookie, true)
                .expectCookie().path(sessionCookie, cookiePath)
                .expectCookie().sameSite(sessionCookie, "Lax")
                .expectCookie().value(sessionCookie, matchesRegex(uuidPattern))
                .expectBody()
                .consumeWith(document("login"));
    }
}
