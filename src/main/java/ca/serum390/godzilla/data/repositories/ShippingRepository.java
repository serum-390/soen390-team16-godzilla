package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ShippingRepository extends ReactiveCrudRepository<Shipping, Integer> {

    @Modifying
    @Query("UPDATE shippings SET SHIPPING_METHOD = $2, STATUS = $3, DUE_DATE = $4, DELIVERY_DATE =$5," +
            "PACKAGING_DATE = $6,  ORDER_ID = $7 ,SHIPPING_PRICE = $8 WHERE ID = $1")
    Mono<Integer> update(
            Integer id,
            String shippingMethod,
            String shippingStatus,
            LocalDate dueDate,
            LocalDate deliveryDate,
            LocalDate packagingDate,
            Integer orderId,
            Double shippingPrice);
}
