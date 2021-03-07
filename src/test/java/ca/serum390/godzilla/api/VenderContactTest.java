package ca.serum390.godzilla.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.serum390.godzilla.data.repositories.VenderContactRepository;
import ca.serum390.godzilla.domain.vender.VenderContact;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import reactor.core.publisher.Flux;

@SpringBootTest
@AutoConfigureWebTestClient
public class VenderContactTest {

    private static final String VENDER_CONTACT_API = "/api/vendercontact/";

    @Autowired
    WebTestClient webClient;

    @Autowired
    DatabaseClient databaseClient;

    @MockBean
    VenderContactRepository venderContactRepository;

    /**
     * Tests: GET /api/vendercontact/
     */

    @Test
    @WithMockUser("test")
    void retrieveAllVenderContactTest(){
        int numberOfDemoVenderContacts = 1;
        List<VenderContact> contactsCollection = new ArrayList<>();
        when(venderContactRepository.findAll()).thenReturn(
            buildDemoVenderContactFlux()
                .doOnNext(contactsCollection::add)
                .take(numberOfDemoVenderContacts));

        webClient.get()
                .uri(VENDER_CONTACT_API)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBodyList(VenderContact.class)
                .hasSize(numberOfDemoVenderContacts)
                .isEqualTo(contactsCollection)
                .value(list -> {
                    assertThat(list).isNotNull();
                });

        verify(venderContactRepository, times(1)).findAllVender();
    }

    /**
     * Generates {@link VenderContact VenderContacts} with progressively incrementing
     * id values
     * 
     * @return An inifinitely long Flux VenderContacts
     */
    private Flux<VenderContact> buildDemoVenderContactFlux(){
        class Incrementer{
            int val = 1;
            int next() {
                return val++;
            }
        }

        Incrementer idIncrementer = new Incrementer();
        Supplier<VenderContact> buildDemoContact = () -> VenderContact
                .builder()
                .id(idIncrementer.next())
                .companyName("testCompanyName")
                .contactName("testContanctName")
                .address("testAddress")
                .contact("testContact")
                .contactType("testVender")
                .build();

        return Flux.generate(buildDemoContact::get,(state, sink) -> {sink.next(state);return buildDemoContact.get();});
    }
}
