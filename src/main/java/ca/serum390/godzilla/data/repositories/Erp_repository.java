package ca.serum390.godzilla.data.repositories;

@RequiredArgsConstructor
@Service
public interface Erp_repository  extends ReactiveCrudRepository<Erp_user, Integer>{
    Mono<Erp_user> findByUsername(String username);

}

