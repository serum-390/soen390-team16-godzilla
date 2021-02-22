package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.sales.SalesOrder;
import reactor.core.publisher.Mono;

public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrder, Integer> {

}
