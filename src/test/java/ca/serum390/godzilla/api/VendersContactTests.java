package ca.serum390.godzilla.api;

import static ca.serum390.godzilla.api.handlers.exceptions.NegativeSalesContactIdException.CANNOT_PROCESS_DUE_TO;
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


import ca.serum390.godzilla.data.repositories.VendorContactRepository;
import ca.serum390.godzilla.domain.vendor.VendorContact;
import ca.serum390.godzilla.util.BuildableJsonMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class VendersContactTests {
    
    @MockBean
    VendorContactRepository vendorContactRepository;
    final String VENDOR_CONTACT_API = "/api/vendorcontact/";
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
     * Tests: '/api/vendorcontact/'
     */
    @Test
    @WithMockUser
    void getAllVendorContactTest() {
        Flux<VendorContact> contacts = buildContactFlux(1).limitRequest(2);
        when(vendorContactRepository.findAllVendor()).thenReturn(contacts);
        Mono<List<VendorContact>> collected = contacts.collectList();

        StepVerifier.create(collected)
                .consumeNextWith(list -> client.get()
                    .uri(VENDOR_CONTACT_API)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(APPLICATION_JSON)
                    .expectBodyList(VendorContact.class)
                    .consumeWith(document("api/vendorcontact/all_GET",preprocessResponse(prettyPrint())))
                    .value(l -> assertThat(l)
                        .isNotNull()
                        .hasSize(2)
                        .isEqualTo(list)))
                    .expectComplete()
                    .verifyThenAssertThat()
                    .tookLessThan(Duration.ofSeconds(1));
        verify(vendorContactRepository).findAllVendor();
    }

    private static Flux<VendorContact> buildContactFlux(Integer startingId) {
        return Flux.fromStream(() -> Stream.iterate(
            buildVendorContact(startingId),
            c -> true,
            c -> buildVendorContact(c.getId() + 1)));
    }

    private static VendorContact buildVendorContact(final int id) {
        return VendorContact
            .builder()
                .id(id)
                .address("123 test st.")
                .companyName("test")
                .contact("123-456-7890")
                .contactName("testName")
                .contactType("Vendor")
            .build();
    }
}
