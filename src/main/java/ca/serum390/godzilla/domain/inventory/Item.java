package ca.serum390.godzilla.domain.inventory;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

import java.util.Map;

@Data
@With
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table("inventory")
public class Item {

    @Id
    private Integer id;
    private String itemName;
    private int goodType;
    private int quantity;
    private float buyPrice;
    private float sellPrice;
    private String location;
    private Map<Integer,Integer> billOfMaterial;
}
