package ca.serum390.godzilla.data.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.server.ServerResponse;

import ca.serum390.godzilla.domain.Inventory.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Item, Integer>{

    
    //Search by id for the bill of material 
    //@Query("SELECT 'bill_of_material' FROM inventory WHERE id = $1")
    //Mono<Integer> BOMfindById(Integer id);

    //Search by id for the quantity
    //@Query("SELECT 'quantity' FROM inventory WHERE id = $1")
    //Mono<Integer> QTYfindById(Integer id);

    //Search by name
    @Query("SELECT FROM inventory WHERE item_name = $1")
    Flux<Item> FindbyName(String item_name);

    //@Modifying
    //@Query("UPDATE goods SET item_name = $1, good_type = $2, quantity = $3, buy_price = $4, sell_price = $5, location = $6, bill_of_material = $7 WHERE id = $8")
    //Mono<Integer> update(String item_name,int good_type,int quantity, float buy_price, float sell_price, String location, Boolean bill_of_material, UUID id);

}
