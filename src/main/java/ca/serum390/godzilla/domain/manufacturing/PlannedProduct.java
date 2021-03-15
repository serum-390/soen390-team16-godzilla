package ca.serum390.godzilla.domain.manufacturing;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@With
@Builder
@ToString
@Table("planned_products")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PlannedProduct {

    @Id
    private Integer id;
    private LocalDate productionDate;
    private Integer orderID;
    private String status;

    //TODO use enum for status : new , processing, blocked , complete
    public PlannedProduct(LocalDate productionDate, Integer orderID) {
        this.productionDate = productionDate;
        this.orderID = orderID;
        this.status = "new";
    }
}
