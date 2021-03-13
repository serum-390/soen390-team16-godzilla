package ca.serum390.godzilla.util.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchaseOrderEvent extends ERPEvent {
    //TODO : fire this event after a certain time, the inventory will listen and handle it. it will add all the items
    // in the purchase event to inventory -> fires an inventory event
    private Integer orderID;
}
