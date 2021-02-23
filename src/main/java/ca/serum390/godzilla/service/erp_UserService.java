package ca.serum390.godzilla.service;

@RequiredArgsConstructor
@Service
public class erp_UserService implements ReactiveUserDetailsService {
    
    private final Erp_repository erp_repository;

    Mono<Erp_user> findByUsername(String username){

        return Erp_repository.findByUsername(username)
        .cast(UserDetails.class);
    }

}
