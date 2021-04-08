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
    public static final String READY = "ready";
    public static final String NEW = "new";
    public static final String PACKAGING_PROCESS= "packaging process";
    public static final String PRODUCTION_PROCESS = "production process";
    public static final String SHIPPING_PROCESS = "shipping process";
    public static final String PACKAGED = "packaged";
    public static final String SHIPPED = "shipped";
    public static final String DELIVERED = "delivered";

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
