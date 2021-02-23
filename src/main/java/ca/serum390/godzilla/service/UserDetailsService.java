package ca.serum390.godzilla.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ca.serum390.godzilla.data.repositories.GodzillaUserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private final GodzillaUserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userRepository.findByUsername(s).cast(UserDetails.class);
    }
}
