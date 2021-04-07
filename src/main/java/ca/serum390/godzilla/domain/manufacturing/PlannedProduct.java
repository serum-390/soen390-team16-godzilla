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
    public static final String BLOCKED = "BLOCKED";
    public static final String NEW = "NEW";
    public static final String SCHEDULED = "SCHEDULED";
    public static final String COMPLETED = "COMPLETED";
    public static final String CANCELLED = "CANCELLED";

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
