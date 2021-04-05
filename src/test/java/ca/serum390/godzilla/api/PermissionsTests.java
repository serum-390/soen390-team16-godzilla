package ca.serum390.godzilla.api;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.Duration;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.config.StartupApplicationConfiguration;
import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.GodzillaUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class PermissionsTests {

    @MockBean
    GodzillaUserRepository repo;

    @MockBean
    StartupApplicationConfiguration config;

    @Autowired
    PasswordEncoder encoder;
    WebTestClient client;
    final String PERMISSIONS_URI = "/api/users/permissions/";

    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider provider) {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(provider))
                .build();

        mockStartupConfig();
    }


    @ParameterizedTest
    @WithMockUser("test")
    @ValueSource(ints = {1, 2, 3, 4})
    void getUserPermissionsByIdTest(int numberOfRoles) {
        var user = userFlux(1)
                .limitRequest(numberOfRoles)
                .last()
                .doOnNext(u -> when(repo
                    .findById(u.getId()))
                    .thenReturn(Mono.just(u)));

        verifyGetUserPermissions(
                numberOfRoles,
                user,
                u -> PERMISSIONS_URI + "?id=" + u.getId(),
                (u, repo) -> verify(repo).findById(u.getId()));
    }

    @ParameterizedTest
    @WithMockUser("Test")
    @MethodSource("badIdMethodSource")
    void getUserPermissionsBadIdTest(String id) {
        client.get()
                .uri(PERMISSIONS_URI + "?id=" + id)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @ParameterizedTest
    @WithMockUser("test")
    @ValueSource(ints = {1, 2, 3, 4})
    void getUserPermissionsByNameTest(int numberOfRoles) {
        var user = userFlux(1)
                .limitRequest(numberOfRoles)
                .last()
                .doOnNext(u -> when(repo
                    .findByUsername(u.getUsername()))
                    .thenReturn(Mono.just(u)));

        verifyGetUserPermissions(
                numberOfRoles,
                user,
                u -> PERMISSIONS_URI + "?name=" + u.getUsername(),
                (u, repo) -> verify(repo).findByUsername(u.getUsername()));
    }

    @Test
    @WithMockUser("test")
    void getUserPermissionsBadNameTest() {
        var user = userFlux(1)
                .limitRequest(1)
                .last()
                .doOnNext(u -> when(repo
                    .findByUsername(u.getUsername()))
                    .thenReturn(Mono.empty()));

        StepVerifier.create(user)
                .consumeNextWith(u -> client
                    .get()
                    .uri(PERMISSIONS_URI + "?name=" + u.getUsername())
                    .exchange()
                    .expectStatus()
                        .isNotFound()
                    .expectBody()
                        .consumeWith(document("api/users/permissions/GET_byBadName"))
                        .isEmpty())
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));
    }

    void verifyGetUserPermissions(int numberOfRoles,
                                  Mono<GodzillaUser> user,
                                  Function<GodzillaUser, String> uri,
                                  BiConsumer<GodzillaUser, GodzillaUserRepository> verifyService) {
        StepVerifier.create(user)
                .consumeNextWith(
                    u -> {
                        client.get()
                            .uri(uri.apply(u))
                            .exchange()
                            .expectStatus()
                                .isOk()
                            .expectHeader()
                            .contentType(APPLICATION_JSON)
                            .expectBody(isJsonMap())
                            .consumeWith(b -> documentGetPermissions(b, numberOfRoles))
                            .value(json -> assertThat(json)
                                .isNotNull()
                                .hasSize(2)
                                .containsEntry("isAdmin", false)
                                .containsEntry("roles", u.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::toString)
                                    .collect(toList())));

                        verifyService.accept(u, repo);
                    })
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));
    }

    Flux<GodzillaUser> userFlux(int startingId) {
        return Flux.fromStream(() -> Stream
                .iterate(
                    buildUser(startingId),
                    user -> true,
                    user -> user
                        .withId(user.getId() + 1)
                        .withAuthorities(userRolesSource()
                            .limit(user.getId() - startingId + (long) 1)
                            .reduce((r1, r2) -> r1 + "," + r2)
                            .orElse("")))
                .skip(1));
    }

    private Stream<String> userRolesSource() {
        return Stream.of(
            "ROLE_USER",
            "ROLE_SELLER",
            "ROLE_BUYER",
            "ROLE_MANAGER"
        );
    }

    private GodzillaUser buildUser(int id) {
        return GodzillaUser
                .builder()
                    .id(id)
                    .username("SomeUser")
                    .password(encoder.encode("SomePassword"))
                    .authorities("")
                .build();
    }

    private ParameterizedTypeReference<Map<String, Object>> isJsonMap() {
        return new ParameterizedTypeReference<Map<String, Object>>(){};
    }

    private void documentGetPermissions(EntityExchangeResult<Map<String, Object>> body, int numberOfRoles) {
        if (numberOfRoles == 1) {
            document(
                "api/users/permissions/GET_byId",
                preprocessResponse(prettyPrint()))
            .accept(body);
        }
    }

    /**
     * We need to mock the test user account
     */
    private void mockStartupConfig() {
        when(repo.findByUsername("test"))
                .thenReturn(Mono
                    .fromCallable(() -> GodzillaUser
                        .builder()
                        .id(1)
                        .username("test")
                        .authorities("ROLE_USER")
                        .build()));
    }

    private static String[] badIdMethodSource() {
        return new String[] {
            "-1", "-200", "-9999", "-999999",
            String.valueOf(Integer.MIN_VALUE),
            "asdawd", "123asd", "oh man"
        };
    }
}
