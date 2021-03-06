package ca.serum390.godzilla.config;

import static ca.serum390.godzilla.api.handlers.GodzillaUserHandler.DEFAULT_AUTHORITIES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.serum390.godzilla.SendEmailService;
import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.GodzillaUser;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Log4j2
@Configuration
public class StartupApplicationConfiguration {

    @Autowired
    GodzillaUserRepository godzillaUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SendEmailService sendEmailService;

    /**
     * Add some demo users to the database on application startup
     *
     * @param event
     */
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Running post start up configuration...");
        sendEmailService.sendEmail("ERP.Godzilla@gmail.com", "test", "this is a test").subscribe();
        Flux.just("demo", "jeff", "test")
            .filterWhen(this::userAlreadyExists)
            .map(this::buildDemoUser)
            .collectList()
            .subscribe(users -> godzillaUserRepository.saveAll(users)
                .doOnError(log::error)
                .subscribe(this::logDemoUserCreation));
    }

    /**
     * Checks if the user already exists in the database
     *
     * @param user
     * @return
     */
    private Mono<Boolean> userAlreadyExists(String username) {
        var found = godzillaUserRepository.findByUsername(username);
        return found == null
                ? Mono.just(false)
                : godzillaUserRepository
                    .findByUsername(username)
                    .map(u -> false)
                    .defaultIfEmpty(true);
    }

    private GodzillaUser buildDemoUser(String username) {
        return GodzillaUser
            .builder()
                .username(username)
                .password(passwordEncoder.encode("demo"))
                .authorities(DEFAULT_AUTHORITIES)
                .isAdmin(true)
                .email("demo@demomail.com")
            .build();
    }

    private void logDemoUserCreation(GodzillaUser user) {
        log.info("Added demo user: {} to the database", user);
    }
}
