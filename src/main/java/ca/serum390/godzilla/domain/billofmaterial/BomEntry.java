package ca.serum390.godzilla.domain.billofmaterial;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    @Column("item_name") private Integer itemName;
    @Column("item_needed") private Integer itemNeeded;
    private Integer quantity;
}
