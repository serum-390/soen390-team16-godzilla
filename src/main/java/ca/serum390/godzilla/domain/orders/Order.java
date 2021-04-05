package ca.serum390.godzilla.domain.orders;

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
@Table("orders")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    // Status values
    public static final String READY = "READY";
    public static final String NEW = "NEW";
    public static final String PRODUCTION_PROCESS = "PRODUCTION PROCESS";
    public static final String SHIPPING_PROCESS = "SHIPPING PROCESS";
    public static final String PACKAGED = "PACKAGED";
    public static final String SHIPPED = "SHIPPED";
    public static final String DELIVERED = "DELIVERED";

    @Id
    private Integer id;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private String deliveryLocation;
    private String orderType;
    private String status;
    private Map<Integer, Integer> items;
    private Integer productionID;

    public Order(LocalDate createdDate, LocalDate dueDate, String deliveryLocation, String orderType) {
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.deliveryLocation = deliveryLocation;
        this.orderType = orderType;
        this.status = NEW;
        items = new HashMap<>();
    }
}
