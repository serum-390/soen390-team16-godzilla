package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface PlannedProductsRepository extends ReactiveCrudRepository<PlannedProduct, Integer> {
    @Modifying
    @Query("UPDATE PLANNED_PRODUCTS SET ORDER_ID = $2, PRODUCTION_DATE = $3, STATUS = $4  WHERE ID = $1")
    Mono<Integer> update(Integer id, Integer orderID, LocalDate productionDate, String status);

    @Modifying
    @Query("UPDATE PLANNED_PRODUCTS SET  STATUS = $2 WHERE ID = $1")
    Mono<Integer> updateStatus(Integer id, String status);

    @Query("Select * FROM plannedProducts WHERE STATUS = $1")
    Flux<Integer> findAllByStatus(String status);
}
