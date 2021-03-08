package ca.serum390.godzilla.data.repositories;

import ca.serum390.godzilla.domain.Inventory.Item;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Item, Integer> {

    /**
     * Search by name
     *
     * @param itemName
     * @return
     */
    @Query("SELECT * FROM inventory WHERE item_name = $1")
    Flux<Item> findByName(String itemName);

    @Modifying
    @Query("UPDATE inventory SET ITEM_NAME = $1, LOCATION = $2, BUY_PRICE = $4, GOOD_TYPE = $5, QUANTITY =$6, SELL_PRICE = $7, BILL_OF_MATERIAL = $8  WHERE ID = $3")
    Mono<Integer> update(String name, String location, Integer id, float buyPrice, int goodType, int quantity, float sellPrice, Map<Integer, Integer> BOM);

    @Query("INSERT INTO inventory(ITEM_NAME, LOCATION, BUY_PRICE, GOOD_TYPE, QUANTITY, SELL_PRICE, BILL_OF_MATERIAL) VALUES ($1,$2,$3,$4,$5,$6,$7)")
    reactor.core.publisher.Mono<Order> save(String name, String location, float buyPrice, int goodType, int quantity, float sellPrice, Map<Integer, Integer> BOM);
    @Modifying
    @Query("UPDATE inventory SET QUANTITY =$2 WHERE ID = $1")
    Mono<Integer> update(Integer id,int quantity);
}
