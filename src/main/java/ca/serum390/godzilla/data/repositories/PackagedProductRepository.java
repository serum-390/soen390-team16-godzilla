package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.packaging.PackagedProduct;

import java.time.LocalDate;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PackagedProductRepository extends ReactiveCrudRepository<PackagedProduct, Integer> {
    @Modifying
    @Query("UPDATE packaged_products SET LENGTH = $1, WIDTH = $2, HEIGHT= $3, WEIGHT=$4 , PACKAGE_TYPE = $5, PACKAGE_DATE = $6  WHERE ID = $7")
    Mono<Integer> update(Float length, Float width, Float height, Float weight, String packageType, LocalDate packageDate, Integer id);

    @Query("INSERT INTO packaged_products(LENGTH, WIDTH, HEIGHT, WEIGHT, PACKAGE_TYPE, PACKAGE_DATE) VALUES ($1,$2,$3,$4,$5,$6) RETURNING *")
    Mono<PackagedProduct> save(Float length, Float width, Float height, Float weight, String packageType, LocalDate packageDate);
}
