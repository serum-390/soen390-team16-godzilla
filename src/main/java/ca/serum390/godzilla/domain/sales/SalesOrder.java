package ca.serum390.godzilla.domain.sales;

import java.util.Date;

import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@Table("orders")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    @Id
    private Integer id;
    private Date createdDte;
    private Date dueDate;
    private String deliveryLocation;
    private String orderType;

}
