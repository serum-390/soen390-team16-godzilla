package ca.serum390.godzilla.domain.manufacturing;

import ca.serum390.godzilla.domain.orders.Order;
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
}
