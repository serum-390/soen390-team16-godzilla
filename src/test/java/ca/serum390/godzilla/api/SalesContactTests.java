package ca.serum390.godzilla.api;

import static ca.serum390.godzilla.api.handlers.exceptions.NegativeIdException.CANNOT_PROCESS_DUE_TO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.data.repositories.SalesContactRepository;
import ca.serum390.godzilla.domain.orders.SalesContact;
import ca.serum390.godzilla.util.BuildableJsonMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class SalesContactTests {


    @MockBean
    SalesContactRepository salesContactRepository;
    final String SALES_CONTACT_API = "/api/salescontact/";
    WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context, RestDocumentationContextProvider restDocProvider) {
        this.client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocProvider))
                .build();
    }

    /**
     * Tests: `/api/salescontact/`
     */
    @Test
    @WithMockUser("test")
    void getAllSalesContactsTest() {
        Flux<SalesContact> contacts = buildContactFlux(1).limitRequest(2);
        when(salesContactRepository.findAll()).thenReturn(contacts);
        Mono<List<SalesContact>> collected = contacts.collectList();

        StepVerifier.create(collected)
                .consumeNextWith(list -> client.get()
                    .uri(SALES_CONTACT_API)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(APPLICATION_JSON)
                    .expectBodyList(SalesContact.class)
                    .consumeWith(
                        document("api/salescontacts/all_GET",
                            preprocessResponse(prettyPrint())))
                    .value(l -> assertThat(l)
                        .isNotNull()
                        .hasSize(2)
                        .isEqualTo(list)))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(salesContactRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("goodIdMethodSource")
    void getSalesContactByIdTest(final int id) {
        Mono<SalesContact> contact = buildContactFlux(id).limitRequest(1).shareNext();
        when(salesContactRepository.findById(id)).thenReturn(contact);

        StepVerifier.create(contact)
                .consumeNextWith(c -> client.get()
                    .uri(SALES_CONTACT_API + id)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(APPLICATION_JSON)
                    .expectBody(SalesContact.class)
                    .consumeWith(con -> assertThat(
                        con.getResponseBody())
                            .isNotNull()
                            .isEqualTo(c)))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(salesContactRepository).findById(id);
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("goodIdMethodSource")
    void getNonExistentSalesContactByIdTest(final int id) {
        when(salesContactRepository.findById(id)).thenReturn(Mono.empty());
        client.get()
                .uri(SALES_CONTACT_API + id)
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody().isEmpty();
        verify(salesContactRepository).findById(id);
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("badIdMethodSource")
    void getSalesContactBadIdTest(final String id) {
        client.get()
                .uri(SALES_CONTACT_API + id)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(String.class)
                .consumeWith(err -> assertThat(err
                    .getResponseBody())
                    .startsWith(CANNOT_PROCESS_DUE_TO));
    }

    @Test
    @WithMockUser("test")
    void createSalesContactTest() {
        Mono<SalesContact> contact = buildContactFlux(1).take(1).shareNext();
        when(salesContactRepository.save(any())).thenReturn(contact);

        StepVerifier.create(contact)
                .consumeNextWith(c -> client.post()
                    .uri(SALES_CONTACT_API)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .body(contact, SalesContact.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(SalesContact.class)
                    .consumeWith(
                        document("api/salescontact/create_POST",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint())))
                    .isEqualTo(c))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(salesContactRepository).save(any());
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("badIdMethodSource")
    void createSalesContactWithBadIdTest(final String id) {
        Mono<Map<Object, Object>> contact =  Mono
                .fromCallable(() -> BuildableJsonMap
                .map(buildSalesContact(0))
                .with("id", id));

       client.post()
                .uri(SALES_CONTACT_API)
                .contentType(APPLICATION_JSON)
                .body(contact, Map.class)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .consumeWith(err -> assertThat(err
                    .getResponseBody())
                    .startsWith(CANNOT_PROCESS_DUE_TO));
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("goodIdMethodSource")
    void updateSalesContactTest(final int id) {
        Mono<SalesContact> contact1 = buildContactFlux(id).limitRequest(1).shareNext();
        Mono<SalesContact> contact2 = contact1.map(c -> c
                .withAddress("New Address")
                .withCompanyName("New Company Name")
                .withContact("New Contact")
                .withContactName("New Contact Name"));

        when(salesContactRepository.findById(id)).thenReturn(contact1);
        when(salesContactRepository.update(
            any(), any(), any(), any(), any(), eq(id))
        ).thenReturn(Mono.just(1));

        StepVerifier.create(contact2)
                .consumeNextWith(c -> client
                    .put()
                    .uri(SALES_CONTACT_API + id)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(c)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("rowsUpdated", 1))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofSeconds(1));

        verify(salesContactRepository).findById(id);
        verify(salesContactRepository).update(
                any(), any(), any(), any(), any(), eq(id));
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("badIdMethodSource")
    void updateSalesContactWithBadIdTest(final String id) {
        Mono<Map<Object, Object>> body = Mono
                .fromCallable(() -> buildSalesContact(0))
                .map(BuildableJsonMap::map)
                .map(m -> m.with("id", id));

        client.put()
                .uri(SALES_CONTACT_API + id)
                .contentType(APPLICATION_JSON)
                .body(body, Map.class)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .value(s -> assertThat(s).startsWith(CANNOT_PROCESS_DUE_TO));
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("goodIdMethodSource")
    void deleteSalesContactTest(final int id) {
        when(salesContactRepository.deleteById(id)).thenReturn(Mono.empty());

        client.delete()
                .uri(SALES_CONTACT_API + id)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        verify(salesContactRepository).deleteById(id);
    }

    @ParameterizedTest
    @WithMockUser("test")
    @MethodSource("badIdMethodSource")
    void deleteSalesContactBadIdTest(final String id) {
        client.delete()
                .uri(SALES_CONTACT_API + id)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .value(s -> assertThat(s).startsWith(CANNOT_PROCESS_DUE_TO));
    }

    /**
     * Builds an inifinite {@link Flux} of {@link SalesContact SalesContacts} with
     * incrementing id values starting from {@code startingId}
     *
     * @param startingId The id value to start incrementing from
     * @return A never-ending {@link Flux} of {@link SalesContact SalesContacts}
     */
    private static Flux<SalesContact> buildContactFlux(Integer startingId) {
        return Flux.fromStream(() -> Stream.iterate(
                buildSalesContact(startingId),
                c -> true,
                c -> buildSalesContact(c.getId() + 1)));
    }

    private static SalesContact buildSalesContact(final int id) {
        return SalesContact
                .builder()
                    .id(id)
                    .address("123 Main st.")
                    .companyName("Godzilla ERP")
                    .contact("514-555-7892")
                    .contactName("Jeff")
                    .contactType("sales")
                .build();
    }

    private static String[] badIdMethodSource() {
        return new String[] {
            "-1", "-200", "-9999", "-999999",
            String.valueOf(Integer.MIN_VALUE),
            "asdawd", "123asd", "oh man"
        };
    }

    private static int[] goodIdMethodSource() {
        return new int[] { 0, 1, 200, 9999, Integer.MAX_VALUE };
    }
}
