package ca.serum390.godzilla.domain.orders;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
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
    @Id
    private Integer id;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private String deliveryLocation;
    private String orderType;
    private String status;
    private Map<Integer, Integer> items;
}
