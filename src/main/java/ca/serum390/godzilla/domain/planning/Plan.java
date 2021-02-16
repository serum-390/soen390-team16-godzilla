package ca.serum390.godzilla.domain.planning;

import org.springframework.stereotype.Component;

import ca.serum390.godzilla.api.handlers.InventoryHandler;
import ca.serum390.godzilla.data.repositories.InventoryRepository;

@Component
public class Plan {
    // TODO: Implement `Plan` domain object

    private int item_quantity;

     //Constructor
    public Plan(){

    }

     /**
      * Check if specific item quantity
      */
    private InventoryRepository searchInTable;

    public Boolean checkQuantity(int quantity){
        
        return false;
    }

}
