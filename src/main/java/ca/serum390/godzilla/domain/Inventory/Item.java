package ca.serum390.godzilla.domain.Inventory;

import java.net.Inet4Address;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@Table("inventory")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class Item {

    @Id
    private Integer id;
    private String item_name;
    private int good_type;
    private int quantity;
    private int buy_price;
    private int sell_price;
    private String location;
    private boolean bill_of_material;
    
}
