package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

public interface OrdersRepository extends ReactiveCrudRepository<Order, Integer> {
    @Modifying
    @Query("UPDATE orders SET CREATED_DATE = $1, DUE_DATE = $2, DELIVERY_LOCATION= $3, ORDER_TYPE=$4 , STATUS = $6 , ITEMS = $7 WHERE ID = $5")
    Mono<Integer> update(LocalDate createdDate, LocalDate dueDate, String deliveryLocation, String orderType,
                         Integer id, String status, Map<Integer, Integer> items);

    @Query("INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ($1,$2,$3,$4,$5,$6)")
    Mono<Integer> save(LocalDate createdDate, LocalDate dueDate, String deliveryLocation, String orderType, String status, Map<Integer, Integer> items);

    @Query("SELECT * FROM orders WHERE ORDER_TYPE= $1 AND STATUS = $2")
    Flux<Order> findAllBy(String orderType, String status);

    @Query("SELECT * FROM orders WHERE ORDER_TYPE= $1")
    Flux<Order> findAllByType(String orderType);

    @Query("SELECT * FROM orders WHERE STATUS= $1")
    Flux<Order> findAllByStatus(String status);
}
