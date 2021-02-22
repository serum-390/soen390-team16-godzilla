package ca.serum390.godzilla.domain.billOfMaterial;

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
@Table("bill_of_material")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class BomEntry {
    
    @Id
    private Integer item_name;
    private Integer item_needed;
    private Integer quantity;
}
