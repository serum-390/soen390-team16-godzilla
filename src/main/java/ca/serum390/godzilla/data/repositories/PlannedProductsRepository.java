package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.manufacturing.PlannedProduct;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

public interface PlannedProductsRepository extends ReactiveCrudRepository<PlannedProduct, Integer> {
    @Modifying
    @Query("UPDATE PLANNED_PRODUCTS SET ORDER_ID = $2, PRODUCTION_DATE = $3, STATUS = $4, USED_ITEMS =$5  WHERE ID = $1")
    Mono<Integer> update(
            Integer id, Integer orderID,
            LocalDate productionDate,
            String status, Map<Integer,
            Integer> usedItems);

    @Modifying
    @Query("UPDATE PLANNED_PRODUCTS SET  STATUS = $2 WHERE ID = $1")
    Mono<Integer> updateStatus(Integer id, String status);

    @Modifying
    @Query("UPDATE PLANNED_PRODUCTS SET USED_ITEMS = $2 WHERE ID = $1")
    Mono<Integer> updateUsedItems(Integer id, Map<Integer, Integer> items);

    @Transactional
    @Query("INSERT INTO PLANNED_PRODUCTS( ORDER_ID, PRODUCTION_DATE, STATUS, USED_ITEMS) VALUES ($1,$2,$3,$4) RETURNING *")
    Mono<PlannedProduct> save(
            Integer orderID,
            LocalDate productionDate,
            String status,
            Map<Integer, Integer> usedItems);

    @Query("Select * FROM plannedProducts WHERE STATUS = $1")
    Flux<PlannedProduct> findAllByStatus(String status);
}
