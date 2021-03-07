package ca.serum390.godzilla.data.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.billOfMaterial.BomEntry;

@Repository
public interface BomRepository extends ReactiveCrudRepository <BomEntry, Integer> {


}
