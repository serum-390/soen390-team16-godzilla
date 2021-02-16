package ca.serum390.godzilla.domain.Inventory;

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
    private UUID id;
    private String itemName;
    private int goodType;
    private int quantity;
    private float buyPrice;
    private float sellPrice;
    private String location;
    private boolean billOfMaterial;
}
