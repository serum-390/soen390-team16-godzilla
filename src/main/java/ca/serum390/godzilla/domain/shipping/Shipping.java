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

    // Status value
    public static final String SHIPPED = "shipped";
    public static final String CANCELED = "canceled";
    public static final String SCHEDULED = "scheduled";
    public static final String NEW = "new";

    // shipping methods
    public static final String AIR = "air";
    public static final String CAR = "car";
    public static final String FERRY = "ferry";

    // prices
    public static final double AIR_RATIO = 4;
    public static final double CAR_RATIO = 1;
    public static final double FERRY_RATIO = 2;


    @Id
    private Integer id;
    private String shippingMethod;
    private String status;
    private LocalDate dueDate;
    private LocalDate shippingDate;
    private Integer orderID;
    private double shippingPrice;

    public Shipping(String shippingMethod,
                    String status,
                    LocalDate dueDate,
                    LocalDate shippingDate,
                    Integer orderID,
                    double shippingPrice) {

        this.shippingMethod = shippingMethod;
        this.status = status;
        this.dueDate = dueDate;
        this.shippingDate = shippingDate;
        this.orderID = orderID;
        this.shippingPrice = shippingPrice;
    }

    public static double getPrice(int numItems, String shippingMethod) {
        switch (shippingMethod) {
            case AIR:
                return numItems * AIR_RATIO;
            case CAR:
                return numItems * CAR_RATIO;
            case FERRY:
                return numItems * FERRY_RATIO;
            default:
                return -1;
        }
    }
}
