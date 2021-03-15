package ca.serum390.godzilla.domain.manufacturing;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@With
@Builder
@ToString
@Table("planned_products")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PlannedProduct {

    // Status values
    public static final String BLOCKED = "blocked";
    public static final String NEW = "new";
    public static final String SCHEDULED = "scheduled";
    public static final String COMPLETED = "completed";

    @Id
    private Integer id;
    private LocalDate productionDate;
    private Integer orderID;
    private String status;
    private Map<Integer, Integer> usedItems;

    public PlannedProduct(LocalDate productionDate, Integer orderID) {
        this.productionDate = productionDate;
        this.orderID = orderID;
        this.status = NEW;
        usedItems = new HashMap<>();
    }
}
