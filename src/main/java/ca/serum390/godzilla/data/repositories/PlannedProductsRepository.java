package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface PlannedProductsRepository extends ReactiveCrudRepository<PlannedProduct, Integer> {
    @Modifying
    @Query("UPDATE plannedProducts SET ORDER_ID = $2, PRODUCTION_DATE = $3, STATUS = $4  WHERE ID = $1")
    Mono<Integer> update(Integer id, Integer orderID, LocalDate productionDate, String status);

    @Query("Select * FROM plannedProducts WHERE STATUS = $1")
    Mono<Integer> findAllByStatus(String status);
}
