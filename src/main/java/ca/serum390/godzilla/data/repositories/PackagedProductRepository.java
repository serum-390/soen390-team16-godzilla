package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.packaging.PackagedProduct;
import jdk.jfr.FlightRecorderListener;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import javax.sound.sampled.FloatControl;

@Repository
public interface PackagedProductRepository extends ReactiveCrudRepository<PackagedProduct, Integer> {
    @Modifying
    @Query("UPDATE packaged_products SET LENGTH = $1, WIDTH = $2, HEIGHT= $3, WEIGHT=$4 , PACKAGE_TYPE = $6  WHERE ID = $5")
    Mono<Integer> update(Float length, Float width, Float height, Float weight,
                         String packageType, Integer id);
}
