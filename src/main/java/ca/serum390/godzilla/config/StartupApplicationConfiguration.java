package ca.serum390.godzilla.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import ca.serum390.godzilla.domain.GodzillaUser;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@Configuration
public class StartupApplicationConfiguration {

    @Autowired
    GodzillaUserRepository godzillaUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Add some demo users to the database on application startup
     *
     * @param event
     */
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Flux<GodzillaUser> demoUsers = Flux.just(buildDemoUser("demo"), buildDemoUser("jeff"));
        log.debug("Running post start up configuration...");
        godzillaUserRepository
                .saveAll(demoUsers)
                .subscribe(this::logDemoUserCreation);
    }

    private GodzillaUser buildDemoUser(String username) {
        return GodzillaUser.builder()
                .username(username)
                .password(passwordEncoder.encode("demo"))
                .authorities("ROLE_USER")
                .build();
    }

    private void logDemoUserCreation(GodzillaUser user) {
        log.debug("Added demo user: {} to the database", user);
    }
}
