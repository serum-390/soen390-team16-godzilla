package ca.serum390.godzilla.service;

import ca.serum390.godzilla.data.repositories.Erp_repository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class Erp_UserDetailsService implements ReactiveUserDetailsService  {
    private final Erp_repository erp_repository;


    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return erp_repository.findByUsername(s).cast(UserDetails.class);
    }
}
