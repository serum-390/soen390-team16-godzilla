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

    // Transportation methods
    public static final String AIR = "air";
    public static final String CAR = "car";
    public static final String FERRY = "ferry";


    @Id
    private Integer id;
    private String transportationMethod;
    private String shippingStatus;
    private LocalDate dueDate;
    private LocalDate deliveryDate;
    private LocalDate packagingDate;
    private Integer orderId;
    private Double shippingPrice;
}
