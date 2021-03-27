package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends ReactiveCrudRepository<Shipping, Integer> {
}
