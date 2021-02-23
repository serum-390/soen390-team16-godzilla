package ca.serum390.godzilla.data.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import ca.serum390.godzilla.domain.Inventory.Item;
import reactor.core.publisher.Flux;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Item, Integer>{

    /**
     * Search by name
     * @param itemName
     * @return
     */
    @Query("SELECT * FROM inventory WHERE item_name = $1")
    Flux<Item> findbyName(String itemName);
}
