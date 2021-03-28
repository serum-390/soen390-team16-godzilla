package ca.serum390.godzilla.domain.shipping;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@With
@Builder
@ToString
@Table("shippings")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {

    // Status values
    public static final String PACKAGED = "packaged";
    public static final String DELIVERED = "delivered";
    public static final String TRANSIT = "in transit";
    public static final String NEW = "new";

    // shipping methods
    public static final String AIR = "air";
    public static final String CAR = "car";
    public static final String FERRY = "ferry";

    // prices
    public static final double PACKAGING_RATIO = 1.5;
    public static final double AIR_RATIO = 4;
    public static final double CAR_RATIO = 1;
    public static final double FERRY_RATIO = 2;

    public Shipping(String shippingMethod,
                    String status,
                    LocalDate dueDate,
                    LocalDate shippingDate,
                    LocalDate packagingDate,
                    Integer orderID,
                    double shippingPrice) {

        this.shippingMethod = shippingMethod;
        this.status = status;
        this.dueDate = dueDate;
        this.shippingDate = shippingDate;
        this.packagingDate = packagingDate;
        this.orderID = orderID;
        this.shippingPrice = shippingPrice;

    }

    @Id
    private Integer id;
    private String shippingMethod;
    private String status;
    private LocalDate dueDate;
    private LocalDate shippingDate;
    private LocalDate packagingDate;
    private Integer orderID;
    private double shippingPrice;
}
